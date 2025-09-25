package com.Hbence.appointmentManagementAPI.configurations.emailSender;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;

    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sulisdolgok8@gmail.com");
        message.setTo("bzhalmai@gmail.com");
        message.setText("text");
        message.setSubject("subject");

        mailSender.send(message);
    }
}
