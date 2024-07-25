package ltc.hackathon.ce.flex.response;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Companion {
    String name;
    String email;
    List<Access> accessList = new ArrayList<>();
}
