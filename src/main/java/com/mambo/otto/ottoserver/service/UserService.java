package com.mambo.otto.ottoserver.service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mambo.otto.ottoserver.domain.User;
import com.mambo.otto.ottoserver.domain.UserRepository;
import com.mambo.otto.ottoserver.dto.SessionUser;
import com.mambo.otto.ottoserver.dto.UserRespDto;
import com.mambo.otto.ottoserver.dto.UserReqDto.UserUpdateReqDto;
import com.mambo.otto.ottoserver.dto.UserRespDto.TokenLoginRespDto;
import com.mambo.otto.ottoserver.util.exception.CustomApiException;
import com.mambo.otto.ottoserver.util.jwt.JwtProcess;
import com.mambo.otto.ottoserver.util.jwt.JwtProps;

import lombok.RequiredArgsConstructor;

/**
 * AUTH : SW
 * FUNCTION : Any logics can serving here
 * DATE : 2023.05.02
 * UPDATE( AUTH ) : -
 * 
 * <pre>
 * 비즈니스 로직 담당
 * </pre>
 * 
 * @enterToken : 자동로그인 요청 시 아예 토큰을 재 발행 하면서, 토큰 기간 연장
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository uR;
    private final HttpSession session;

    private SessionUser getSession() {
        return (SessionUser) session.getAttribute("sessionUser");
    }

    public TokenLoginRespDto enterToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("authorization");
        if (jwtToken == null) {
            throw new CustomApiException("토큰이 헤더에 없습니다.", HttpStatus.ACCEPTED);
        }
        jwtToken = jwtToken.replace(JwtProps.AUTH, "");

        Long userId = JwtProcess.verifyId(jwtToken);
        User userPS = uR.findByUserId(userId)
                .orElseThrow(() -> new CustomApiException("토큰 검증 실패", HttpStatus.ACCEPTED));

        String newToken = "Bearer " + JwtProcess.create(userPS);

        return new TokenLoginRespDto(new UserRespDto(userPS), newToken);
    }

    public UserRespDto findByUserId(Long id) {
        User userPs = uR.findByUserId(id).orElseThrow(() -> new RuntimeException("로그인 정보 확인 불가"));
        UserRespDto userDto = new UserRespDto(userPs);

        return userDto;
    }

    @Transactional
    public String deleteUser(Long id) {
        if (!getSession().getId().equals(id)) {
            return "다시 로그인하여 주십시오";
        }
        try {
            uR.findByUserId(id).get();
            uR.deleteBy(id);
        } catch (Exception e) {
            return "삭제할 수 없습니다.";
        }
        session.invalidate();
        return "삭제 성고옹";

    }

    @Transactional
    public UserRespDto updateUser(UserUpdateReqDto updateReqDto) {

        if (!updateReqDto.getInUserId().equals(getSession().getId())) {
            return null;

        }

        uR.update(updateReqDto.toEntity());

        Optional<User> userOp = uR.findByUserId(updateReqDto.getInUserId());

        UserRespDto userRespDto = new UserRespDto(userOp.get());
        return userRespDto;
    }

    public String logout() {
        session.invalidate();
        return "로그아웃";

    }

    // @Transactional
    // public JoinRespDto save(UserJoinReqDto joinReqDto) {
    // String encPhoneNum = sha256.encrypt(joinReqDto.getVcUserHpp());

    // joinReqDto.setVcUserPhone(joinReqDto.getVcUserHpp());
    // joinReqDto.setVcUserHpp(encPhoneNum);
    // joinReqDto.setVcUserNickname(joinReqDto.getVcUserName());

    // User userPs = uR.save(joinReqDto.toEntity());

    // return new JoinRespDto(userPs);
    // }

    // @Transactional()
    // public SessionUser login(UserLoginReqDto userLoginReqDto) {
    // Optional<User> userOp =
    // uR.findByUserPhoneNumber(userLoginReqDto.getVcUserHpp());

    // if (userOp.isEmpty()) {
    // throw new RuntimeException("일치하는 정보가 없습니다 " +
    // userLoginReqDto.getVcUserHpp());
    // }

    // User userPS = userOp.get();

    // if (!userPS.getVcUserHpp().equals(userLoginReqDto.getVcUserHpp())) {
    // throw new RuntimeException("번호가 맞는지 다시 확인해주세요");
    // }
    // return new SessionUser(userPS);

    // }

}
