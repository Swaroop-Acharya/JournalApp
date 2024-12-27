package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.config.RabbitMQConfig;
import net.engineeringdigest.journalApp.entity.Email;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQConfig rabbitMQConfig;


    public void sendMessage(Email email){
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME,email);
    }
}
