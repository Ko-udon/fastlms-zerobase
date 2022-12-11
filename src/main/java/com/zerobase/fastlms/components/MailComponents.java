package com.zerobase.fastlms.components;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;


@Component
@RequiredArgsConstructor
public class MailComponents {
    //dlfmafjzhzihocih
    private final JavaMailSender javaMailSender;

    public boolean sendMail(String mail,String subject,String text){
        boolean result=false;

        MimeMessagePreparator msg=new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage, true,"UTF-8");
                mimeMessageHelper.setTo(mail);
                mimeMessageHelper.setSubject(subject);
                mimeMessageHelper.setText(text,true);

            }
        };
        try{
            javaMailSender.send(msg);
            result=true;

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return result;

    }
    /*public void sendMailTest(){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setTo("ehddn2202@naver.com");
        message.setSubject("나 고동우");
        message.setText("자바 스프링 메일 보내기 테스트 ");

        javaMailSender.send(message);

    }*/
}
