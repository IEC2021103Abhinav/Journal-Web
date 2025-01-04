package com.rjAbhi.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendEmail("abhinavsunny0006@gmail.com",
                "Testing with Java",
                "Hiii madam, I want to see you yaar");
    }

}
