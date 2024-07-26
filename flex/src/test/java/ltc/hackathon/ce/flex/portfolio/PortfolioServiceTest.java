package ltc.hackathon.ce.flex.portfolio;

import ltc.hackathon.ce.flex.auth.User;
import ltc.hackathon.ce.flex.auth.UserRepo;
import ltc.hackathon.ce.flex.perm.PermRepo;
import ltc.hackathon.ce.flex.perm.Permission;
import ltc.hackathon.ce.flex.perm.PermissionId;
import ltc.hackathon.ce.flex.request.CompanionRequest;
import ltc.hackathon.ce.flex.response.*;
import ltc.hackathon.ce.flex.util.EmailGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PortfolioServiceTest {

    @InjectMocks
    private PortfolioService portfolioService;

    @Mock
    private PortfolioRepo portfolioRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private PermRepo permRepo;

    @Mock
    private EmailGenerator emailGenerator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPortfolioWithBalance() {
        String email = "test@example.com";

        User user = new User();
        user.setInternal(true);

        Portfolio portfolio = new Portfolio();
        portfolio.setProductType("BALANCE");
        portfolio.setPortfolioId("1");
        portfolio.setAccountBalance(1000);
        portfolio.setName("Test Account");
        portfolio.setAccountNumber("123456");

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(portfolioRepo.findByEmail(email)).thenReturn(List.of(portfolio));

        PortfolioResponse response = portfolioService.getPortfolio(email);

        assertNotNull(response);
        assertEquals("1", response.getPortfolioId());
        assertEquals("1000", response.getAccountBalance());
        assertEquals("Test Account", response.getName());
        assertEquals("123456", response.getAccountNumber());
    }

    @Test
    public void testGetPortfolioWithLoan() {
        String email = "test@example.com";

        User user = new User();
        user.setInternal(true);

        Portfolio portfolio = new Portfolio();
        portfolio.setProductType("LOAN");
        portfolio.setPortfolioId("2");
        portfolio.setLoanType("Home Loan");
        portfolio.setAmount(50000);
        portfolio.setLoanTenure("10 years");

        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));
        when(portfolioRepo.findByEmail(email)).thenReturn(List.of(portfolio));

        PortfolioResponse response = portfolioService.getPortfolio(email);

        assertNotNull(response);
        assertEquals(1, response.getLoans().size());
        Loan loan = response.getLoans().get(0);
        assertEquals("2", loan.getPortfolioId());
        assertEquals("Home Loan", loan.getLoanType());
        assertEquals("50000", loan.getLoanAmount());
        assertEquals("10 years", loan.getLoanTenure());
    }

    @Test
    public void testAddCompanionSuccessWithFullAccess() {
        CompanionRequest request = new CompanionRequest();
        request.setEmail("companion@example.com");
        request.setName("Companion");
        request.setCountryCode("+1");
        request.setPhoneNumber("1234567890");
        request.setFullAccess(true);
        request.setAccountEmail("test@example.com");

        User existingUser = new User();
        existingUser.setEmail("companion@example.com");

        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioId("1");

        when(userRepo.findByEmail("companion@example.com")).thenReturn(Optional.of(existingUser));
        when(portfolioRepo.findByEmail("test@example.com")).thenReturn(List.of(portfolio));

        String response = portfolioService.addCompanion(request);

        verify(permRepo, times(1)).save(any(Permission.class));
        verify(emailGenerator).sendSignUpEmail("companion@example.com", "Added to portfolio");
        assertEquals("Successfully added companion to portfolio", response);
    }

    @Test
    public void testAddCompanionSuccessWithPartialAccess() {
        CompanionRequest request = new CompanionRequest();
        request.setEmail("companion@example.com");
        request.setName("Companion");
        request.setCountryCode("+1");
        request.setPhoneNumber("1234567890");
        request.setFullAccess(false);
        request.setAccountEmail("test@example.com");
        request.setPortfolioId("1");

        User existingUser = new User();
        existingUser.setEmail("companion@example.com");

        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioId("1");

        when(userRepo.findByEmail("companion@example.com")).thenReturn(Optional.of(existingUser));


        String response = portfolioService.addCompanion(request);

        verify(permRepo, times(1)).save(any(Permission.class));
        verify(emailGenerator).sendSignUpEmail("companion@example.com", "Added to portfolio");
        assertEquals("Successfully added companion to portfolio", response);
    }

    @Test
    public void testBuildCompanions() {
        String accountEmail = "test@example.com";

        Permission permission = new Permission();
        PermissionId permissionId = new PermissionId();
        permissionId.setEmail("companion@example.com");
        permissionId.setAccountEmail(accountEmail);
        permissionId.setPortfolioId(1);
        permission.setId(permissionId);
        permission.setCanView(true);
        permission.setCanPay(false);
        permission.setAccessPermanent(true);

        Portfolio portfolio = new Portfolio();
        portfolio.setProductType("BALANCE");
        portfolio.setPortfolioId("1");

        when(permRepo.findAllByIdAccountEmail(accountEmail)).thenReturn(Optional.of(List.of(permission)));
        when(portfolioRepo.findById(1)).thenReturn(Optional.of(portfolio));
        when(userRepo.findByEmail("companion@example.com")).thenReturn(Optional.of(new User()));

        List<Companion> companions = portfolioService.buildCompanions(accountEmail);

        assertNotNull(companions);
        assertEquals(1, companions.size());
        Companion companion = companions.get(0);
        assertEquals("companion@example.com", companion.getEmail());
        assertTrue(companion.getAccessList().get(0).isBalanceCheck());
        assertTrue(companion.getAccessList().get(0).isPermanent());
    }
}
