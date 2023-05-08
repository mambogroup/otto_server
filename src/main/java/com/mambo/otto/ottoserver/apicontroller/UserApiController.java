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

/**
 * AUTH : SW
 * FUNCTION : Recieve Clients Request with JSON
 * DATE : 2023.05.02
 * UPDATE( AUTH ) : 2023.05.04( SW )
 * 
 * <pre>
 * 사용자와 관련된 요청 관리 객체
 * 
 * login() : JwtAuthenticationFilter.java 에서 핸들링 {Exception 포함}
 * </pre>
 * 
 * @PathVariable : needs get @RequestBody instead.
 * @RequiredArgsConstructor : Dependency Injection.
 * @return : ResponseDto code, message, data / Clients get JSON data.
 */

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService uS;

    @GetMapping("/user/{id}")
    public ResponseDto<?> findByUserId(@PathVariable(required = false) Long id) {
        return new ResponseDto<>(1, "200", uS.findByUserId(id));
    }

    @DeleteMapping("s/delete/{id}")
    public ResponseDto<?> deleteUser(@PathVariable(required = false) Long id) {
        return new ResponseDto<>(1, "200", uS.deleteUser(id));
    }

    @PutMapping("/s/update")
    public ResponseDto<?> updateUser(@RequestBody UserUpdateReqDto updateReqDto) {
        return new ResponseDto<>(1, "200", uS.updateUser(updateReqDto));
    }

    @GetMapping("/logout")
    public ResponseDto<?> logout() {
        return new ResponseDto<>(1, "200", uS.logout());
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