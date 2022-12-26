package com.zerobase.fastlms.main.controller;

import com.zerobase.fastlms.admin.banner.dto.BannerDto;
import com.zerobase.fastlms.admin.banner.mapper.BannerMapper;
import com.zerobase.fastlms.admin.banner.model.BannerParam;
import com.zerobase.fastlms.admin.banner.service.BannerService;
import com.zerobase.fastlms.components.MailComponents;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.repository.MemberRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.RequestUtil;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@Data
public class MainController {

    private final MailComponents mailComponents;
    private final MemberRepository memberRepository;
    private final BannerMapper bannerMapper;
    private final BannerService bannerService;

    String userAgent;
    String clientIp;




    @RequestMapping("/")
    public String index(HttpServletRequest request,Model model, BannerParam parameter){
        //과제 1

        userAgent=request.getHeader("User-Agent");
        clientIp = request.getHeader("X-Forwarded-For");
        /*log.info("X-FORWARDED-FOR : " + ip);*/

        if (clientIp == null) {
            clientIp = request.getHeader("Proxy-Client-IP");
            log.info("Proxy-Client-IP : " + clientIp);
        }
        if (clientIp == null) {
            clientIp = request.getHeader("WL-Proxy-Client-IP");
            log.info("WL-Proxy-Client-IP : " + clientIp);
        }
        if (clientIp == null) {
            clientIp = request.getHeader("HTTP_CLIENT_IP");
            log.info("HTTP_CLIENT_IP : " + clientIp);
        }
        if (clientIp == null) {
            clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
            log.info("HTTP_X_FORWARDED_FOR : " + clientIp);
        }
        if (clientIp == null) {
            clientIp = request.getRemoteAddr();
            log.info("getRemoteAddr : "+clientIp);
        }
        log.info("Result : IP Address : "+clientIp);


        log.info(userAgent);
        log.info(clientIp);


        parameter.init();

        List<BannerDto> bannerList=bannerMapper.selectMainBanner(parameter);
        model.addAttribute("bannerList",bannerList);


        return "index";
    }

    /*@RequestMapping("/")
    public String index(){
        String email="ejko206@naver.com";
        String subject="안녕하세요. rhehddn 입니다.";
        String text="<p> 안녕하세요.</p><p>메일 보내기 테스트 중입니다.</p>";
        mailComponents.sendMail(email,subject,text);
        return "index"; 메일보내기 테스트
    }*/
    @RequestMapping("/error/denied")
    public String errorDenied(){
        return "error/denied";
    }

}
