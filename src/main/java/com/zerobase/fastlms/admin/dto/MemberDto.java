package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.Member;
import lombok.*;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberDto {


    String userId;
    String userName;
    String phone;
    String password;
    LocalDateTime regDt;
    LocalDateTime uptDt;


    boolean emailAuthYn;
    LocalDateTime emailAuthDt;
    String emailAuthKey;

    String resetPasswordKey;
    LocalDateTime resetPasswordLimitDt;
    String userStatus;

    boolean adminYn;

    //컬럼 추가
    long totalCount;

    long seq;

    public static MemberDto of(Member member){
        return MemberDto.builder()
            .userId(member.getUserId())
            .userName(member.getUserName())
            .phone(member.getPhone())
            .regDt(member.getRegDt())
            .uptDt(member.getUpdDt())
            .emailAuthYn(member.isEmailAuthYn())
            .emailAuthDt(member.getEmailAuthDt())
            .emailAuthKey(member.getEmailAuthKey())
            .resetPasswordKey(member.getResetPasswordKey())
            .resetPasswordLimitDt(member.getResetPasswordLimitDt())
            .userStatus(member.getUserStatus())
            .adminYn(member.isAdminYn())
            .build();
    }

    public String getRegDtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return regDt != null ? regDt.format(formatter) : "";
    }

    public String getUdtDtText() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return uptDt != null ? uptDt.format(formatter) : "";
    }

}
