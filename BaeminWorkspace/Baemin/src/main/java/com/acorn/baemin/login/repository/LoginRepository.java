package com.acorn.baemin.login.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.acorn.baemin.domain.AddressDTO;
import com.acorn.baemin.domain.SellerDTO;
import com.acorn.baemin.domain.UserDTO;

@Repository
public class LoginRepository implements LoginRepositoryI {

	@Autowired
	private SqlSession session;

	@Autowired
	private static String namespace = "com.acorn.LoginMapper.";

	// 손님 아이디 찾기
	@Override
	public String findCustomerId(Map<String, Object> params) {
		UserDTO user = session.selectOne(namespace + "findCustomerId", params);
		return (user != null) ? user.getUserId() : null;
	}

	// 사장님 아이디 찾기
	@Override
	public String findSellerId(Map<String, Object> params) {
		SellerDTO seller = session.selectOne(namespace + "findSellerId", params);
		return (seller != null) ? seller.getSellerId() : null;
	}

	// 손님 비밀번호 찾기
	@Override
	public String findCustomerPassword(Map<String, Object> params) {
		UserDTO user = session.selectOne(namespace + "finduserPw", params);
		return (user != null) ? user.getUserPw() : null;
	}

	// 사장님 비밀번호 찾기
	@Override
	public String findSellerPassword(Map<String, Object> params) {
		SellerDTO user = session.selectOne(namespace + "findsellerPw", params);
		return (user != null) ? user.getSellerPw() : null;
	}

	// 손님 로그인
	@Override
	public UserDTO login(String userId, String hashedPassword) {
		System.out.println(userId + hashedPassword);
		UserDTO user = new UserDTO();
		user.setUserId(userId);
		System.out.println("select" + user);
		UserDTO result = session.selectOne(namespace + "selectUserById", user);
		System.out.println("result9" + result);
		return result;
	}

	// 사장님 로그인
	@Override
	public SellerDTO loginseller(String sellerId, String sellerPw) {
		System.out.println(sellerId + sellerPw);
		SellerDTO seller = new SellerDTO();
		seller.setSellerId(sellerId);
		seller.setSellerPw(sellerPw);
		System.out.println("select" + seller);
		SellerDTO result2 = session.selectOne(namespace + "selectSellerById", seller);
		System.out.println("result" + result2);
		return result2;
	}

	// 전체조회
	@Override
	public List<UserDTO> selectAll() throws Exception {
		System.out.println("good");
		return session.selectList(namespace + "selectAll");
	}

	// 로그인 여부 확인
	@Override
	public boolean loginCustomer(String inputId, String inputPw) {
		return true;
	}


	// 로그아웃
	@Override
	public String logout() {
		return null;
	}

	// 아이디, 비번 찾기
	@Override
	public UserDTO findUserById(String userId) {
		return session.selectOne("com.acorn.mapper.LoginMapper.findUserById", userId);
	}

	@Override
	public UserDTO findUserByEmail(String userEmail) {
		return session.selectOne("com.acorn.mapper.LoginMapper.findUserByEmail", userEmail);
	}

	@Override
	public void updatePassword(UserDTO userPw) {
		session.update("com.acorn.mapper.LoginMapper.updatePassword", userPw);
	}

	@Override
	public UserDTO findKakao(HashMap<String, Object> userInfo) {
		System.out.println("userInfo : "+ userInfo);
	
		UserDTO findUserInfo = session.selectOne(namespace+"findKakao", userInfo);
		System.out.println(findUserInfo);
		return  findUserInfo;
	}

	@Override
	public void kakaoInsert(HashMap<String, Object> userInfo) {
		System.out.println("다음 정보를 저장합니다+++++");
		System.out.println("RN:"+userInfo.get("phoneNumber"));
		System.out.println("RE:"+userInfo.get("email"));
		System.out.println("RA:"+userInfo.get("baseAddress"));
		System.out.println("RDA:"+userInfo.get("detailAddress"));
		
		session.insert(namespace+"kakaoInsert", userInfo);
	}
	
	public AddressDTO findAddrInfo(UserDTO userInfo) {
		
		return session.selectOne(namespace+"findAddrInfo", userInfo);
		
	}
	
	public AddressDTO addressInsert(UserDTO userInfo) {
		int userCode = (int) userInfo.getUserCode();
		String deliveryAddress = userInfo.getUserAddress();
		String detailDeliveryAddress = userInfo.getUserAddressDetail();
		
		// 추출한 정보를 이용하여 AddressDTO 객체 생성
		AddressDTO addressDTO = new AddressDTO();
		
		addressDTO.setUserCode(userCode);
		addressDTO.setDeliveryAddress(deliveryAddress);
		addressDTO.setDetailDeliveryAddress(detailDeliveryAddress);
		// AddressDTO 객체를 이용하여 데이터베이스에 삽입
		session.insert(namespace + "addressInsert", addressDTO);

		return addressDTO;
	}

}