package com.zerobase.fastlms.main.controller;

import com.zerobase.fastlms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final MailComponents mailComponents;

    @RequestMapping("/")
    public String index(){
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
