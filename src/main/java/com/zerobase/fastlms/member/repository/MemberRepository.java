package com.zerobase.fastlms.member.repository;

import com.zerobase.fastlms.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,String> {  //tablename, key
    
    Optional<Member> findByEmailAuthKey(String emailAuthKey);  //메서드를 정의만 하면 스프링이 알아서 구현체를 만들어줌

    Optional<Member> findByUserIdAndUserName(String userId, String userName);

    Optional<Member> findByResetPasswordKey(String resetPasswordKey);


}
