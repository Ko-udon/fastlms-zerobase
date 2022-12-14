package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.Member;
import lombok.*;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

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

    boolean emailAuthYn;
    LocalDateTime emailAuthDt;
    String emailAuthKey;

    String resetPasswordKey;
    LocalDateTime resetPasswordLimitDt;

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
            .emailAuthYn(member.isEmailAuthYn())
            .emailAuthDt(member.getEmailAuthDt())
            .emailAuthKey(member.getEmailAuthKey())
            .resetPasswordKey(member.getResetPasswordKey())
            .resetPasswordLimitDt(member.getResetPasswordLimitDt())
            .adminYn(member.isAdminYn())
            .build();
    }

}
