package com.mambo.otto.ottoserver.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mambo.otto.ottoserver.domain.User;
import com.mambo.otto.ottoserver.domain.UserRepository;
import com.mambo.otto.ottoserver.dto.ResponseDto;
import com.mambo.otto.ottoserver.dto.SessionUser;
import com.mambo.otto.ottoserver.dto.UserReqDto.UserLoginReqDto;
import com.mambo.otto.ottoserver.util.jwt.JwtProcess;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * AUTH : SW
 * FUNCTION : 클라이언트의 로그인 요청을 받은 것을 핸들링 하는 클래스
 * DATE : 2023.05.02
 * UPDATE( AUTH ), msg : 2023.05.15( SW ), 토큰 생성 메서드 리팩터링
 * 
 * <pre>
 * !!!
 * @JwtAuthorizationAccessRegister 로 대체
 * !!!
 * </pre>
 * 
 * @SHA256 : convert( encrypt ) the Input data to crypto code
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

        if (!req.getMethod().equals("POST")) {
            customResponse("로그인시에는 post요청을 해야 합니다.", resp);
            return;
        }

        ObjectMapper om = new ObjectMapper();
        UserLoginReqDto loginReqDto = om.readValue(req.getInputStream(), UserLoginReqDto.class);
        log.debug("디버그 : " + loginReqDto.getVcUserHpp());

        SHA256 sh = new SHA256();
        String encPhoneNum = sh.encrypt(loginReqDto.getVcUserHpp());
        loginReqDto.setVcUserHpp(encPhoneNum);

        // TODO : User 객체 변경 필요
        Optional<User> userOP = userRepository.findByUserPhoneNumber(loginReqDto.getVcUserHpp());

        User userPS = userOP.get();
        if (!userPS.getVcUserHpp().equals(encPhoneNum)) {
            customResponse("번호를 다시 확인해 주십시오", resp);
            return;
        }

        String jwtToken = JwtProcess.create(userPS);

        SessionUser sessionUser = new SessionUser(User.builder().id(userPS.getInUserId()).build());
        HttpSession session = req.getSession();
        session.setAttribute("sessionUser", sessionUser);

        customJwtResponse(jwtToken, userPS, resp);

        // chain.doFilter(req, resp);
    }

    private void customJwtResponse(String token, User userPS, HttpServletResponse resp)
            throws IOException, JsonProcessingException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setHeader("Authorization", "Bearer " + token);
        PrintWriter out = resp.getWriter();
        resp.setStatus(200);
        ResponseDto<?> responseDto = new ResponseDto<>(200, "정상적으로 로그인이 되었습니다", new SessionUser(userPS));
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
