package ltc.hackathon.ce.flex.perm;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "perm")
public class Permission {
    @EmbeddedId
    PermissionId id;
    LocalDate startDate;
    LocalDate endDate;
    boolean isAccessPermanent;
    boolean canView;
    boolean canPay;
    boolean fullAccess;
}
