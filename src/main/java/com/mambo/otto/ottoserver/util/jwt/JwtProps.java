package com.mambo.otto.ottoserver.util.jwt;

/**
 * AUTH : SW
 * FUNCTION : JWT 토큰 발행 시 필요한 데이터 값들
 * DATE : 2023.05.02
 * UPDATE( AUTH ), msg : 2023.05.15( SW ), 토큰 생성 메서드 리팩터링
 * 
 * <pre>
 * 공통 관리
 * </pre>
 * 
 * @EXPIRESAT : 1000 * 60 * 60 = 1 hour
 * @SECRET : that needs to Verifying user's JWT TOKEN,
 * @HEADER : response Header
 */

public interface JwtProps {
    public static final String SUBJECT = "맘보오또로또";
    public static final String SECRET = "맘보";
    public static final String AUTH = "Bearer ";
    public static final String HEADER = "Authorization";
    public static final Integer EXPIRESAT = 1000 * 60 * 60 * 24 * 10; // 10일
}