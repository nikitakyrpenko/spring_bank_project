package com.epam.bankproject.bankproject.contoller;

import com.epam.bankproject.bankproject.contoller.util.PageRequestFetcher;
import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.CreditAccount;
import com.epam.bankproject.bankproject.domain.DepositAccount;
import com.epam.bankproject.bankproject.domain.User;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Controller
public class UserController {

    private static final int PAGE_SIZE = 2;

    private AccountService accountService;

    @GetMapping(value = "user/accounts")
    public ModelAndView showUsersAccounts(ModelAndView modelAndView,
                                          Authentication authentication,
                                          @RequestParam(value = "page", required = false) String stringPage) {

        User user =(User) authentication.getPrincipal();

        int page = PageRequestFetcher.fetchPageParameters(
                stringPage,
                accountService.countAllByOwnerId(user.getId()) / PAGE_SIZE);

        Page<Account> accountsPage = accountService.findAllByOwnerId(user.getId(), PageRequest.of(page, PAGE_SIZE));

        modelAndView.addObject("user",user);
        modelAndView.addObject("accountPage", accountsPage);

        int totalPages = accountsPage.getTotalPages();

        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        modelAndView.setViewName("accountsInfo");
        return modelAndView;
    }

    @GetMapping(value = "user/accountDetails")
    public ModelAndView showAccountDetail(ModelAndView modelAndView,
                                          @RequestParam(name = "id", required = true) String id){


        Account accountById = accountService.findById(Integer.valueOf(id));
        if (accountById.getAccountType() == AccountType.DEPOSIT){
            DepositAccount depositAccount = (DepositAccount) accountById;
            modelAndView.addObject("account", depositAccount);
            modelAndView.setViewName("depositAccountDetails");
        }else {
            CreditAccount creditAccount = (CreditAccount) accountById;
            modelAndView.addObject("account", creditAccount);
            modelAndView.setViewName("creditAccountDetails");
        }
        return modelAndView;
    }

}
