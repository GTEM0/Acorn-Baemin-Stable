package com.acorn.baemin.user.repository;

import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.acorn.baemin.domain.SellerDTO;
import com.acorn.baemin.domain.UserDTO;

@Repository
public class UserRepository implements UserRepositoryI {

	@Autowired
	private SqlSession session;

	@Autowired
	private static String namespace = "com.acorn.LoginMapper.";

	// �մ� �Ϻ� ��ȸ
	@Override
	public UserDTO selectCustomerInfo(String selone) throws Exception {
		return session.selectOne(namespace + "selectCustomerInfo", selone);
	}

	// ����� �Ϻ� ��ȸ
	@Override
	public SellerDTO selectSellerInfo(String selone2) throws Exception {
		return session.selectOne(namespace + "selectSellerInfo", selone2);
	}


	// �մ� ȸ�� ����
	@Override
	public void insertUser(UserDTO insertuser) {
		session.insert(namespace + "customerSignup", insertuser);
	}
	
	// ����� ȸ�� ����
	@Override
	public void insertSeller(SellerDTO sellerinsert) {
		session.insert(namespace + "insertSeller", sellerinsert);
	}

	// �մ� ���� ����
	@Override
	public void updateUser(UserDTO updateuser) {
		session.update(namespace + "updateUser", updateuser);
	}

	// ����� ���� ����
	@Override
	public void updateSeller(SellerDTO sellerupdate) {
		session.update(namespace + "updateSeller", sellerupdate);
	}

//	@Update("update user_tbl set userNickname = #{userNickname}, userPhone = #{userPhone}, userPostCode = #{userPostCode}, userAddress = #{userAddress}, userAddressDetail = #{userAddressDetail}, userEmail = #{userEmail} where userCode = #{userCode}")
//	public void customerUpdate(UserDTO userDTO) {
//
//	}

	
}
