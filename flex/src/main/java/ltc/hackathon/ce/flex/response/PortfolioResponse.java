package ltc.hackathon.ce.flex.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PortfolioResponse {
    String email;
    String name;
    String accountNumber;
    String accountBalance;
    String portfolioId;
    List<Loan> loans = new ArrayList<>();
    List<Bill> bills = new ArrayList<>();
    List<Companion> companions = new ArrayList<>();
    List<Admirer> admirers = new ArrayList<>();
}
