package com.epam.bankproject.bankproject.contoller;

import com.epam.bankproject.bankproject.contoller.util.AuthenticationFacade;
import com.epam.bankproject.bankproject.contoller.util.ModelAndViewBuilder;
import com.epam.bankproject.bankproject.view.data.OperationData;
import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.DepositAccount;
import com.epam.bankproject.bankproject.domain.Operation;
import com.epam.bankproject.bankproject.service.AccountService;
import com.epam.bankproject.bankproject.service.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Date;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class TransactionController {

    private final OperationService operationService;
    private final AuthenticationFacade authenticationFacade;
    private final AccountService accountService;
    private final ModelAndViewBuilder modelAndViewBuilder;

    @GetMapping(value = "user/transaction")
    public ModelAndView showTransactionView(
            @RequestParam(name = "accountid") Integer id,
            @RequestParam(name = "error", required = false) String error) {

        Account byId = accountService.findById(id);

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
        return modelAndViewBuilder.buildWithName("transactions");
    }

    @PostMapping(value = "user/transaction")
    public ModelAndView saveTransaction(@Valid OperationData operationData, BindingResult bindingResult) {

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
                .transfer(operationData.getTransfer().doubleValue())
                .sender(DepositAccount.builder().id(operationData.getSenderId()).build())
                .receiver(DepositAccount.builder().id(operationData.getReceiverId()).build())
                .build();
    }
}
