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

/**
 * AUTH : SW
 * FUNCTION : Entity, Syncing with MariaDB's Table name and Column name
 * DATE : 2023.05.02
 * UPDATE( AUTH ) : -
 * 
 * <pre>
 * User 객체, MariaDB에서는 tbl_uesr로 사용중
 * </pre>
 * 
 * @Setter : don't using for this Class, Look up the _
 * @UserReqDto
 * @GeneratedValue(strategy : PrimaryKey Column
 * @Column : Checking for using Entity
 * 
 * @vc_user_hpp : for the Security, encrypt code from user_PhoneNumber
 * @vc_user_phone : for the Formal, data from user_PhoneNumber
 */

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
    @Column(name = "vc_user_name", nullable = false)
    private String vcUserName;
    @Column(name = "vc_user_email", unique = true)
    private String vcUserEmail;
    // @Column(name = "vc_user_password", nullable = false)
    // private String vcUserPassword;
    @Column(name = "vc_user_nickname", unique = true, nullable = false)
    private String vcUserNickname;
    @Column(name = "vc_user_hpp", unique = true, nullable = false)
    private String vcUserHpp;
    @Column(name = "vc_user_phone", unique = true, nullable = false)
    private String vcUserPhone;
    @Column(name = "vc_user_birth", nullable = false)
    private String vcUserBirth;
    @Column(name = "vc_user_sex", nullable = false)
    private String vcUserSex;
    @Column(name = "vc_user_profile_img")
    private String vcUserProfileImgurl;
    @CreationTimestamp
    @Column(name = "ts_user_joindate")
    private LocalDateTime tsUserJoindate;
    @Column(name = "ts_user_leave")
    private LocalDateTime tsUserLeave;
    @Column(name = "in_user_state", nullable = false)
    private int inUserState;
    @Column(name = "in_user_sign_by")
    private int inUserSignBy;

    @Builder
    public User(Long id, String username, String email, String nickname, String hpp, String phone,
            String birth, String sex, String profileImgurl, LocalDateTime joindate, LocalDateTime leave, int state,
            int signBy) {
        this.inUserId = id;
        // this.vcUserUid = userUid;
        this.vcUserName = username;
        this.vcUserEmail = email;
        // this.vcUserPassword = password;
        this.vcUserNickname = nickname;
        this.vcUserHpp = hpp;
        this.vcUserPhone = phone;
        this.vcUserBirth = birth;
        this.vcUserSex = sex;
        this.vcUserProfileImgurl = profileImgurl;
        this.tsUserJoindate = joindate;
        this.tsUserLeave = leave;
        this.inUserState = state;
        this.inUserSignBy = signBy;
    }

}
