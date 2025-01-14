package com.acorn.baemin.login.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.acorn.baemin.domain.AddressDTO;
import com.acorn.baemin.domain.SellerDTO;
import com.acorn.baemin.domain.UserDTO;

public interface LoginRepositoryI {

	public List<UserDTO> selectAll() throws Exception;

	public boolean loginCustomer(String inputId, String inputPw) throws Exception;

	// 아이디 찾기 기능(손님, 사장님)
	String findCustomerId(Map<String, Object> params);

	String findSellerId(Map<String, Object> params);

	// 손님 비밀번호 찾기
	String findCustomerPassword(Map<String, Object> params);

	// 사장님 비밀번호 찾기
	String findSellerPassword(Map<String, Object> params);

	// 로그아웃
	public String logout();

	// 손님 로그인
	UserDTO login(String userId, String hashedPassword);

	// 사장님 로그인
	SellerDTO loginseller(String sellerId, String sellerPw);

	// 아이디 비번 찾기
	UserDTO findUserById(String userId);

	UserDTO findUserByEmail(String userEmail);

	void updatePassword(UserDTO user);
	
	// 카카오에서 받은 정보가 db에 있는지 확인
	UserDTO findKakao(HashMap<String, Object> userInfo);
	
	// db에 없으면 insert
	void kakaoInsert(HashMap<String, Object> userInfo);
	
}