package com.acorn.baemin.login.controller;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHashingExample {

    public static void main(String[] args) {
        // 사용자가 입력한 비밀번호
        String userPassword = "121213q!";
        // 솔트 생성
        String salt = BCrypt.gensalt();

        // 비밀번호 해싱
        String hashedPassword = BCrypt.hashpw(userPassword, salt);
        
        
         System.out.println("hashedPassword="+ hashedPassword);

        // 저장된 hashedPassword와 비교 (로그인 시)
        String inputPassword = "121213q!"; // 사용자가 입력한 비밀번호

        if (BCrypt.checkpw(inputPassword, hashedPassword)) {
            System.out.println("비밀번호 일치");
        } else {
            System.out.println("비밀번호 불일치");
        }
    }
}