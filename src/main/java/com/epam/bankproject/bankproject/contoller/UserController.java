package com.epam.bankproject.bankproject.contoller;

import com.epam.bankproject.bankproject.contoller.util.*;
import com.epam.bankproject.bankproject.domain.*;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.service.AccountService;
import com.epam.bankproject.bankproject.service.ChargeService;
import com.epam.bankproject.bankproject.service.OperationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Date;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class UserController {
    private static final int PAGE_SIZE = 2;

    private final AccountService accountService;
    private final OperationService operationService;
    private final ChargeService chargeService;
    private final AuthenticationFacade authenticationFacade;


    @GetMapping(value = "user/accounts")
    public ModelAndView showUsersAccounts(@RequestParam(value = "page", required = false) String stringPage) {

        Integer userId = authenticationFacade.getAuthenticatedUser().getId();

        ModelAndViewBuilder modelAndViewBuilder = new ModelAndViewBuilder();

        modelAndViewBuilder
                .withObjectBySupplier("user", authenticationFacade::getAuthenticatedUser)
                .withPageableByFunction("page",
                        accountService::findAllByOwnerId,
                        userId,
                        computeCurrentPage(stringPage, accountService::countAllByOwnerId, userId));


        return modelAndViewBuilder.buildWithName("accountsInfo");
    }


    @GetMapping(value = "user/accountDetails")
    public ModelAndView showAccountDetail(
            @RequestParam(name = "page", required = true) String stringPage,
            @RequestParam(name = "type", required = true) String type,
            @RequestParam(name = "id", required = true) Integer id) {

        ModelAndViewBuilder modelAndViewBuilder = new ModelAndViewBuilder();

        modelAndViewBuilder
                .withObjectBySupplier("user", authenticationFacade::getAuthenticatedUser)
                .withObjectBySupplier("account", () -> {
                    Account account = accountService.findById(id);
                    return account.getAccountType() == AccountType.DEPOSIT ? (DepositAccount) account : (CreditAccount) account;
                })
                .withPageableByFunction("page",
                        operationService::findAllOperationsByAccountId,
                        id,
                        computeCurrentPage(stringPage, operationService::countAllByReceiverAccountIdOrSenderAccountId, id));


        if (AccountType.DEPOSIT.name().equals(type)) {
            return modelAndViewBuilder.buildWithName("depositAccountDetails");
        }
        return modelAndViewBuilder.buildWithName("creditAccountDetails");
    }


    @GetMapping(value = "user/charges")
    public ModelAndView showCharges(
            @RequestParam(name = "accountid") Integer id,
            @RequestParam(name = "page") String stringPage) {

        ModelAndViewBuilder modelAndViewBuilder = new ModelAndViewBuilder();

        modelAndViewBuilder
                .withObjectBySupplier("user", authenticationFacade::getAuthenticatedUser)
                .withObjectBySupplier("account", () -> accountService.findById(id))
                .withPageableByFunction("page",
                        chargeService::findAllByAccountId,
                        id,
                        computeCurrentPage(stringPage, chargeService::countAllByAccountId, id));

        return modelAndViewBuilder.buildWithName("charges");
    }

    @GetMapping(value = "user/transaction")
    public ModelAndView showTransactionView(
            @RequestParam(name = "accountid") Integer id,
            @RequestParam(name = "error", required = false) String error) {


        ModelAndViewBuilder modelAndViewBuilder = new ModelAndViewBuilder();

        modelAndViewBuilder
                .withObjectBySupplier("user", authenticationFacade::getAuthenticatedUser)
                .withObjectBySupplier("operation", () -> {
                    OperationData operationData = new OperationData();
                    operationData.setSenderId(id);
                    return operationData;
                });

        if (error != null) {
            modelAndViewBuilder.withObjectBySupplier("error", () -> error);
        }
        return modelAndViewBuilder.buildWithName("transaction");
    }

    @PostMapping(value = "user/transaction")
    public ModelAndView saveTransaction(@Valid OperationData operationData, BindingResult bindingResult) {

        ModelAndViewBuilder modelAndViewBuilder = new ModelAndViewBuilder();


        if (bindingResult.hasErrors()) {
            return modelAndViewBuilder
                    .withObjectBySupplier("user", authenticationFacade::getAuthenticatedUser)
                    .buildWithName("redirect:/user/transaction?accountid=" + operationData.getSenderId() + "&error=true");
        }
        Operation operation = mapOperationDataToOperation(operationData);
        Operation save = operationService.save(operation);

        return modelAndViewBuilder
                .buildWithName("redirect:/user/accountDetails?page=0&id=" + save.getSender().getId() + "&type=" + save.getSender().getAccountType());



    }

    private Operation mapOperationDataToOperation(OperationData operationData) {
        return Operation.builder()
                .purpose(operationData.getPurpose())
                .dateOfOperation(new Date(System.currentTimeMillis()))
                .transfer(operationData.getTransfer())
                .sender(DepositAccount.builder().id(operationData.getSenderId()).build())
                .receiver(DepositAccount.builder().id(operationData.getReceiverId()).build())
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
