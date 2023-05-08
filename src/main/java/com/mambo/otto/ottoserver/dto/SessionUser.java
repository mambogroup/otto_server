package com.mambo.otto.ottoserver.dto;

import com.mambo.otto.ottoserver.domain.User;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTH : SW
 * FUNCTION : only has needs data
 * DATE : 2023.05.02
 * UPDATE( AUTH ) : -
 * 
 * <pre>
 * 세션 저장용 데이터, 보안과 상관없이 사용할 수 있는 데이터만 사용
 * </pre>
 * 
 */

@Setter
@Getter
public class SessionUser {
    private Long id;
    private String username;
    private String phoneNumber;

    public SessionUser(User user) {
        this.id = user.getInUserId();
        this.username = user.getVcUserName();
        this.phoneNumber = user.getVcUserPhone();
    }

    public User toEntity() {
        return User.builder().id(id).username(username).hpp(phoneNumber).build();
    }
}
