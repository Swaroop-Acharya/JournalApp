package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.config.RabbitMQConfig;
import net.engineeringdigest.journalApp.entity.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQReceiver1 {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void sendMessage1(Email email){
        try{
            log.info("{}this mail work will be handled by{}", email.getTo(), getClass());
            Thread.sleep(5000);
            emailService.sendEmail(email.getTo(),email.getSubject(),email.getBody());
            log.info("Mail sent to the customer{}", getClass());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
