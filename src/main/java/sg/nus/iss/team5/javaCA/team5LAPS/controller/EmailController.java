package sg.nus.iss.team5.javaCA.team5LAPS.controller;

import sg.nus.iss.team5.javaCA.team5LAPS.model.EmailDetails;
import sg.nus.iss.team5.javaCA.team5LAPS.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    
    @Autowired private EmailService emailService;
 
    @PostMapping("/sendMail")
    public String sendMail(@RequestBody EmailDetails details) {
        
        String status = emailService.sendNotifEmail(details);
        return status;
    }
    
}
