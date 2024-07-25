package ltc.hackathon.ce.flex.perm;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class PermissionId implements Serializable {
    String accountEmail;
    String email;
    Integer portfolioId;
}
