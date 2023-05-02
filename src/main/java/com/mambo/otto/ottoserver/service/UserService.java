package com.mambo.otto.ottoserver.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mambo.otto.ottoserver.domain.User;
import com.mambo.otto.ottoserver.domain.UserRepository;
import com.mambo.otto.ottoserver.dto.UserReqDto.UserJoinReqDto;
import com.mambo.otto.ottoserver.dto.UserRespDto.JoinRespDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository uR;

    @Transactional
    public JoinRespDto save(UserJoinReqDto joinReqDto) {

        User userPs = uR.save(joinReqDto.toEntity());
        return new JoinRespDto(userPs);
    }

}
