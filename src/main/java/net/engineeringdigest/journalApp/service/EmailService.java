package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String to, String subject, String body){
        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(to);
            simpleMailMessage.setText(body);
            javaMailSender.send(simpleMailMessage);
        }catch (Exception e){
            log.error("Exception while sending a registration email",e.getMessage());
        }
    }

    public void sendMailWithAttachment(String to, String subject,String body,String filePath){
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body);
            File file = new File(filePath);
            mimeMessageHelper.addAttachment(file.getName(),file);
            javaMailSender.send(mimeMessage);

        }catch(Exception e){
            log.error("Exception while sending the mail with attachment"+e.getMessage());
        }
    }
}
