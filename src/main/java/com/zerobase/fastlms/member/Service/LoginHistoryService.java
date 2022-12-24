package com.zerobase.fastlms.member.Service;

import com.zerobase.fastlms.admin.dto.LoginHistoryDto;
import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface LoginHistoryService  {

    LoginHistoryDto detail(String userId);  //회원 상세 정보

    List<LoginHistoryDto> list(MemberParam parameter);
}
