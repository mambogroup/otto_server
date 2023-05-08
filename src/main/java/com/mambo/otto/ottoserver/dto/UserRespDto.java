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
 */

@Getter
@Setter
public class UserRespDto {
    private Long inUserId;
    private String vcUserName;
    private String vcUserNickname;
    private String vcUserEmail;
    private String vcUserPhone;
    private String vcUsrProfileImgUrl;
    private String vcUesrBirth;
    private String vcUserSex;

    public UserRespDto(User user) {
        this.inUserId = user.getInUserId();
        this.vcUserName = user.getVcUserName();
        this.vcUserNickname = user.getVcUserNickname();
        this.vcUserEmail = user.getVcUserEmail();
        this.vcUserPhone = user.getVcUserPhone();
        this.vcUsrProfileImgUrl = user.getVcUserProfileImgurl();
        this.vcUesrBirth = user.getVcUserBirth();
        this.vcUserSex = user.getVcUserSex();
    }

    @Getter
    @Setter
    public static class JoinRespDto {
        private Long getInUserId;
        private String getVcUserName;

        public JoinRespDto(User user) {
            this.getInUserId = user.getInUserId();
            this.getVcUserName = user.getVcUserName();
        }
    }

}
