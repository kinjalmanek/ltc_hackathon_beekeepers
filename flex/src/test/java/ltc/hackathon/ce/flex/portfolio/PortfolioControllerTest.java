package ltc.hackathon.ce.flex.portfolio;

import ltc.hackathon.ce.flex.request.CompanionRequest;
import ltc.hackathon.ce.flex.response.PortfolioResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class PortfolioControllerTest {

    @InjectMocks
    private PortfolioController portfolioController;

    @Mock
    private PortfolioService portfolioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPortfolioSuccess() {
        String email = "test@example.com";
        PortfolioResponse portfolioResponse = new PortfolioResponse(); // Initialize as needed

        when(portfolioService.getPortfolio(email)).thenReturn(portfolioResponse);

        PortfolioResponse response = portfolioController.getPortfolio(email);

        assertEquals(portfolioResponse, response);
    }

    @Test
    public void testAddCompanionSuccess() {
        CompanionRequest companionRequest = new CompanionRequest(); // Initialize as needed
        String responseMessage = "Companion added successfully";

        when(portfolioService.addCompanion(companionRequest)).thenReturn(responseMessage);

        ResponseEntity<String> response = portfolioController.addCompanion(companionRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseMessage, response.getBody());
    }

    // You might want to add additional tests for failure scenarios
}
