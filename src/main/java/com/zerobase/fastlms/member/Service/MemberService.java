package com.zerobase.fastlms.member.Service;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface MemberService extends UserDetailsService {

    boolean register(MemberInput parameter);

    boolean emailAuth(String uuid); //이메일 인증

    boolean sendResetPassword(ResetPasswordInput parameter); //입력한 이메일로 비밀번호 초기화 정보를 전송하는 메소드


    boolean resetPassword(String id, String password);  //입력받은 uuid 에 대해서  password로 초기화

    boolean checkResetPassword(String uuid);  //입력받은 uuid값이 유효한지 확인

    List<MemberDto> list(MemberParam parameter);  //회원 목록 리턴(관리자 계정만 사용 가능)

    MemberDto detail(String userId);  //회원 상세 정보

    //회원 상태 변경
    boolean updateStatus(String userId, String userStatus);

    //회원 비밀번호 초기화
    boolean updatePassword(String userId, String password);

    ServiceResult updateMember(MemberInput parameter); //회원 정보 수정

    ServiceResult updateMemberPassword(MemberInput parameter); //회원 정보 페이지내 비밀번호 변경 기능


    ServiceResult withdraw(String userId,String password);  //회원을 탈퇴시켜 주는 로직


    boolean login(String userId); //로그인
}
