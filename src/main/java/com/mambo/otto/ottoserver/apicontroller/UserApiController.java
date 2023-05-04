package com.mambo.otto.ottoserver.apicontroller;

import org.springframework.web.bind.annotation.RestController;

import com.mambo.otto.ottoserver.dto.ResponseDto;
import com.mambo.otto.ottoserver.dto.UserReqDto.UserJoinReqDto;
import com.mambo.otto.ottoserver.dto.UserReqDto.UserUpdateReqDto;
import com.mambo.otto.ottoserver.dto.UserRespDto.JoinRespDto;
import com.mambo.otto.ottoserver.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService uS;

    @GetMapping("/user/{id}")
    public ResponseDto<?> findByUserId(@PathVariable(required = false) Long id) {
        return new ResponseDto<>(1, "", uS.findByUserId(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto<?> deleteUser(@PathVariable(required = false) Long id) {
        uS.deleteUser(id);
        return new ResponseDto<>(1, "", null);
    }

    @PutMapping("/s/update")
    public ResponseDto<?> updateUser(@RequestBody UserUpdateReqDto updateReqDto) {
        return new ResponseDto<>(1, "", uS.updateUser(updateReqDto));
    }

    @GetMapping("/logout")
    public String logout() {
        uS.logout();
        return "redirect:/loginForm";
    }

    @PostMapping("/join")
    public ResponseDto<?> save(@RequestBody UserJoinReqDto joinReqDto) {
        JoinRespDto joinUser = uS.save(joinReqDto);

        return new ResponseDto<>(1, joinReqDto.getVcUserName(), joinUser);
    }

    // @PostMapping("/login")
    // public ResponseDto<?> postMethodName(@RequestBody UserLoginReqDto
    // userLoginReqDto) {
    // SessionUser sUser = uS.login(userLoginReqDto);
    // session.setAttribute("sessionUser", sUser);

    // return new ResponseDto<>(1, "로그인 성공", sUser);
    // }

}