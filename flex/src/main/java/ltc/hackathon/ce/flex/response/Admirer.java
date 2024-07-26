package ltc.hackathon.ce.flex.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Admirer {
    private String name;
    private String email;
    private String accountNo;
    List<Access> accessList = new ArrayList<>();
}
