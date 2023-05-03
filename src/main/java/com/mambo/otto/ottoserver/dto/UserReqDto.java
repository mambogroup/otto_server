package com.mambo.otto.ottoserver.dto;

import com.mambo.otto.ottoserver.domain.User;

import lombok.Getter;
import lombok.Setter;

public class UserReqDto {

    @Getter
    @Setter
    public static class UserJoinReqDto {
        // private String vcUserUid;
        private String vcUserName;
        private String vcUserEmail;
        // private String vcUserPassword;
        private String vcUserNickname;
        private String vcUserHpp;
        private String vcUserBirth;
        private String vcUserSex;
        private int inUserSignBy;

        public User toEntity() {
            return User.builder().username(vcUserName).email(vcUserEmail).nickname(vcUserNickname)
                    .hpp(vcUserHpp).birth(vcUserBirth).sex(vcUserSex).signBy(inUserSignBy).build();
        }
    }

    @Getter
    @Setter
    public static class UserLoginReqDto {
        private String vcUserHpp;
    }
}
