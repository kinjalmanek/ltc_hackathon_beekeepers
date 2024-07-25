package ltc.hackathon.ce.flex.auth;


import ltc.hackathon.ce.flex.request.LoginRequest;
import ltc.hackathon.ce.flex.request.SignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            return new ResponseEntity<String>(authService.login(loginRequest), HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Login failed : " + e.getMessage());
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignUpRequest signUpRequest) {
        try {
            return new ResponseEntity<String>(authService.signUp(signUpRequest), HttpStatus.OK);
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body("Cannot sign up : " + e.getMessage());
        }
    }
}
