package com.mambo.otto.ottoserver.util.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mambo.otto.ottoserver.domain.User;

/**
 * AUTH : SW
 * FUNCTION : 토큰 생성
 * DATE : 2023.05.15
 * UPDATE( AUTH ) : -
 * 
 * <pre>
 * 토큰 생성 및 토큰관련 메서드
 * </pre>
 */

public class JwtProcess {
    // TODO : User 객체 변경 필요
    public static String create(User user) {
        String jwtToken = JWT.create()
                .withSubject(user.getVcUserName())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProps.EXPIRESAT))
                .withClaim("phonenumber", user.getVcUserHpp())
                .withClaim("userId", user.getInUserId())
                .sign(Algorithm.HMAC512(JwtProps.SECRET));

        return jwtToken;
    }

}
