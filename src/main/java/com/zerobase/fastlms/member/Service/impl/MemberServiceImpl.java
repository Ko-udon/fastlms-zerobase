package com.zerobase.fastlms.member.Service.impl;

import com.zerobase.fastlms.components.MailComponents;
import com.zerobase.fastlms.member.Service.MemberService;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.exception.MemberNotEmailAuthException;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;

    @Override
    public boolean register(MemberInput parameter) {

        Optional<Member> optionalMember=memberRepository.findById(parameter.getUserId());
        if(optionalMember.isPresent()){
            //현재 userId에 해당하는 데이터가 존재한다면, 아이디가 중복이라면
            return false;
        }
        String encPassword= BCrypt.hashpw(parameter.getPassword(),BCrypt.gensalt());


        String uuid=UUID.randomUUID().toString();

        Member member=Member.builder()
                .userId(parameter.getUserId())
                .userName(parameter.getUserName())
                .phone(parameter.getPhone())
                .password(encPassword)
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
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
    public boolean emailAuth(String uuid) {
        Optional<Member> optionalMember= memberRepository.findByEmailAuthKey(uuid);

        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member=optionalMember.get();

        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //넘어오는 값은 username, 이메일임
        Optional<Member>  optionalMember=memberRepository.findById(username);
        if(!optionalMember.isPresent()){
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }
        Member member=optionalMember.get();
        if(!member.isEmailAuthYn()){
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 해주세요.");
        }

        List<GrantedAuthority> grantedAuthorities=new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(member.getUserId(),member.getPassword(),grantedAuthorities);
    }
}
