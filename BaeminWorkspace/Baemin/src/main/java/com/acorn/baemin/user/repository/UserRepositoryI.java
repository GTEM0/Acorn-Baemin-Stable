package com.acorn.baemin.user.repository;

import com.acorn.baemin.domain.SellerDTO;
import com.acorn.baemin.domain.UserDTO;

public interface UserRepositoryI {

	public UserDTO selectCustomerInfo(String selone) throws Exception;

	public SellerDTO selectSellerInfo(String selone2) throws Exception;	
	
	// �մ� ����
	public void insertCustomer(UserDTO insertcustomer);
		
	// ����� ����
	public void insertSeller(SellerDTO insertseller);
	
	// �մ� ��������
	public void updateCustomer(UserDTO updatecustomer);
	
	// ����� ��������
	public void updateSeller(SellerDTO updateseller);

}
