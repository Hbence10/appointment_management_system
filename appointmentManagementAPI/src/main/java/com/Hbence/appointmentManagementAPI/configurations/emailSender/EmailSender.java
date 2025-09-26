package com.Hbence.appointmentManagementAPI.configurations.emailSender;

import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;
    private final String fromEmail = "sulisdolgok8@gmail.com";
    private final UserRepository userRepository;

    public void sendEmailAboutRegistration() {

    }

    public void sendVerificationCodeEmail(String toEmail, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.fromEmail);
        message.setSubject("Hitelesitő kód");
        message.setText("A hitelesitő kódja a jelszó változtatáshoz: " + verificationCode + ". \n A kód 5 percig aktív");
        message.setTo(toEmail);

        mailSender.send(message);
    }

    public void sendEmailAboutReservationCanceled() {

    }

    public void sendEmailAboutNews(){

    }
}
