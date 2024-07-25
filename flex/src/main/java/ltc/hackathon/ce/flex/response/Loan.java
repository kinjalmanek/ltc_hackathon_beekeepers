package ltc.hackathon.ce.flex.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Loan {
    String portfolioId;
    String loanType;
    String loanTenure;
    String loanAmount;
}
