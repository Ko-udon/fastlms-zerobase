package com.zerobase.fastlms.admin.dto;

import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.entity.Member;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginHistoryDto {




    String userId;

    //컬럼 추가
    long totalCount;

    long seq;

    //과제2
    LocalDateTime lastLogin;
    String clientIp;
    String userAgent;

    public static LoginHistoryDto of(LoginHistory loginHistory){
        return LoginHistoryDto.builder()
            .userId(loginHistory.getUserId())
            .lastLogin(loginHistory.getLastLogin())  //마지막 접속 날짜
            .clientIp(loginHistory.getClientIp())
            .userAgent(loginHistory.getUserAgent())
            .build();
    }


    public String getLastLogin() {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return lastLogin != null ? lastLogin.format(formatter) : "";
    }

}
