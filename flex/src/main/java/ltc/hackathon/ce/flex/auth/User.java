package ltc.hackathon.ce.flex.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(nullable = false, unique = true)
    String email;
    @Column(nullable = true)
    String password;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    String countryCode;
    @Column(nullable = false)
    String phoneNumber;
    @Column(nullable = false)
    boolean isActive;
    @Column(nullable = false)
    boolean isInternal;
    @Column(nullable = true)
    String otp;
    Timestamp createdOn;
    Timestamp lastLogin;

    public User() {
        countryCode = "91";
        phoneNumber = "9999999999";
    }
}
