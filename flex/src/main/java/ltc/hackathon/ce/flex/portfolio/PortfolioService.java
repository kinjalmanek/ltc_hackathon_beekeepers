package ltc.hackathon.ce.flex.portfolio;

import ltc.hackathon.ce.flex.auth.User;
import ltc.hackathon.ce.flex.auth.UserRepo;
import ltc.hackathon.ce.flex.perm.PermRepo;
import ltc.hackathon.ce.flex.perm.Permission;
import ltc.hackathon.ce.flex.perm.PermissionId;
import ltc.hackathon.ce.flex.request.CompanionRequest;
import ltc.hackathon.ce.flex.response.*;
import ltc.hackathon.ce.flex.util.EmailGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PortfolioService {

    @Autowired
    PortfolioRepo portfolioRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    private PermRepo permRepo;

    @Autowired
    EmailGenerator emailGenerator;

    public PortfolioResponse getPortfolio(String email) {
        PortfolioResponse response = new PortfolioResponse();
        response.setEmail(email);
        List<Loan> loans = new ArrayList<>();
        List<Bill> bills = new ArrayList<>();

        User user = userRepo.findByEmail(email).get();
        if(user.isInternal()) {
            for (Portfolio portfolio : portfolioRepo.findByEmail(email)) {
                switch (portfolio.getProductType()) {
                    case "BALANCE": {
                        response.setPortfolioId(portfolio.getPortfolioId());
                        response.setAccountBalance(String.valueOf(portfolio.getAccountBalance()));
                        response.setName(portfolio.getName());
                        response.setAccountNumber(portfolio.getAccountNumber());
                        break;
                    }
                    case "LOAN": {
                        Loan loan = new Loan();
                        loan.setPortfolioId(portfolio.getPortfolioId());
                        loan.setLoanType(portfolio.getLoanType());
                        loan.setLoanAmount(String.valueOf(portfolio.getAmount()));
                        loan.setLoanTenure(portfolio.getLoanTenure());
                        loans.add(loan);
                        break;
                    }
                    case "BILL": {
                        Bill bill = new Bill();
                        bill.setPortfolioId(portfolio.getPortfolioId());
                        bill.setBillType(portfolio.getBillType());
                        bill.setBillAmount(String.valueOf(portfolio.getAmount()));
                        bill.setBillFrequency(portfolio.getBillFrequency());
                        bill.setBillDate(portfolio.getBillDate());
                        bills.add(bill);
                        break;
                    }
                }
            }
            response.getLoans().addAll(loans);
            response.getBills().addAll(bills);
            response.getCompanions().addAll(buildCompanions(email));
        }
        response.getAdmirers().addAll(buildAdmirers(email));
        return response;
    }

    public String addCompanion(CompanionRequest request){
        User user;
        try {
            user = userRepo.findByEmail(request.getEmail()).orElseThrow();
        }catch (Exception e){
            user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setCountryCode(request.getCountryCode());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setInternal(false);
            user.setActive(false);
            user.setCreatedOn(Timestamp.from(Instant.now()));
            userRepo.save(user);
        }
        if(request.isFullAccess()){
            List<Portfolio> portfolioList = new ArrayList<>();
            portfolioList = portfolioRepo.findByEmail(request.getAccountEmail());
            for(Portfolio portfolio : portfolioList){
                PermissionId id = new PermissionId();
                id.setEmail(request.getEmail());
                id.setAccountEmail(request.getAccountEmail());
                id.setPortfolioId(Integer.valueOf(portfolio.getPortfolioId()));
                Permission permission = new Permission();
                permission.setId(id);
                permission.setCanPay(true);
                permission.setCanView(true);
                permission.setFullAccess(true);
                permission.setAccessPermanent(true);
                permission.setStartDate(LocalDate.now());
                permRepo.save(permission);
            }
        }
        else
        {
            PermissionId id = new PermissionId();
            id.setEmail(request.getEmail());
            id.setAccountEmail(request.getAccountEmail());
            id.setPortfolioId(Integer.valueOf(request.getPortfolioId()));
            Permission permission = new Permission();
            permission.setId(id);

            permission.setCanView(request.isCanView());
            permission.setCanPay(request.isCanPay());
            permission.setFullAccess(request.isFullAccess());
            if (Objects.nonNull(request.getStartDate()))
                permission.setStartDate(LocalDate.parse(request.getStartDate()));
            else
                permission.setStartDate(LocalDate.now());
            if (Objects.nonNull(request.getEndDate()))
                permission.setEndDate(LocalDate.parse(request.getEndDate()));
            permission.setAccessPermanent(request.isAccessPermanent());
            permRepo.save(permission);
        }
        emailGenerator.sendSignUpEmail(request.getEmail(), "Added to portfolio");
        return "Successfully added companion to portfolio";
    }

    List<Companion> buildCompanions(String accountEmail){
        List<Permission> permissionList = permRepo.findAllByIdAccountEmail(accountEmail).orElse(new ArrayList<>());
        List<Companion> companions = new ArrayList<>();
        for(Permission permission : permissionList){
            Portfolio portfolio = portfolioRepo.findById(permission.getId().getPortfolioId()).get();
            Companion companion;
            if(companions.stream().noneMatch((x -> x.getEmail().equals(permission.getId().getEmail())))) {
                companion = new Companion();
                companion.setEmail(permission.getId().getEmail());
                companion.setName(userRepo.findByEmail(permission.getId().getEmail()).get().getName());
                companions.add(companion);
            }
            else {
                companion = companions.stream().filter(x -> x.getEmail().equals(permission.getId().getEmail())).findFirst().get();
            }
            companion.getAccessList().add(buildAccess(permission, portfolio));
        }
        return companions;
    }

    List<Admirer> buildAdmirers(String email){
        List<Admirer> admirers = new ArrayList<>();
        List<Permission> permissionList = permRepo.findAllByIdEmail(email).orElse(new ArrayList<>());
        for(Permission permission : permissionList){
            Admirer admirer;
            if(admirers.stream().noneMatch((x) -> x.getEmail().equals(permission.getId().getAccountEmail()))){
                admirer = new Admirer();
                admirer.setEmail(permission.getId().getAccountEmail());
                admirer.setName(userRepo.findByEmail(permission.getId().getAccountEmail()).get().getName());
                admirers.add(admirer);
            }
            else{
                admirer = admirers.stream().filter(x -> x.getEmail().equals(permission.getId().getAccountEmail())).findFirst().get();
            }

            admirer.getAccessList().add(buildAccess(permission, null));
        }
        return admirers;
    }

    private Access buildAccess(Permission permission, Portfolio portfolio) {
        Access access = new Access();
        if(Objects.nonNull(portfolio)) {
            switch (portfolio.getProductType()) {
                case "BALANCE": {
                    access.setBalanceCheck(true);
                    break;
                }
                case "LOAN": {
                    Loan loan = new Loan();
                    loan.setPortfolioId(portfolio.getPortfolioId());
                    loan.setLoanType(portfolio.getLoanType());
                    loan.setLoanAmount(String.valueOf(portfolio.getAmount()));
                    loan.setLoanTenure(portfolio.getLoanTenure());
                    access.setLoan(loan);
                    break;
                }
                case "BILL": {
                    Bill bill = new Bill();
                    bill.setPortfolioId(portfolio.getPortfolioId());
                    bill.setBillType(portfolio.getBillType());
                    bill.setBillAmount(String.valueOf(portfolio.getAmount()));
                    bill.setBillFrequency(portfolio.getBillFrequency());
                    bill.setBillDate(portfolio.getBillDate());
                    access.setBill(bill);
                    break;
                }
            }
        }
        access.setPermanent(permission.isAccessPermanent());
        access.setCanView(permission.isCanView());
        access.setCanPay(permission.isCanPay());
        if(Objects.nonNull(permission.getStartDate()))
            access.setAccessStartDate(permission.getStartDate().toString());
        if(Objects.nonNull(permission.getEndDate()))
            access.setAccessEndDate(permission.getEndDate().toString());
        return access;
    }
}
