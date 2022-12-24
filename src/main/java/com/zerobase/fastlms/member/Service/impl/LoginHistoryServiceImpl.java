package com.zerobase.fastlms.member.Service.impl;

import com.zerobase.fastlms.admin.dto.LoginHistoryDto;
import com.zerobase.fastlms.admin.dto.MemberDto;
import com.zerobase.fastlms.admin.mapper.LoginHistoryMapper;
import com.zerobase.fastlms.admin.mapper.MemberMapper;
import com.zerobase.fastlms.admin.model.MemberParam;
import com.zerobase.fastlms.components.MailComponents;
import com.zerobase.fastlms.course.model.ServiceResult;
import com.zerobase.fastlms.member.Service.LoginHistoryService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
@RequiredArgsConstructor

public class LoginHistoryServiceImpl implements LoginHistoryService {
    private final MemberRepository memberRepository;

    private final LoginHistoryRepository loginHistoryRepository;
    private final MailComponents mailComponents;

    private final LoginHistoryMapper loginHistoryMapper;


    @Override
    public LoginHistoryDto detail(String userId) {
        Optional<LoginHistory> optionalLoginHistory=loginHistoryRepository.findById(userId);
        if(!optionalLoginHistory.isPresent()){
            return null;
        }
        LoginHistory loginHistory=optionalLoginHistory.get();



        return LoginHistoryDto.of(loginHistory);
    }

    @Override
    public List<LoginHistoryDto> list(MemberParam parameter) {
        long totalCount= loginHistoryMapper.selectListCount(parameter);


        List<LoginHistoryDto>list=loginHistoryMapper.selectList(parameter);

        if(!CollectionUtils.isEmpty(list)){
            int i=0;
            for(LoginHistoryDto x: list){
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart()-i);
                i++;
            }
        }
        return list;
    }


}
