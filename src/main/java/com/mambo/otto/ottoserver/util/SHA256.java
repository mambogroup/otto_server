package com.mambo.otto.ottoserver.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Component;

/**
 * AUTH : SW
 * FUNCTION : 암호화
 * DATE : 2023.05.02
 * UPDATE( AUTH ) : -
 * 
 * <pre>
 * 데이터( String )를 받아 암호화 하는데 쓰임, PW
 * </pre>
 * 
 * @/login : PostMapping method with the login values Flitering
 * @/s : Flitering with that method values on any Controllers
 */

@Component
public class SHA256 {

    public String encrypt(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(text.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("비밀번호 해싱이 실패하였습니다.");
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

}