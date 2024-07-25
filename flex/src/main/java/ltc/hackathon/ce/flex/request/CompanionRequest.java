package ltc.hackathon.ce.flex.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanionRequest {
    String name;
    String email;
    String countryCode;
    String phoneNumber;
    String accountEmail;
    String portfolioId;
    boolean isAccessPermanent;
    boolean canView;
    boolean canPay;
    String startDate;
    String endDate;
}
