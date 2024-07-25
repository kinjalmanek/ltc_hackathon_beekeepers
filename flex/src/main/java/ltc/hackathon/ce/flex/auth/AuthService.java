package ltc.hackathon.ce.flex.auth;

import ltc.hackathon.ce.flex.request.LoginRequest;
import ltc.hackathon.ce.flex.request.SignUpRequest;
import ltc.hackathon.ce.flex.request.VerifyRequest;
import ltc.hackathon.ce.flex.util.EmailGenerator;
import ltc.hackathon.ce.flex.util.OtpGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

@Service
public class AuthService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    EmailGenerator emailGenerator;

    @Autowired
    OtpGenerator otpGenerator;

    public String login(LoginRequest loginRequest) {
        if(Objects.nonNull(loginRequest)
                && Objects.nonNull(loginRequest.getEmail())
                && Objects.nonNull(loginRequest.getPassword())){
            User loginUser = userRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            if(loginRequest.getPassword().equals(loginUser.getPassword())) {
                return "Success";
            }
        }
        throw new RuntimeException("Invalid email or password"); }

    public String signUp(SignUpRequest signUpRequest) {
        if (Objects.nonNull(signUpRequest)
                && Objects.nonNull(signUpRequest.getPassword())
                && Objects.nonNull(signUpRequest.getConfirmPassword())
                && signUpRequest.getPassword().equals(signUpRequest.getConfirmPassword())) {
            User newUser = userRepo.findByEmail(signUpRequest.getEmail()).orElseThrow();
            if(null == newUser.getPassword()) {
                newUser.setPassword(signUpRequest.getPassword());
                newUser.setInternal(false);
                newUser.setActive(false);
                newUser.setCreatedOn(Timestamp.from(Instant.now()));
                String otp = otpGenerator.generateOtp();
                emailGenerator.sendOtpEmail(signUpRequest.getEmail(),"Account Verification",otp);
                newUser.setOtp(otp);
                userRepo.save(newUser);
                return "Sign Up Successful";
            }
            throw new RuntimeException("User already exists");
        }
        throw new RuntimeException("Sign Up Failed");
    }

    public String verify(VerifyRequest verifyRequest) {
        if (Objects.nonNull(verifyRequest)){
            User user = userRepo.findByEmail(verifyRequest.getEmail()).orElseThrow();
            if(verifyRequest.getOtp().equalsIgnoreCase(user.getOtp())){
                user.setActive(true);
                userRepo.save(user);
                return "Success";
            }
            else{
                throw new RuntimeException("Invalid OTP");
            }
        }
        throw new RuntimeException("OTP Verification Failed");
    }
}
