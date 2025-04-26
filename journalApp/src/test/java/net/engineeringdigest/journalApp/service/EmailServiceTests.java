package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService ;

    @Test
    public void testSendMail(){
        emailService.setJavaMailSender("to_send_email","Testing java mail sender","hi  how are you sir");
        System.out.print("Email sent to : sent_email");

    }
}
