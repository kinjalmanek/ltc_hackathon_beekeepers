package ltc.hackathon.ce.flex.auth;

import ltc.hackathon.ce.flex.request.LoginRequest;
import ltc.hackathon.ce.flex.request.SignUpRequest;
import ltc.hackathon.ce.flex.request.VerifyRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginSuccess() {
        LoginRequest loginRequest = new LoginRequest(); // set properties as needed
        String responseMessage = "Login successful";

        when(authService.login(loginRequest)).thenReturn(responseMessage);

        ResponseEntity<String> response = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMessage, response.getBody());
    }

    @Test
    public void testLoginFailure() {
        LoginRequest loginRequest = new LoginRequest(); // set properties as needed
        String errorMessage = "Login failed";

        when(authService.login(loginRequest)).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<String> response = authController.login(loginRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Login failed : " + errorMessage, response.getBody());
    }

    @Test
    public void testSignUpSuccess() {
        SignUpRequest signUpRequest = new SignUpRequest(); // set properties as needed
        String responseMessage = "Sign up successful";

        when(authService.signUp(signUpRequest)).thenReturn(responseMessage);

        ResponseEntity<String> response = authController.signup(signUpRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMessage, response.getBody());
    }

    @Test
    public void testSignUpFailure() {
        SignUpRequest signUpRequest = new SignUpRequest(); // set properties as needed
        String errorMessage = "Sign up failed";

        when(authService.signUp(signUpRequest)).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<String> response = authController.signup(signUpRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Cannot sign up : " + errorMessage, response.getBody());
    }

    @Test
    public void testVerifySuccess() {
        VerifyRequest verifyRequest = new VerifyRequest(); // set properties as needed
        String responseMessage = "Verification successful";

        when(authService.verify(verifyRequest)).thenReturn(responseMessage);

        ResponseEntity<String> response = authController.verify(verifyRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMessage, response.getBody());
    }

    @Test
    public void testVerifyFailure() {
        VerifyRequest verifyRequest = new VerifyRequest(); // set properties as needed
        String errorMessage = "Verification failed";

        when(authService.verify(verifyRequest)).thenThrow(new RuntimeException(errorMessage));

        ResponseEntity<String> response = authController.verify(verifyRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Cannot verify : " + errorMessage, response.getBody());
    }
}
