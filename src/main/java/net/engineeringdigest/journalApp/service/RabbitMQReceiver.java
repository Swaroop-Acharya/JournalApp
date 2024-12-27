package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.config.RabbitMQConfig;
import net.engineeringdigest.journalApp.entity.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQReceiver {

    @Autowired
    private RabbitMQConfig rabbitMQConfig;

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(Email email){
        try{
            Thread.sleep(10000);
            emailService.sendEmail(email.getTo(),email.getSubject(),email.getBody());
            log.info("Mail sent to the customer");
        }catch (Exception e){
            log.error("Something went wrong while procesing the send mail at consumer level");
        }
    }

}
