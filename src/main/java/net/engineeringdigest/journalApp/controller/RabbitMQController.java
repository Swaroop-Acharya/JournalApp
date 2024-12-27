package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.Email;
import net.engineeringdigest.journalApp.service.EmailService;
import net.engineeringdigest.journalApp.service.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/mq")
public class RabbitMQController {
    @Autowired
    private RabbitMQSender rabbitMQSender;

    @PostMapping("/sendMail")
    public String sendMessage(@RequestBody Email email){
        rabbitMQSender.sendMessage(email);
        return "Your message is currently being sent to the messagebroker";
    }
}
