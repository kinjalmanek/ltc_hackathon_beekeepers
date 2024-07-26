//package ltc.hackathon.ce.flex.config;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.test.context.TestPropertySource;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@TestPropertySource(properties = {
//        "spring.mail.host=smtp.example.com",
//        "spring.mail.port=587",
//        "spring.mail.username=user@example.com",
//        "spring.mail.password=password"
//})
//@Import(EmailConfig.class) // Import the EmailConfig class
//@ExtendWith(MockitoExtension.class)
//public class EmailConfigTest {
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Test
//    public void testJavaMailSenderConfiguration() {
//        assertThat(javaMailSender).isInstanceOf(JavaMailSenderImpl.class);
//
//        JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl) javaMailSender;
//        assertThat(javaMailSenderImpl.getHost()).isEqualTo("smtp.example.com"); // Replace with expected value
//        assertThat(javaMailSenderImpl.getPort()).isEqualTo(587); // Replace with expected value
//        assertThat(javaMailSenderImpl.getUsername()).isEqualTo("user@example.com"); // Replace with expected value
//        assertThat(javaMailSenderImpl.getPassword()).isEqualTo("password"); // Replace with expected value
//
//        // Check JavaMailSender properties
//        assertThat(javaMailSenderImpl.getJavaMailProperties().getProperty("mail.smtp.starttls.enable"))
//                .isEqualTo("true");
//        assertThat(javaMailSenderImpl.getJavaMailProperties().getProperty("mail.smtp.auth")).isEqualTo("true");
//    }
//}
