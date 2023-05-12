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
import com.mambo.otto.ottoserver.dto.UserRespDto;
import com.mambo.otto.ottoserver.dto.UserReqDto.UserJoinReqDto;
import com.mambo.otto.ottoserver.util.jwt.JwtProcess;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * AUTH : SW
 * FUNCTION : 회원가입을 하면서 바로 로그인이 가능하게끔 하는 로직
 * DATE : 2023.05.09
 * UPDATE( AUTH ), msg : 2023.05.15( SW ), 토큰 생성 메서드 리팩터링
 * 
 * <pre>
 * DB의 유저 정보를 확인하고 유효한 사용자일 경우 로그인 처리와 JWT토큰을 발행하여 header로 반환
 * 1. /login 요청시
 * 2. post 요청시
 * 3. username, password (json)
 * 4. db확인
 * 5. 회원가입 처리
 * 6. 토큰 생성
 * </pre>
 * 
 * @설명 : <회원가입 동시에 로그인, >
 * 
 * @SHA256 : convert( encrypt ) the Input data to crypto code
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
        if (joinReqDto.getVcUserNickname().isEmpty()) {
            joinReqDto.setVcUserNickname(joinReqDto.getVcUserName());
        }

        // 유저네임 있는지 체크
        // TODO : User 객체 변경 필요
        Optional<User> userOP1 = userRepository.findByUserPhoneNumber(joinReqDto.getVcUserHpp());
        Optional<User> userOP2 = userRepository.findByNameBirth(joinReqDto.getVcUserName(),
                joinReqDto.getVcUserBirth());

        if (userOP1.isEmpty()) {
            if (userOP2.isEmpty()) {
                userRepository.save(joinReqDto.toEntity());
                userOP1 = userRepository.findByUserPhoneNumber(joinReqDto.getVcUserHpp());
            }
        }

        User userPS = userOP1.get();

        String jwtToken = JwtProcess.create(userPS);

        SessionUser sessionUser = new SessionUser(User.builder().id(userPS.getInUserId()).build());
        HttpSession session = req.getSession();
        session.setAttribute("sessionUser", sessionUser);

        UserRespDto userRespDto = new UserRespDto(userOP1.get());

        customJwtResponse(jwtToken, userRespDto, resp);

    }

    private void customJwtResponse(String token, UserRespDto userPS, HttpServletResponse resp)
            throws IOException, JsonProcessingException {
        resp.setContentType("application/json; charset=utf-8");
        resp.setHeader("Authorization", "Bearer " + token);
        PrintWriter out = resp.getWriter();
        resp.setStatus(200);
        ResponseDto<?> responseDto = new ResponseDto<>(200, "회원가입 후 로그인이 정상적으로 되었습니다", userPS);
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
