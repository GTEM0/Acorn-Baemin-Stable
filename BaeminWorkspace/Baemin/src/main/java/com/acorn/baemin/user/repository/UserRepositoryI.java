package com.acorn.baemin.user.repository;

import com.acorn.baemin.domain.SellerDTO;
import com.acorn.baemin.domain.UserDTO;

public interface UserRepositoryI {

	public UserDTO selectCustomerInfo(String selone) throws Exception;

	public SellerDTO selectSellerInfo(String selone2) throws Exception;	
	
	// �մ� ����
	public void insertUser(UserDTO insertuser);
		
	// ����� ����
	public void insertSeller(SellerDTO insertseller);
	
	// �մ� ��������
	public void updateUser(UserDTO updateuser);
	
	// ����� ��������
	public void updateSeller(SellerDTO updateseller);
	
}
