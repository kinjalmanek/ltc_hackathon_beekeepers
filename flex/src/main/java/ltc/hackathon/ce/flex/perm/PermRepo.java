package ltc.hackathon.ce.flex.perm;

import ltc.hackathon.ce.flex.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PermRepo extends JpaRepository<Permission,PermissionId>{
    Optional<List<Permission>> findAllByIdAccountEmail(String accountEmail);
    Optional<List<Permission>> findAllByIdEmail(String email);
}
