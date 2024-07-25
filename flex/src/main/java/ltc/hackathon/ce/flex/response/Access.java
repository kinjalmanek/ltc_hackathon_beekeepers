package ltc.hackathon.ce.flex.response;

import lombok.Data;

@Data
public class Access {
    boolean isPermanent;
    boolean canView;
    boolean canPay;
    boolean balanceCheck;
    Loan loan;
    Bill bill;
    String accessStartDate;
    String accessEndDate;
}
