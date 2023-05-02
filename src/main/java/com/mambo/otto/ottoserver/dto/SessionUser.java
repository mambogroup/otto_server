package com.mambo.otto.ottoserver.dto;

import com.mambo.otto.ottoserver.domain.User;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SessionUser {
    private Long id;
    private String username;
    private String phoneNumber;

    public SessionUser(User user) {
        this.id = user.getInUserId();
        this.username = user.getVcUserName();
        this.phoneNumber = user.getVcUserHpp();
    }

    public User toEntity() {
        return User.builder().id(id).username(username).hpp(phoneNumber).build();
    }
}
