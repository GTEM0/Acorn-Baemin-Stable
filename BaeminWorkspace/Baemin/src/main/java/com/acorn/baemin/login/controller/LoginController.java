package com.acorn.baemin.login.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.acorn.baemin.domain.AddressDTO;
import com.acorn.baemin.domain.SellerDTO;
import com.acorn.baemin.domain.UserDTO;
import com.acorn.baemin.home.repository.AddressRepositoryImp;
import com.acorn.baemin.login.repository.LoginRepository;
import com.acorn.baemin.login.repository.LoginRepositoryI;
import com.acorn.baemin.login.service.LoginService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Controller
public class LoginController {

	@Autowired
	LoginRepositoryI rep;

	@Autowired
	LoginRepository lr;

	@Autowired
	AddressRepositoryImp addressDAO;

	@Autowired
	private LoginService loginService;

	@Autowired
	private HttpSession session;

//	@Autowired
//	private BCryptPasswordEncoder passwordEncoder;

	// 유저 아이디 찾기 보내기
	@GetMapping("/findIdForm")
	public String findIdForm() {
		return "user/findIdForm";
	}

	// 유저 아이디 찾기 받기
	@PostMapping("/findId")
	public String findId(@RequestParam String email, Model model) {
		Map<String, Object> params = new HashMap<>();
		params.put("userEmail", email);
		params.put("sellerEmail", email);

		String customerId = rep.findCustomerId(params);
		String sellerId = rep.findSellerId(params);

		model.addAttribute("customerId", customerId);
		model.addAttribute("sellerId", sellerId);

		return "user/findIdResult";
	}

	// 유저 비밀번호 찾기 보내기
	@GetMapping("/findPwForm")
	public String findPwForm() {
		return "user/findPwForm";
	}

	// 유저 비밀번호 찾기 받기
	@PostMapping("/findPw")
	public String findPw(@RequestParam String Id, String email, Model model) {
		System.out.println("findPwResult1" + Id + email);
		Map<String, Object> params = new HashMap<>();
		params.put("userId", Id);
		params.put("userEmail", email);
		params.put("sellerId", Id);
		params.put("sellerEmail", email);

		String customerPw = rep.findCustomerPassword(params);
		String sellerPw = rep.findSellerPassword(params);
		model.addAttribute("customerPw", customerPw);
		model.addAttribute("sellerPw", sellerPw);
		return "user/findPwResult";
	}

	// 카카오 간편 로그인
	@GetMapping(value = "/kakaoLogin")
	public String kakaoLogin(@RequestParam(value = "code", required = false) String code) throws Exception {
		System.out.println("code : " + code);

		String access_Token = loginService.getAccessToken(code);
		System.out.println(" access_Token @LoginController : " + access_Token);

		UserDTO userInfo = loginService.getUserInfoAndAddress(access_Token);
		System.out.println("LoginController : " + userInfo);

		System.out.println("phoneNumber : " + userInfo.getUserPhone());
		System.out.println("email : " + userInfo.getUserEmail());
		System.out.println("baseAddress : " + userInfo.getUserAddress());
		System.out.println("detailAddress : " + userInfo.getUserAddressDetail());

		AddressDTO addrInfo = loginService.findAndInsertAddrInfo(userInfo);

		int addressCode = addrInfo.getAddressCode();
		System.out.println("addressCode @LoginController : " + addressCode);

		// session에 담긴 정보를 초기화.
		session.invalidate();
		// session에 userCode와 addressCode 담기
		session.setAttribute("userCode", userInfo.getUserCode());
		session.setAttribute("addressCode", addressCode);
		// 리턴값은 용도에 맞게 변경
		return "redirect:/home";
	}

	// 유저 로그인 보내기
	@GetMapping("/login")
	public String login(HttpSession session) {
		String result7 = "user/login";
		String user = (String) session.getAttribute("user");
		Integer userCode = (Integer) session.getAttribute("userCode");
		System.out.println(user);
		// 세션확인
		if (user != null || userCode != null) {
			return "redirect:/home";
		} else {
			return result7;
		}
	}

	// 손님 로그인 입력 정보 받아오기
	@PostMapping("/login")
	public String processLogin(@RequestParam String userId, @RequestParam String userpw, Model model,
			String logintype, HttpSession session) {

			UserDTO user = null;
		try {
			user = loginService.loginCustomer(userId, userpw);
			System.out.println(" user INFO =" + user);
			if (user != null && user.getUserStatus() == 1) {

				// 주소 정보 확인 및 추가
				int addressCount = addressDAO.selectAddressCount(user.getUserCode());

				if (addressCount == 0) {
					addressDAO.loginInsertAddress(new AddressDTO(0, user.getUserCode(), user.getUserAddress(),
							user.getUserAddressDetail(), 2));
				} else {
					System.out.println("주소값이 이미 있습니다!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
				}

				// 주소코드를 가져와서 세션에 넣기
				int addressCode = addressDAO.lastOrderAddressCode(user.getUserCode());
				session.setAttribute("addressCode", addressCode);
				
				// 저장된 해시된 비밀번호를 가져옴
				String storedPassword = user.getHashedPassword();
				// 입력된 비밀번호를 BCrypt로 해싱하여 저장된 비밀번호와 비교				 
				
				// 세션에 유저 정보 저장
				session.setAttribute("userCode", user.getUserCode());
				session.setAttribute("user", user.getUserId());
				
				if (BCrypt.checkpw(userpw, user.getHashedPassword())) {
					// 로그인 성공
					return "redirect:/home";
				} else {
					// 로그인 실패
					model.addAttribute("message", "로그인 실패. 비번실패");
					return "user/login";
				}

			} else {
				model.addAttribute("message", "로그인 실패. 로그인 유형과 계정 정보를 확인해주세요2.");
				return "user/login";
			}
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "로그인 중 오류가 발생했습니다.");
			return "user/login";
		}
	}

	// 사장님 로그인 입력 정보 받아오기
	@PostMapping("/login2")
	public String processLogin2(String userId, String userPw, Model model, String logintype, HttpSession session) {
		SellerDTO seller = loginService.loginSeller(userId, userPw);

		if (seller != null) {
			int status = seller.getSellerStatus();
			System.out.println("status" + status);

			if (status == 1) {
				int sellerCode = seller.getSellerCode();
				session.setAttribute("user", sellerCode);
				return "redirect:/sellerHome?sellerCode=" + sellerCode;
			} else {
				model.addAttribute("message", "로그인 실패. 로그인 유형과 계정 정보를 확인해주세요.");
				return "user/login";
			}
		} else {
			model.addAttribute("message", "로그인 실패. 로그인 유형과 계정 정보를 확인해주세요.");
			return "user/login";
		}
	}

	// 유저 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home";
	}

	// 손님 테이블 전체 조회
	@RequestMapping("/selectAll")
	public String main(Model model) {
		List<UserDTO> result;
		try {
			result = rep.selectAll();
			model.addAttribute("list", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/login";
	}
}