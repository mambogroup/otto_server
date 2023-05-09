package com.mambo.otto.ottoserver.service;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mambo.otto.ottoserver.domain.User;
import com.mambo.otto.ottoserver.domain.UserRepository;
import com.mambo.otto.ottoserver.dto.SessionUser;
import com.mambo.otto.ottoserver.dto.UserRespDto;
import com.mambo.otto.ottoserver.dto.UserReqDto.UserUpdateReqDto;

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
 */

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository uR;
    private final HttpSession session;

    private SessionUser getSession() {
        return (SessionUser) session.getAttribute("sessionUser");
    }

    public UserRespDto findByUserId(Long id) {
        User userPs = uR.findByUserId(id).get();
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
        Optional<User> userOp = uR.findByUserId(updateReqDto.getInUserId());
        userOp.orElseThrow(() -> new RuntimeException("잘못된 접근입니다."));

        uR.update(updateReqDto);

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
