package net.engineeringdigest.journalApp.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    public  void emailTest(){
      emailService.sendMail("swaroopa802@gmail.com","Hi Iam Swaroop","Hi buddy");
    }

    @Test
    public void emailTestWithAttachemetn(){
        emailService.sendMailWithAttachment("swaroopa802@gmail.com","Order recipet","Your order place here is your order receipt","C:\\Users\\swaro\\OneDrive\\Documents\\Loan payment\\Oct.pdf");
    }
}
