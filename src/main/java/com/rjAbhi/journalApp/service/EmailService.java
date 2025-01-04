package com.rjAbhi.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
//    yahan pe bean application.yml mein jab aap configure kariyega tabhi iska bean banega

    public void sendEmail(String to, String subject, String body){

        try {
            SimpleMailMessage simpleMailMessage =new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(body);
            javaMailSender.send(simpleMailMessage);

        } catch (Exception e) {
            log.error("Exception while sendEmail",e);
        }
    }

}
