package com.mambo.otto.ottoserver.apicontroller;

import org.springframework.web.bind.annotation.RestController;

import com.mambo.otto.ottoserver.domain.User;
import com.mambo.otto.ottoserver.dto.UserReqDto.UserJoinReqDto;
import com.mambo.otto.ottoserver.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService uS;

    @PostMapping("/join")
    public String postMethodName(@RequestBody UserJoinReqDto joinReqDto) {
        uS.save(joinReqDto);

        return "성공";
    }

}
