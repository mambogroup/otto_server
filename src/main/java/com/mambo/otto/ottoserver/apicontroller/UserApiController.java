package com.mambo.otto.ottoserver.apicontroller;

import org.springframework.web.bind.annotation.RestController;

import com.mambo.otto.ottoserver.domain.User;
import com.mambo.otto.ottoserver.dto.ResponseDto;
import com.mambo.otto.ottoserver.dto.UserReqDto.UserJoinReqDto;
import com.mambo.otto.ottoserver.dto.UserRespDto.JoinRespDto;
import com.mambo.otto.ottoserver.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService uS;

    @PostMapping("/join")
    public ResponseDto<?> save(@RequestBody UserJoinReqDto joinReqDto) {
        JoinRespDto joinUser = uS.save(joinReqDto);

        return new ResponseDto<>(1, "로그인이 완료되었습니다    " + joinReqDto.getVcUserName() + " 님 안녕하세요", joinUser);
    }

}
