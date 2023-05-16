package com.mambo.otto.ottoserver.util.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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
 * 
 * @create : 토큰생성이 필요한 로직에서 사용
 * @verify : 필터체인을 타는 로직 외에서 사용할 수 있음./ 자동로그인
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

    public static Long verifyId(String jwtToken) {
        DecodedJWT decodeJwt = JWT.require(Algorithm.HMAC512(JwtProps.SECRET)).build().verify(jwtToken);
        Long userId = decodeJwt.getClaim("userId").asLong();
        return userId;
    }

}
