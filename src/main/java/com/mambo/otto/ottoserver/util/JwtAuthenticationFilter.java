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
import com.mambo.otto.ottoserver.dto.UserReqDto.UserLoginReqDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * AUTH : SW
 * FUNCTION : 클라이언트의 로그인 요청을 받은 것을 핸들링 하는 클래스
 * DATE : 2023.05.02
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
public class JwtAuthenticationFilter implements Filter {

    private final UserRepository userRepository; // DI (FilterConfig 주입받음)

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        // Post요청이 아닌것을 거부
        if (!req.getMethod().equals("POST")) {
            customResponse("로그인시에는 post요청을 해야 합니다.", resp);
            return;
        }

        // Body 값 받기
        ObjectMapper om = new ObjectMapper();
        UserLoginReqDto loginReqDto = om.readValue(req.getInputStream(), UserLoginReqDto.class);
        log.debug("디버그 : " + loginReqDto.getVcUserHpp());

        SHA256 sh = new SHA256();
        String encPassword = sh.encrypt(loginReqDto.getVcUserHpp());
        loginReqDto.setVcUserHpp(encPassword);

        // 유저네임 있는지 체크
        Optional<User> userOP = userRepository.findByUserPhoneNumber(loginReqDto.getVcUserHpp());

        if (userOP.isEmpty()) {
            customResponse("입력하신 번호는 서비스에 등록되어있지 않습니다.", resp);
            return;
        }

        User userPS = userOP.get();
        if (!userPS.getVcUserHpp().equals(encPassword)) {
            customResponse("번호를 다시 확인해 주십시오", resp);
            return;
        }

        Date expire = new Date(System.currentTimeMillis() + (1000 * 60 * 60));

        String jwtToken = JWT.create()
                .withSubject("맘보오또로또")
                .withExpiresAt(expire)
                .withClaim("phonenumber", userPS.getVcUserHpp())
                .withClaim("userId", userPS.getInUserId())
                .sign(Algorithm.HMAC512("맘보"));
        log.debug("디버그 : " + jwtToken);

        SessionUser sessionUser = new SessionUser(User.builder().id(userPS.getInUserId()).build());
        HttpSession session = req.getSession();
        session.setAttribute("sessionUser", sessionUser);

        // JWT토큰 응답
        customJwtResponse(jwtToken, userPS, resp);

        // chain.doFilter(req, resp);
    }

    private void customJwtResponse(String token, User userPS, HttpServletResponse resp)
            throws IOException, JsonProcessingException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setHeader("Authorization", "Bearer " + token);
        PrintWriter out = resp.getWriter();
        resp.setStatus(200);
        ResponseDto<?> responseDto = new ResponseDto<>(1, "성공", new SessionUser(userPS));
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
