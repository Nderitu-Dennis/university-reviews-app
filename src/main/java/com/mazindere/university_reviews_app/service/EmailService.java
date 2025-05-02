package com.mazindere.university_reviews_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String token) {
        String subject = "Verify your UniGuide Email";
        String link = "http://localhost:8080/verify?token=" + token;
        String messageBody = "Hi,\n\nPlease click the link below to verify your email:\n" + link + "\n\nThanks,\nUniGuide Team.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(messageBody);

        mailSender.send(message);
    }
}
