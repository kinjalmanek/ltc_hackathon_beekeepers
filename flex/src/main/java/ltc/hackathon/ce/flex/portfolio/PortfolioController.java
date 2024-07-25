package ltc.hackathon.ce.flex.portfolio;

import ltc.hackathon.ce.flex.request.CompanionRequest;
import ltc.hackathon.ce.flex.response.PortfolioResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PortfolioController {

    @Autowired
    PortfolioService portfolioService;

    @GetMapping("/portfolio")
    public PortfolioResponse getPortfolio(String email){
        return portfolioService.getPortfolio(email);
    }

    @PostMapping("/companion")
    public ResponseEntity<String> addCompanion(@RequestBody CompanionRequest companionRequest){
        return new ResponseEntity<String>(portfolioService.addCompanion(companionRequest), HttpStatus.OK);
    }


}
