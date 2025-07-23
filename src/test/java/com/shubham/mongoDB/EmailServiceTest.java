package com.shubham.mongoDB;

import com.shubham.mongoDB.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    void testSendEmail(){
        emailService.sendEmail("shubhamliman302001@gmail.com","Test Emaail", "This is a test mail");
    }
}
