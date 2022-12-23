package com.zerobase.fastlms.member.model;

import com.zerobase.fastlms.course.model.ServiceResult;
import lombok.Data;
import lombok.ToString;

@Data
@ToString

public class MemberInput {

    private String userId;
    private String userName;
    private String password;
    private String phone;

    private String newPassword;

    private String zipcode;
    private String addr;
    private String addrDetail;

}
