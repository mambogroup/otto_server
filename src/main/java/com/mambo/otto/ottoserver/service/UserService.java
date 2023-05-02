package com.mambo.otto.ottoserver.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mambo.otto.ottoserver.domain.User;
import com.mambo.otto.ottoserver.domain.UserRepository;
import com.mambo.otto.ottoserver.dto.SessionUser;
import com.mambo.otto.ottoserver.dto.UserReqDto.UserJoinReqDto;
import com.mambo.otto.ottoserver.dto.UserReqDto.UserLoginReqDto;
import com.mambo.otto.ottoserver.dto.UserRespDto.JoinRespDto;
import com.mambo.otto.ottoserver.util.SHA256;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository uR;
    private final SHA256 sha256;

    @Transactional
    public JoinRespDto save(UserJoinReqDto joinReqDto) {

        String encPassword = sha256.encrypt(joinReqDto.getVcUserPassword());
        System.out.println("테스트 : " + encPassword);
        joinReqDto.setVcUserPassword(encPassword);

        User userPs = uR.save(joinReqDto.toEntity());

        return new JoinRespDto(userPs);
    }

    @Transactional()
    public SessionUser login(UserLoginReqDto userLoginReqDto) {
        Optional<User> userOp = uR.findByUserPhoneNumber(userLoginReqDto.getVcUserHpp());

        if (userOp.isEmpty()) {
            throw new RuntimeException("일치하는 정보가 없습니다 " + userLoginReqDto.getVcUserHpp());
        }

        User userPS = userOp.get();

        if (!userPS.getVcUserHpp().equals(userLoginReqDto.getVcUserHpp())) {
            throw new RuntimeException("번호가 맞는지 다시 확인해주세요");
        }
        return new SessionUser(userPS);

    }

}
