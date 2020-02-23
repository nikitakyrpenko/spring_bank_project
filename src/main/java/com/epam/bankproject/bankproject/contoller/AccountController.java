package com.epam.bankproject.bankproject.contoller;

import com.epam.bankproject.bankproject.contoller.util.*;
import com.epam.bankproject.bankproject.domain.*;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.service.*;
import com.epam.bankproject.bankproject.view.data.CreditAccountData;
import com.epam.bankproject.bankproject.view.data.DepositAccountData;
import com.epam.bankproject.bankproject.view.data.RequestData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.util.function.Function;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class AccountController {
    private static final int PAGE_SIZE = 2;

    private final UserService userService;
    private final RequestService requestService;
    private final AccountService accountService;
    private final OperationService operationService;
    private final AuthenticationFacade authenticationFacade;
    private final ModelAndViewBuilder modelAndViewBuilder;

    @GetMapping(value = "user/accounts")
    public ModelAndView showUsersAccounts(@RequestParam(value = "page", required = false) String stringPage) {

        User user = authenticationFacade.getAuthenticatedUser();

        modelAndViewBuilder
                .withObjectBySupplier("user", () -> user)
                .withObjectBySupplier("newAccount", () -> new RequestData(user.getId()))
                .withPageableByBiFunction("page",
                        accountService::findAllByOwnerId,
                        user.getId(),
                        computeCurrentPage(stringPage, accountService::countAllByOwnerId, user.getId()));

        return modelAndViewBuilder.buildWithName("accountsInfo");
    }


    @GetMapping(value = "user/accountDetails")
    public ModelAndView showAccountDetail(
            @RequestParam(name = "page") String stringPage,
            @RequestParam(name = "type") String type,
            @RequestParam(name = "id") Integer id) {

        ModelAndViewBuilder modelAndViewBuilder = new ModelAndViewBuilder();

        modelAndViewBuilder
                .withObjectBySupplier("user", authenticationFacade::getAuthenticatedUser)
                .withObjectBySupplier("account", () -> {
                    Account account = accountService.findById(id);
                    return account.getAccountType() == AccountType.DEPOSIT ? (DepositAccount) account : (CreditAccount) account;
                })
                .withPageableByBiFunction("page",
                        operationService::findAllOperationsByAccountId,
                        id,
                        computeCurrentPage(stringPage, operationService::countAllByReceiverAccountIdOrSenderAccountId, id));

        if (AccountType.DEPOSIT.name().equals(type)) {
            return modelAndViewBuilder.buildWithName("depositAccountDetails");
        }
        return modelAndViewBuilder.buildWithName("creditAccountDetails");
    }

    @GetMapping(value = "admin/accountRegistration")
    public ModelAndView showAccountRegistrationForm(@RequestParam(name = "userId") Integer id,
                                                    @RequestParam(name = "type") String type,
                                                    @RequestParam(name = "requestId") Integer requestId,
                                                    @RequestParam(name = "error", required = false) String error) {

        User owner = userService.findById(id);
        Request request = requestService.findById(requestId);

        modelAndViewBuilder
                .withObjectBySupplier("user", authenticationFacade::getAuthenticatedUser);

        if (AccountType.DEPOSIT.name().equals(type)) {
            return modelAndViewBuilder
                    .withObjectBySupplier("account", () -> new DepositAccountData(owner.getId(), request.getId()))
                    .withObjectBySupplier("error", () -> error)
                    .buildWithName("depositAccountRegistration");
        }
        return
                modelAndViewBuilder
                        .withObjectBySupplier("account", () -> new CreditAccountData(owner.getId(), request.getId()))
                        .withObjectBySupplier("error", () -> error)
                        .buildWithName("creditAccountRegistration");
    }

    @PostMapping(value = "admin/accountRegistration")
    public ModelAndView saveAccount(@Valid DepositAccountData account, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return modelAndViewBuilder.buildWithName(
                    "redirect:/admin/accountRegistration?error=true&userId=" + account.getOwner() + "&type=DEPOSIT&requestId=" + account.getRequestId());
        }


        DepositAccount depositAccount = mapDataToEntity(account);
        requestService.deleteById(account.getRequestId());
        accountService.save(depositAccount);
        return modelAndViewBuilder.buildWithName("redirect:/admin/requests?page=0");
    }

    @PostMapping(value = "admin/creditAccountRegistration")
    public ModelAndView saveCreditAccount(@Valid CreditAccountData account, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return modelAndViewBuilder.buildWithName(
                    "redirect:/admin/accountRegistration?error=true&userId=" + account.getOwner() + "&type=CREDIT&requestId=" + account.getRequestId());
        }

        CreditAccount creditAccount = mapDataToEntity(account);
        requestService.deleteById(account.getRequestId());
        accountService.save(creditAccount);
        return modelAndViewBuilder.buildWithName("redirect:/admin/requests?page=0");

    }

    private DepositAccount mapDataToEntity(DepositAccountData depositAccountData) {
        User user = userService.findById(depositAccountData.getOwner());

        return DepositAccount.builder()
                .depositAmount(depositAccountData.getDepositAmount().doubleValue())
                .depositRate(depositAccountData.getDepositRate().doubleValue() / 100)
                .owner(user)
                .expirationDate(Date.valueOf(depositAccountData.getExpirationDate()))
                .accountType(AccountType.DEPOSIT)
                .build();
    }

    private CreditAccount mapDataToEntity(CreditAccountData creditAccountData) {
        User user = userService.findById(creditAccountData.getOwner());
        return CreditAccount.builder()
                .owner(user)
                .accountType(AccountType.CREDIT)
                .limit(creditAccountData.getCreditLimit().doubleValue())
                .creditRate(creditAccountData.getCreditRate().doubleValue() / 100)
                .expirationDate(Date.valueOf(creditAccountData.getExpirationDate()))
                .liability(0.0)
                .build();

    }



    private PageRequest computeCurrentPage(String stringPage, Function<Integer, Long> function, Integer id) {
        Long apply = function.apply(id) / PAGE_SIZE;
        int page = PageRequestFetcher.fetchPageParameters(
                stringPage,
                apply);
        return PageRequest.of(page, PAGE_SIZE);
    }

}
