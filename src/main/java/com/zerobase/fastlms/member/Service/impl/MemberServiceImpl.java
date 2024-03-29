package com.zerobase.fastlms.member.Service.impl;

import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.mapper.MemberMapper;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.components.MailComponents;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.main.controller.MainController;
import com.zerobase.fastlms.member.Service.MemberService;
import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.entity.MemberCode;
import com.zerobase.fastlms.member.exception.MemberNotEmailAuthException;
import com.zerobase.fastlms.member.exception.MemberStopUserException;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.repository.LoginHistoryRepository;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.util.PasswordUtils;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.jdbc.Null;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor

public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    private final LoginHistoryRepository loginHistoryRepository;
    private final MailComponents mailComponents;

    private final MemberMapper memberMapper;

    @Override
    public boolean register(MemberInput parameter) {

        Optional<Member> optionalMember=memberRepository.findById(parameter.getUserId());
        if(optionalMember.isPresent()){
            //현재 userId에 해당하는 데이터가 존재한다면, 아이디가 중복이라면
            return false;
        }
        String encPassword= BCrypt.hashpw(parameter.getPassword(),BCrypt.gensalt());  //비밀번호 해쉬 암호화


        String uuid=UUID.randomUUID().toString();

        Member member=Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .phone(parameter.getPhone())
                .password(encPassword)
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .userStatus(Member.MEMBER_STATUS_REQ)
                .lastLogin(LocalDateTime.now())  //마지막 접속일
                .build();
        memberRepository.save(member);
        
        /*member.setUserId(parameter.getUserId());
        member.setUserName(parameter.getUserName());
        member.setPhone(parameter.getPhone());
        member.setPassword(parameter.getPassword());
        member.setRegDt(LocalDateTime.now());
        member.setEmailAuthYn(false);
        member.setEmailAuthKey(uuid);  //이메일 인증키, 사용자마다 랜덤하게*/ //이처럼 전부 작성하기 보단 위에 builder를 사용함 보통

        String email= parameter.getUserId();
        String subject="fastlms 사이트 가입을 축하드립니다.";
        String text="<p>fastlms 사이트 가입을 축하드립니다. <p><p>아래 링크를 통해 인증하여 가입을 완료 하세요. </p>"
                +"<div><a target='_blank' href='http://localhost:8080/member/email-auth?id=" + uuid + "'>가입 완료 </a></div>";
        mailComponents.sendMail(email,subject,text);
        // email-auth?id=" + uuid + "'

        

        return true;
    }

    @Override
    public boolean emailAuth(String uuid) { //이메일 인증 활성화
        Optional<Member> optionalMember= memberRepository.findByEmailAuthKey(uuid);

        if(!optionalMember.isPresent()){
            return false;
        }
        Member member=optionalMember.get();

        if(member.isEmailAuthYn()){
            return false; //이미 해당 계정이 활성화 되었으므로 재 활성화시 false
        }

        member.setUserStatus(Member.MEMBER_STATUS_ING);
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter) {
        Optional<Member>  optionalMember=memberRepository.findByUserIdAndUserName(parameter.getUserId(), parameter.getUserName());
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member=optionalMember.get();

        String uuid=UUID.randomUUID().toString();

        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusDays(1)); //초기화 후 재설정 제한까지 기간, ㅎ+하루
        memberRepository.save(member);


        String email= parameter.getUserId();
        String subject="[fastlms] 비밀번호 초기화 메일 입니다.";
        String text="<p>fastlms 비밀번호 초기화 메일 입니다. <p><p>아래 링크를 통해 비밀번호를 초기화 해주세요. </p>"
                +"<div><a target='_blank' href='http://localhost:8080/member/reset/password?id="+ uuid + "'>비밀번호 초기화 링크 </a></div>";
        mailComponents.sendMail(email,subject,text);


        return true;
    }

    @Override
    public boolean resetPassword(String uuid, String password) {
        Optional<Member>  optionalMember=memberRepository.findByResetPasswordKey(uuid);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member=optionalMember.get();
        //초기화 날짜 제한 체크
        if(member.getResetPasswordLimitDt()==null){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("유효한 날짜가 아닙니다. ");
        }


        String encPassword=BCrypt.hashpw(password,BCrypt.gensalt());
        member.setPassword(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean checkResetPassword(String uuid) {
        Optional<Member>  optionalMember=memberRepository.findByResetPasswordKey(uuid);
        if(!optionalMember.isPresent()){
            return false;
        }
        Member member=optionalMember.get();
        //초기화 날짜 제한 체크
        if(member.getResetPasswordLimitDt()==null){
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        if(member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("유효한 날짜가 아닙니다. ");
        }


        return true;
    }

    @Override
    public List<MemberDto> list(MemberParam parameter) {

        long totalCount= memberMapper.selectListCount(parameter);

        List<MemberDto>list=memberMapper.selectList(parameter);

        if(!CollectionUtils.isEmpty(list)){
            int i=0;
            for(MemberDto x: list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart()-i);
                i++;
            }
        }
        return list;

        //return memberRepository.findAll();
    }

    @Override
    public MemberDto detail(String userId) {
        Optional<Member> optionalMember=memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            return null;
        }
        Member member=optionalMember.get();


        return MemberDto.of(member);
    }

    @Override
    public boolean updateStatus(String userId, String userStatus) {
        Optional<Member>  optionalMember=memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member=optionalMember.get();

        member.setUserStatus(userStatus);

        memberRepository.save(member);

        return true;
    }

    @Override
    public boolean updatePassword(String userId, String password) {
        Optional<Member>  optionalMember=memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member=optionalMember.get();

        String encPassword=BCrypt.hashpw(password,BCrypt.gensalt());

        member.setPassword(encPassword);
        memberRepository.save(member);

        return true;
    }

    public String getIpAddr() {
        String ip_addr = null;
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = sra.getRequest();

        ip_addr = request.getHeader("X-Forwarded-For");
        if (ip_addr == null) {
            ip_addr = request.getHeader("Proxy-Client-IP");
        }
        if (ip_addr == null) {
            ip_addr = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip_addr == null) {
            ip_addr = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip_addr == null) {
            ip_addr = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip_addr == null) {
            ip_addr = request.getRemoteAddr();
        }
        return ip_addr;
    }
    public String getUserAgent() {
        String userAgent = null;
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = sra.getRequest();

        userAgent = request.getHeader("User-Agent");

        return userAgent;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //넘어오는 값은 username, 이메일임

        Optional<Member>  optionalMember=memberRepository.findById(username);



        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member=optionalMember.get();


        if(Member.MEMBER_STATUS_REQ.equals(member.getUserStatus())){
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 해주세요.");
        }
        if(Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())){
            throw new MemberStopUserException("정지된 회원 입니다.");
        }

        if(Member.MEMBER_STATUS_WITHDRAW.equals(member.getUserStatus())){
            throw new MemberStopUserException("탈퇴된 계정입니다.");
        }


        member.setLastLogin(LocalDateTime.now());   //로그인시 접속한 시간 나타내기

        member.setClientIp(getIpAddr());        // 접속 IP
        member.setUserAgent(getUserAgent());   // 사용자 agent

        memberRepository.save(member);



        LoginHistory loginHistory=LoginHistory.builder()
            .userId(member.getUserId())
            .clientIp(member.getClientIp())
            .userAgent(member.getUserAgent())
            .lastLogin(member.getLastLogin())
            .build();



        loginHistoryRepository.save(loginHistory);






        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));  //디폴트 롤
        
        if(member.isAdminYn()){
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));  //관리자 롤
        }

        return new User(member.getUserId(),member.getPassword(),grantedAuthorities);
    }

    @Override
    public ServiceResult updateMemberPassword(MemberInput parameter) {
        String userId=parameter.getUserId();

        Optional<Member> optionalMember=memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            return new ServiceResult(false,"회원 정보가 존재하지 않습니다.");
        }

        Member member=optionalMember.get();



        if(!PasswordUtils.equals(parameter.getPassword(),member.getPassword())){
            return new ServiceResult(false,"비밀번호가 일치하지 않습니다.");
        }

        String encPassword=PasswordUtils.encPassword(parameter.getNewPassword());
        member.setPassword(encPassword);
        memberRepository.save(member);

        return new ServiceResult(true);
    }

    @Override
    public ServiceResult withdraw(String userId,String password) {


        Optional<Member> optionalMember=memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            return new ServiceResult(false,"회원 정보가 존재하지 않습니다.");
        }

        Member member=optionalMember.get();

        if(!PasswordUtils.equals(password,member.getPassword())) {
            return new ServiceResult(false,"비밀번호가 일치하지 않습니다.");
        }

        member.setUserName("삭제된 회원");
        member.setPhone("");
        member.setRegDt(null);

        member.setUpdDt(null);

        member.setEmailAuthYn(false);
        member.setEmailAuthDt(null);
        member.setEmailAuthKey("");
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        member.setUserStatus(MemberCode.MEMBER_STATUS_WITHDRAW);
        member.setZipcode("");
        member.setAddr("");
        member.setAddrDetail("");
        memberRepository.save(member);

        return new ServiceResult();
    }


    @Override
    public ServiceResult updateMember(MemberInput parameter) {
        String userId=parameter.getUserId();

        Optional<Member> optionalMember=memberRepository.findById(userId);
        if(!optionalMember.isPresent()){
            return new ServiceResult(false,"회원 정보가 존재하지 않습니다.");
        }

        Member member=optionalMember.get();
        member.setPhone(parameter.getPhone());
        member.setUpdDt(LocalDateTime.now());
        member.setZipcode(parameter.getZipcode());
        member.setAddr(parameter.getAddr());
        member.setAddrDetail(parameter.getAddrDetail());


        memberRepository.save(member);
        return new ServiceResult(true);
    }

    @Override
    public boolean login(String userId) {
        Optional<Member> optionalMember=memberRepository.findById(userId);
        Member member=optionalMember.get();
        member.setLastLogin(LocalDateTime.now());

        memberRepository.save(member);

        return true;
    }

}
