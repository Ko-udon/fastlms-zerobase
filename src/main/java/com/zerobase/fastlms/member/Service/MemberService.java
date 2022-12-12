package com.zerobase.fastlms.member.Service;

import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    boolean register(MemberInput parameter);

    boolean emailAuth(String uuid); //이메일 인증

    boolean sendResetPassword(ResetPasswordInput parameter); //입력한 이메일로 비밀번호 초기화 정보를 전송하는 메소드


    boolean resetPassword(String id, String password);  //입력받은 uuid 에 대해서  password로 초기화

    boolean checkResetPassword(String uuid);  //입력받은 uuid값이 유효한지 확인
}
