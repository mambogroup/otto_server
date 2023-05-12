package com.mambo.otto.ottoserver.util;

import java.io.IOException;
import java.io.PrintWriter;

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
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mambo.otto.ottoserver.domain.User;
import com.mambo.otto.ottoserver.dto.ResponseDto;
import com.mambo.otto.ottoserver.dto.SessionUser;

import lombok.RequiredArgsConstructor;

/**
 * AUTH : SW
 * FUNCTION : 권한이 필요한 메서드 수행 시 거치는 필터링
 * DATE : 2023.05.02
 * UPDATE( AUTH ) : 2023.05.08( SW )
 * 
 * <pre>
 * Request 의 header 에 토큰값을 해싱하여 메서드를 수행할 권한이 있는지 체크
 * </pre>
 * 
 * @SHA256 : convert( encrypt ) the Input data to crypto code
 * @secretkey : that needs to Verifying user's JWT TOKEN,
 * @customJwtResponse : response with Header data
 */

@RequiredArgsConstructor
public class JwtAuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String jwtToken = req.getHeader("Authorization");
        if (jwtToken == null) {
            customResponse("JWT토큰이 없어서 인가할 수 없습니다.", resp);
            return;
        }

        jwtToken = jwtToken.replace("Bearer ", "");
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512("맘보")).build().verify(jwtToken);
            System.out.println("1");
            System.out.println("2");
            Long userId = decodedJWT.getClaim("userId").asLong();
            String phonenumber = decodedJWT.getClaim("phonenumber").asString();
            SessionUser sessionUser = new SessionUser(User.builder().id(userId).hpp(phonenumber).build());
            HttpSession session = req.getSession();
            session.setAttribute("sessionUser", sessionUser);
        } catch (Exception e) {
            customResponse("토큰 검증 실패", resp);// 핸들링 커스텀
            return;
        }

        chain.doFilter(req, resp);
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
