package ltc.hackathon.ce.flex.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bill {
    String portfolioId;
    String billType;
    String billAmount;
    String billDate;
    String billFrequency;
}
