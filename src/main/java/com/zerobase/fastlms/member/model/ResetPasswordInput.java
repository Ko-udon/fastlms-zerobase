package com.zerobase.fastlms.member.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import java.time.LocalDateTime;

@ToString
@Data
public class ResetPasswordInput {
    private String userId;
    private String userName;

    private String id;
    private String password;

}
