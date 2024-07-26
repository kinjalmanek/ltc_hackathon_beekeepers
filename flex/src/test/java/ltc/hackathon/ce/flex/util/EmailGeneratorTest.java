package ltc.hackathon.ce.flex.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmailGeneratorTest {

    @InjectMocks
    private EmailGenerator emailGenerator;

    @Mock
    private JavaMailSender mailSender;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendSignUpEmail() {
        // Arrange
        String to = "test@example.com";
        String subject = "Added to portfolio";

        // Act
        emailGenerator.sendSignUpEmail(to, subject);

        // Assert
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(to);
        expectedMessage.setSubject(subject);
        expectedMessage.setText("You have been added as a companion. Please sign up/login to access the account and view details");

        verify(mailSender).send(expectedMessage);
    }

    @Test
    public void testSendOtpEmail() {
        // Arrange
        String to = "test@example.com";
        String subject = "Account Verification";
        String otp = "123456";

        // Act
        emailGenerator.sendOtpEmail(to, subject, otp);

        // Assert
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(to);
        expectedMessage.setSubject(subject);
        expectedMessage.setText("OTP for signup is : " + otp);

        verify(mailSender).send(expectedMessage);
    }
}
