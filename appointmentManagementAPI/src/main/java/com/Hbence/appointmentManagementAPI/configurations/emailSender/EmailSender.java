package com.Hbence.appointmentManagementAPI.configurations.emailSender;

import com.Hbence.appointmentManagementAPI.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender mailSender;
    private final String fromEmail = "sulisdolgok8@gmail.com";
    private final UserRepository userRepository;

    public void sendEmailAboutRegistration(String toEmail) {

    }

    public void sendVerificationCodeEmail(String toEmail, String verificationCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(this.fromEmail);
        message.setSubject("Hitelesitő kód");
        message.setText("A hitelesitő kódja a jelszó változtatáshoz: " + verificationCode + ". \nA kód 5 percig aktív");
        message.setTo(toEmail);

        mailSender.send(message);
    }

    public void sendEmailAboutReservationCanceled(String toEmail) {

    }

    public void sendEmailAboutNews(String toEmail) {

    }

    public void sendEmailAboutUserUpdate(String toEmail) {
    }

    public void sendEmailAboutReservation(String toEmail, String vCode, String firstName, String lastName) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(toEmail);
        helper.setSubject("Sikeres Foglalás");
//
        String emailText = "<h1>Sikeres foglalást rögzitettünk!</h1>";
        if (!vCode.equals("")) {
            emailText +=
                    "<h2>Tisztelt " + firstName + " " + lastName + "</h2>" +
                    "<p>A foglalását az allábbi linken tudja nyomon követni: <a href='http://localhost:4200/reservationCancel'>Foglalás nyomonkövetése</a></p>" +
                    "<p>A foglalás törléséhez az alábbi kódot tudja használni:<b> " + vCode + "</b></p>"
            ;
        }

        helper.setText(emailText, true);
        mailSender.send(message);
    }
}
