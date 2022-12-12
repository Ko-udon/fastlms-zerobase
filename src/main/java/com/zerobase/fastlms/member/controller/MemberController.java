package com.zerobase.fastlms.member.controller;


import com.zerobase.fastlms.member.Service.MemberService;
import com.zerobase.fastlms.member.entity.Member;
import com.zerobase.fastlms.member.model.MemberInput;
import com.zerobase.fastlms.member.model.ResetPasswordInput;
import com.zerobase.fastlms.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/find-password")
    public String findPassword(){

        return "member/find_password";
    }

    @PostMapping("/member/find-password")
    public String findPasswordSubmit(
            Model model
            ,ResetPasswordInput resetPasswordInput){
        boolean result=false;
        try{
            result=memberService.sendResetPassword(resetPasswordInput);
        }catch (Exception e){

        }
        //boolean result=memberService.sendResetPassword(resetPasswordInput);

        model.addAttribute("result",result);

        return "member/find_password_result";

    }

    @RequestMapping("/member/login")
    public String login(){
        //System.out.println("request GET!");
        return "member/login";
    }

    @GetMapping("/member/register")
    public String register(){
        System.out.println("request GET!");
        return "member/register";
    }
    @PostMapping("/member/register")
    public String registerSubmit(Model model, HttpServletRequest request,
                                 MemberInput parameter){


        System.out.println(parameter.toString());

        boolean result=memberService.register(parameter);
        model.addAttribute("result",result);
        return "member/register_complete";
    }

    @GetMapping("/member/email-auth")
    public String emailAuth(Model model,HttpServletRequest request){
        String uuid=request.getParameter("id");
        System.out.println(uuid);

        boolean result=memberService.emailAuth(uuid);
        model.addAttribute("result",result);

        return "member/email-auth";
    }

    @GetMapping("/member/info")
    public String memberInfo(){
        return "member/info";
    }

    @GetMapping("/member/reset/password")
    public String resetPassword(Model model, HttpServletRequest request){
        String uuid=request.getParameter("id");

        boolean result=memberService.checkResetPassword(uuid);
        model.addAttribute("result",result);

        return "member/reset_password";
    }

    @PostMapping("/member/reset/password")
    public String resetPasswordSubmit(Model model,
                                      ResetPasswordInput resetPasswordInput){
        boolean result=false;
        try{
            result=memberService.resetPassword(resetPasswordInput.getId(),resetPasswordInput.getPassword());
        }catch (Exception e){

        }

        model.addAttribute("result",result);

        return "member/reset_password_result";
    }

}
