package ltc.hackathon.ce.flex.request;

import lombok.Data;

@Data
public class VerifyRequest {
    String email;
    String otp;
}
