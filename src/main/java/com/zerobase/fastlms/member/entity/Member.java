package com.zerobase.fastlms.member.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Member implements MemberCode {
    @Id
    private String userId;

    private String userName;
    private String phone;
    private String password;
    private LocalDateTime regDt;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;
    private String emailAuthKey;

    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDt;
    private LocalDateTime updDt;

    //관리자 여부 지정
    //회원에 따를 ROLE을 지정할것인가
    //준회원/정회원/관리자
    //ROLE_SEMI_USER, USER, SPECIAL_USER, ADMIN
    //private
    private boolean adminYn;

    private String userStatus;  //이용자의 상태, 정지 이용자 or 사용자



}
