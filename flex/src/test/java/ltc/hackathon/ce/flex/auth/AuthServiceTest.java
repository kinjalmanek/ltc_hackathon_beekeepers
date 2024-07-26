package ltc.hackathon.ce.flex.auth;

import ltc.hackathon.ce.flex.request.LoginRequest;
import ltc.hackathon.ce.flex.request.SignUpRequest;
import ltc.hackathon.ce.flex.request.VerifyRequest;
import ltc.hackathon.ce.flex.util.EmailGenerator;
import ltc.hackathon.ce.flex.util.OtpGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private EmailGenerator emailGenerator;

    @Mock
    private OtpGenerator otpGenerator;

    @Test
    public void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password123");

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        String response = authService.login(loginRequest);

        assertEquals("Success", response);
    }

    @Test
    public void testLoginFailure() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("wrongpassword");

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            authService.login(loginRequest);
        });

        assertEquals("No value present", thrown.getMessage());
    }

    @Test
    public void testSignUpSuccess() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password123");
        signUpRequest.setConfirmPassword("password123");

        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        existingUser.setPassword(null); // Ensure it's a new user

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));
        when(otpGenerator.generateOtp()).thenReturn("123456");

        String response = authService.signUp(signUpRequest);

        verify(emailGenerator).sendOtpEmail("test@example.com", "Account Verification", "123456");
        assertEquals("Sign Up Successful", response);
    }

    @Test
    public void testSignUpUserAlreadyExists() {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail("test@example.com");
        signUpRequest.setPassword("password123");
        signUpRequest.setConfirmPassword("password123");

        User existingUser = new User();
        existingUser.setEmail("test@example.com");
        existingUser.setPassword("existingpassword");

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(existingUser));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            authService.signUp(signUpRequest);
        });

        assertEquals("User already exists", thrown.getMessage());
    }

    @Test
    public void testVerifySuccess() {
        VerifyRequest verifyRequest = new VerifyRequest();
        verifyRequest.setEmail("test@example.com");
        verifyRequest.setOtp("123456");

        User user = new User();
        user.setEmail("test@example.com");
        user.setOtp("123456");

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        String response = authService.verify(verifyRequest);

        assertEquals("Success", response);
        verify(userRepo).save(user);
    }

    @Test
    public void testVerifyFailure() {
        VerifyRequest verifyRequest = new VerifyRequest();
        verifyRequest.setEmail("test@example.com");
        verifyRequest.setOtp("wrongotp");

        User user = new User();
        user.setEmail("test@example.com");
        user.setOtp("123456");

        when(userRepo.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            authService.verify(verifyRequest);
        });

        assertEquals("Invalid OTP", thrown.getMessage());
    }
}
