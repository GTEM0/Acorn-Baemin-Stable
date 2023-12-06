package com.acorn.baemin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	int userCode;
	String userId;
	String hashedPassword;
	String userPw;
	String userName;
	String userNickname;
	String userPhone;
	String userEmail;
	String userBirth;
	int userGender;
	String userPostCode;
	String userAddress;
	String userAddressDetail;	
	int userStatus;
	int mail_auth;
	String mail_key;
	
}
