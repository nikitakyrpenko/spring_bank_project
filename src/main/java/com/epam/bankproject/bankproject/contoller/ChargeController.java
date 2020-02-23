package com.epam.bankproject.bankproject.contoller;

import com.epam.bankproject.bankproject.contoller.util.AuthenticationFacade;
import com.epam.bankproject.bankproject.contoller.util.ModelAndViewBuilder;
import com.epam.bankproject.bankproject.contoller.util.PageRequestFetcher;
import com.epam.bankproject.bankproject.service.AccountService;
import com.epam.bankproject.bankproject.service.ChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.function.Function;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class ChargeController {

    private static final int PAGE_SIZE = 2;

    private final AccountService accountService;
    private final ChargeService chargeService;
    private final AuthenticationFacade authenticationFacade;
    private final ModelAndViewBuilder modelAndViewBuilder;

    @GetMapping(value = "user/charges")
    public ModelAndView showCharges(
            @RequestParam(name = "accountid") Integer id,
            @RequestParam(name = "page") String stringPage) {

        modelAndViewBuilder
                .withObjectBySupplier("account", () -> accountService.findById(id))
                .withObjectBySupplier("user", authenticationFacade::getAuthenticatedUser)
                .withPageableByBiFunction("page",
                        chargeService::findAllByAccountId,
                        id,
                        computeCurrentPage(stringPage, chargeService::countAllByAccountId, id));

        return modelAndViewBuilder.buildWithName("allCharges");
    }

    private PageRequest computeCurrentPage(String stringPage, Function<Integer, Long> function, Integer id) {
        Long apply = function.apply(id) / PAGE_SIZE;
        int page = PageRequestFetcher.fetchPageParameters(
                stringPage,
                apply);
        return PageRequest.of(page, PAGE_SIZE);
    }
}
