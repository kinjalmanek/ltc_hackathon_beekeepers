package ltc.hackathon.ce.flex.portfolio;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "portfolio")
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String portfolioId;
    private String name;
    private String email;
    private String accountNumber;
    private int accountBalance;
    private String productType;
    private String loanType;
    private int amount;
    private String loanTenure;
    private String billType;
    private String billFrequency;
    private String billDate;
}
