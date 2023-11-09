package com.acorn.baemin.login.repository;

import java.util.List;

import com.acorn.baemin.domain.SellerDTO;
import com.acorn.baemin.domain.UserDTO;

public interface LoginRepositoryI {
	
	public List<UserDTO> selectAll() throws Exception;
			
	public boolean loginCustomer(String inputId, String inputPw) throws Exception;

	// �α���
	public String customerlogin();

	// Ȩ
	public String home();

	// �α׾ƿ�
	public String logout();

	//�մ� �α���
	UserDTO login(String userId, String userPw);

	//����� �α���
	SellerDTO loginseller(String sellerId, String sellerPw);
	

	
	
	
	
}
