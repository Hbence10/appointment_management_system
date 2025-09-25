package com.Hbence.appointmentManagementAPI.configurations.emailSender;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;
    private final String fromEmail = "";

    public void sendEmailAboutRegistration() {

    }

    public void sendVerificationCodeEmail(String toEmail, String verificationEmail) {

    }

    public void sendEmailAboutReservationCanceled(){

    }
}
