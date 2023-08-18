package sg.nus.iss.team5.javaCA.team5LAPS.service;

import sg.nus.iss.team5.javaCA.team5LAPS.model.EmailDetails;

public interface EmailService {
    
    String sendNotifEmail(EmailDetails details);

}