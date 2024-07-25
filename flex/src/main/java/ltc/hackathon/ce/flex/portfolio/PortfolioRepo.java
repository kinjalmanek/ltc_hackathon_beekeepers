package ltc.hackathon.ce.flex.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepo extends JpaRepository<Portfolio, Integer> {
    List<Portfolio> findByEmail(String email);
}
