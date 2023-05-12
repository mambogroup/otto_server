package com.mambo.otto.ottoserver.dto;

import com.mambo.otto.ottoserver.domain.User;

import lombok.Getter;
import lombok.Setter;

/**
 * AUTH : SW
 * FUNCTION : only can Access to Entity Object
 * DATE : 2023.05.02
 * UPDATE( AUTH ) : -
 * 
 * <pre>
 * 클라이언트 요청 시 도메인의 엔티티 객체 대신 사용함
 * </pre>
 * 
 * @toEntity : using for Convert to Object to querying
 */
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
        private String vcUserPhone;
        private String vcUserBirth;
        private String vcUserSex;
        private int inUserSignBy;
        private String vcUserProfileImgurl = "https://png.pngtree.com/png-clipart/20190619/original/pngtree-profile-line-black-icon-png-image_4008155.jpg";
        private int inUserState;

        public User toEntity() {
            return User.builder().phone(vcUserPhone).username(vcUserName).email(vcUserEmail)
                    .profileImgurl(vcUserProfileImgurl).nickname(vcUserNickname)
                    .hpp(vcUserHpp).birth(vcUserBirth).sex(vcUserSex).signBy(inUserSignBy).state(inUserState).build();
        }
    }

    @Getter
    @Setter
    public static class UserUpdateReqDto {
        private Long inUserId;
        private String vcUserName;
        private String vcUserEmail;
        private String vcUserNickname;
        private String vcUserHpp;
        private String vcUserBirth;
        private String vcUserSex;
        private int inUserSignBy;

        public User toEntity() {
            return User.builder().id(inUserId).username(vcUserName).email(vcUserEmail).nickname(vcUserNickname)
                    .hpp(vcUserHpp).birth(vcUserBirth).sex(vcUserSex).signBy(inUserSignBy).build();
        }
    }

    @Getter
    @Setter
    public static class UserLoginReqDto {
        private String vcUserHpp;
    }
}
