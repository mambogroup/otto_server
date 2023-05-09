package com.mambo.otto.ottoserver.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mambo.otto.ottoserver.domain.User;
import com.mambo.otto.ottoserver.domain.UserRepository;
import com.mambo.otto.ottoserver.dto.ResponseDto;
import com.mambo.otto.ottoserver.dto.SessionUser;
import com.mambo.otto.ottoserver.dto.UserReqDto.UserJoinReqDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * AUTH : SW
 * FUNCTION : 회원가입을 하면서 바로 로그인이 가능하게끔 하는 로직
 * DATE : 2023.05.09
 * UPDATE( AUTH ) : -
 * 
 * <pre>
 * DB의 유저 정보를 확인하고 유효한 사용자일 경우 로그인 처리와 JWT토큰을 발행하여 header로 반환
 * 1. /login 요청시
 * 2. post 요청시
 * 3. username, password (json)
 * 4. db확인
 * 5. 토큰 생성
 * </pre>
 * 
 * @exprie : 1000 * 60 * 60 = 1 hour
 * @SHA256 : convert( encrypt ) the Input data to crypto code
 * @secretkey : that needs to Verifying user's JWT TOKEN,
 * @customJwtResponse : response with Header
 */

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationAccessRegister implements Filter {

    private final UserRepository userRepository; // DI (FilterConfig 주입받음)

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (!req.getMethod().equals("POST")) {
            customResponse("회원가입 시 post요청을 해야 합니다.", resp);
            return;
        }

        ObjectMapper om = new ObjectMapper();
        UserJoinReqDto joinReqDto = om.readValue(req.getInputStream(), UserJoinReqDto.class);
        log.debug("디버그 : " + joinReqDto.getVcUserHpp());

        SHA256 sh = new SHA256();
        String encPhoneNum = sh.encrypt(joinReqDto.getVcUserHpp());
        joinReqDto.setVcUserPhone(joinReqDto.getVcUserHpp());
        joinReqDto.setVcUserHpp(encPhoneNum);
        joinReqDto.setVcUserNickname(joinReqDto.getVcUserName());

        userRepository.save(joinReqDto.toEntity());

        // 유저네임 있는지 체크
        Optional<User> userOP = userRepository.findByUserPhoneNumber(joinReqDto.getVcUserHpp());

        if (userOP.isEmpty()) {
            customResponse("회원가입이 정상적으로 처리되지 않았습니다.", resp);
            return;
        }

        User userPS = userOP.get();

        Date expire = new Date(System.currentTimeMillis() + (1000 * 60 * 60));

        String jwtToken = JWT.create()
                .withSubject("맘보오또로또")
                .withExpiresAt(expire)
                .withClaim("phonenumber", userPS.getVcUserHpp())
                .withClaim("userId", userPS.getInUserId())
                .sign(Algorithm.HMAC512("맘보")); // TODO : 시크릿값 보안파일로 관리 필요

        SessionUser sessionUser = new SessionUser(User.builder().id(userPS.getInUserId()).build());
        HttpSession session = req.getSession();
        session.setAttribute("sessionUser", sessionUser);

        customJwtResponse(jwtToken, userPS, resp);

    }

    private void customJwtResponse(String token, User userPS, HttpServletResponse resp)
            throws IOException, JsonProcessingException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setHeader("Authorization", "Bearer " + token);
        PrintWriter out = resp.getWriter();
        resp.setStatus(200);
        ResponseDto<?> responseDto = new ResponseDto<>(1, "200", new SessionUser(userPS));
        ObjectMapper om = new ObjectMapper();
        String body = om.writeValueAsString(responseDto);
        out.println(body);
        out.flush();
    }

    private void customResponse(String msg, HttpServletResponse resp) throws IOException, JsonProcessingException {
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter out = resp.getWriter();
        resp.setStatus(400);
        ResponseDto<?> responseDto = new ResponseDto<>(-1, msg, null);
        ObjectMapper om = new ObjectMapper();
        String body = om.writeValueAsString(responseDto);
        out.println(body);
        out.flush();
    }

}
