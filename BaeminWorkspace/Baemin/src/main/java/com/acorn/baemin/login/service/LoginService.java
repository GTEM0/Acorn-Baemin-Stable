package com.acorn.baemin.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acorn.baemin.domain.SellerDTO;
import com.acorn.baemin.domain.UserDTO;
import com.acorn.baemin.login.repository.LoginRepositoryI;

@Service
public class LoginService {

    @Autowired
    private LoginRepositoryI loginRepositoryi;

    //�մ� �α���
    public UserDTO login(String userId, String userPw) {
        try {
            return loginRepositoryi.login(userId, userPw);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //����� �α���
    public SellerDTO login2(String sellerId, String sellerPw) {
        try {
            return loginRepositoryi.loginseller(sellerId, sellerPw);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
  
}
