package ltc.hackathon.ce.flex.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailGenerator {
    @Autowired
    JavaMailSender mailSender;

    public void sendSignUpEmail(String to, String subject) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText("You have been added as a trustee. Please sign up/login to access the account and view details");
        mailSender.send(message);
    }

    public void sendOtpEmail(String to, String subject, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText("OTP for signup is : " + otp);
        mailSender.send(message);
    }
}
