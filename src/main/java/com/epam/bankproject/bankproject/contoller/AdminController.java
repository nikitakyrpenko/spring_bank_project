package com.epam.bankproject.bankproject.contoller;

import com.epam.bankproject.bankproject.contoller.util.*;
import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.CreditAccount;
import com.epam.bankproject.bankproject.domain.DepositAccount;
import com.epam.bankproject.bankproject.domain.User;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.service.AccountService;
import com.epam.bankproject.bankproject.service.RequestService;
import com.epam.bankproject.bankproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.sql.Date;
import java.util.function.Supplier;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Controller

@PreAuthorize("hasRole(T(com.epam.bankproject.bankproject.enums.Role).ROLE_ADMIN)")
public class AdminController {
    private static final int PAGE_SIZE = 2;

    private final RequestService requestService;
    private final UserService userService;
    private final AccountService accountService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping(value = "admin/requests")
    public ModelAndView showAccountRequests(@RequestParam(name = "page") String stringPage){

        ModelAndViewBuilder modelAndViewBuilder = new ModelAndViewBuilder();

        modelAndViewBuilder.withPageableByFunction(
                "page",
                requestService::findAll,
                computeCurrentPage(stringPage,requestService::countAll))
        .withObjectBySupplier("user",authenticationFacade::getAuthenticatedUser);

        return modelAndViewBuilder.buildWithName("requestedAccounts");
    }

    @GetMapping(value = "admin/accountRegistration")
    public ModelAndView showAccountRegistrationForm(@RequestParam(name = "userId") Integer id,
                                                    @RequestParam(name = "type") String type,
                                                    @RequestParam(name = "requestId") Integer requestId){

        User owner = userService.findById(id);

        ModelAndViewBuilder modelAndViewBuilder = new ModelAndViewBuilder();

        modelAndViewBuilder
                .withObjectBySupplier("user",authenticationFacade::getAuthenticatedUser);

        if (AccountType.DEPOSIT.name().equals(type)) {
            return modelAndViewBuilder
                    .withObjectBySupplier("account", () -> new DepositAccountData(owner.getId(),requestId))
                    .buildWithName("depositAccountRegistration");
        }
        return
                modelAndViewBuilder
                        .withObjectBySupplier("account", () -> new CreditAccountData(owner.getId(),requestId))
                        .buildWithName("creditAccountRegistration");
    }

   @PostMapping(value = "admin/accountRegistration")
    public ModelAndView saveAccount(DepositAccountData account){
      DepositAccount depositAccount = mapDataToEntity(account);
      requestService.deleteById(account.getRequestId());
      accountService.save(depositAccount);
      return new ModelAndView("redirect:/admin/requests?page=0");
    }

    @PostMapping(value = "admin/creditAccountRegistration")
    public ModelAndView saveCreditAccount(CreditAccountData account){
        CreditAccount creditAccount = mapDataToEntity(account);
        requestService.deleteById(account.getRequestId());
        accountService.save(creditAccount);
        return new ModelAndView("redirect:/admin/requests?page=0");

    }

    private PageRequest computeCurrentPage(String stringPage, Supplier<Long> supplier) {
        Long apply = supplier.get() / PAGE_SIZE;
        int page = PageRequestFetcher.fetchPageParameters(
                stringPage,
                apply);
        return PageRequest.of(page, PAGE_SIZE);
    }


    private DepositAccount mapDataToEntity(DepositAccountData depositAccountData){
        User user = userService.findById(depositAccountData.getOwner());

        return DepositAccount.builder()
                .depositAmount(depositAccountData.getDepositAmount())
                .depositRate(depositAccountData.getDepositRate())
                .owner(user)
                .expirationDate(Date.valueOf(depositAccountData.getExpirationDate()))
                .accountType(AccountType.DEPOSIT)
                .build();
    }

    private CreditAccount mapDataToEntity(CreditAccountData creditAccountData){
        User user = userService.findById(creditAccountData.getOwner());
        return CreditAccount.builder()
                .owner(user)
                .accountType(AccountType.CREDIT)
                .limit(creditAccountData.getCreditLimit())
                .creditRate(creditAccountData.getCreditRate())
                .expirationDate(Date.valueOf(creditAccountData.getExpirationDate()))
                .balance(creditAccountData.getBalance())
                .liability(0.0)
                .build();

    }
}
