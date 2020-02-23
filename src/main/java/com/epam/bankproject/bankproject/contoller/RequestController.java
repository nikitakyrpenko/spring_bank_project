package com.epam.bankproject.bankproject.contoller;

import com.epam.bankproject.bankproject.contoller.util.AuthenticationFacade;
import com.epam.bankproject.bankproject.contoller.util.ModelAndViewBuilder;
import com.epam.bankproject.bankproject.contoller.util.PageRequestFetcher;
import com.epam.bankproject.bankproject.domain.Request;
import com.epam.bankproject.bankproject.domain.User;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.service.RequestService;
import com.epam.bankproject.bankproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.util.function.Supplier;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class RequestController {

    private static final int PAGE_SIZE = 2;

    private final UserService userService;
    private final RequestService requestService;
    private final AuthenticationFacade authenticationFacade;
    private final ModelAndViewBuilder modelAndViewBuilder;

    @GetMapping(value = "admin/requests")
    public ModelAndView showAccountRequests(@RequestParam(name = "page") String stringPage) {

        modelAndViewBuilder.withPageableByFunction(
                "page",
                requestService::findAll,
                computeCurrentPage(stringPage, requestService::countAll))
                .withObjectBySupplier("user", authenticationFacade::getAuthenticatedUser);

        return modelAndViewBuilder.buildWithName("requestedAccounts");
    }

    @GetMapping(value = "/user/requestAccount")
    public ModelAndView saveAccountRequest(@RequestParam(name = "userId") Integer id,
                                           @RequestParam(name = "type") String type){

        User user = userService.findById(id);

        Request request = Request
                .builder()
                .owner(user)
                .accountType(AccountType.valueOf(type))
                .build();

        requestService.save(request);
        return modelAndViewBuilder.buildWithName("redirect:/user/accounts?page=0");

    }

    private PageRequest computeCurrentPage(String stringPage, Supplier<Long> supplier) {
        Long apply = supplier.get() / PAGE_SIZE;
        int page = PageRequestFetcher.fetchPageParameters(
                stringPage,
                apply);
        return PageRequest.of(page, PAGE_SIZE);
    }
}
