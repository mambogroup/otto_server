package com.mambo.otto.ottoserver.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mambo.otto.ottoserver.domain.User;
import com.mambo.otto.ottoserver.domain.UserRepository;
import com.mambo.otto.ottoserver.dto.UserReqDto.UserJoinReqDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository uR;

    @Transactional
    public void save(UserJoinReqDto joinReqDto) {
        User user = joinReqDto.toEntity();
        uR.save(user);
    }

}
