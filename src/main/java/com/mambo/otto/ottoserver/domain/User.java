package com.mambo.otto.ottoserver.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "in_user_id")
    private Long inUserId;
    // @Column(name = "vc_user_uid", unique = true, nullable = false)
    // private String vcUserUid;
    @Column(name = "vc_user_name", unique = true, nullable = false)
    private String vcUserName;
    @Column(name = "vc_user_email", unique = true, nullable = false)
    private String vcUserEmail;
    // @Column(name = "vc_user_password", nullable = false)
    // private String vcUserPassword;
    @Column(name = "vc_user_nickname", unique = true, nullable = false)
    private String vcUserNickname;
    @Column(name = "vc_user_hpp", unique = true, nullable = false)
    private String vcUserHpp;
    @Column(name = "vc_user_birth", nullable = false)
    private String vcUserBirth;
    @Column(name = "vc_user_sex", nullable = false)
    private String vcUserSex;
    @Column(name = "vc_user_profile_img")
    private String vcUserProfileImgurl = "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Default_pfp.svg/340px-Default_pfp.svg.png";
    @CreationTimestamp
    @Column(name = "ts_user_joindate")
    private LocalDateTime tsUserJoindate;
    @Column(name = "ts_user_leave")
    private LocalDateTime tsUserLeave;
    @Column(name = "in_user_state", nullable = false)
    private int inUserState = 0; // default 0:활동중
    @Column(name = "in_user_sign_by")
    private int inUserSignBy;

    @Builder
    public User(Long id, String username, String email, String nickname, String hpp,
            String birth, String sex, String profileImgurl, LocalDateTime joindate, LocalDateTime leave, int state,
            int signBy) {
        this.inUserId = id;
        // this.vcUserUid = userUid;
        this.vcUserName = username;
        this.vcUserEmail = email;
        // this.vcUserPassword = password;
        this.vcUserNickname = nickname;
        this.vcUserHpp = hpp;
        this.vcUserBirth = birth;
        this.vcUserSex = sex;
        this.vcUserProfileImgurl = profileImgurl;
        this.tsUserJoindate = joindate;
        this.tsUserLeave = leave;
        this.inUserState = state;
        this.inUserSignBy = signBy;
    }

}
