package com.epam.bankproject.bankproject.controller;

import com.epam.bankproject.bankproject.ObjectCreator;
import com.epam.bankproject.bankproject.contoller.ChargeController;
import com.epam.bankproject.bankproject.contoller.util.AuthenticationFacade;
import com.epam.bankproject.bankproject.controller.util.ControllerAdviceHelper;
import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.Charge;
import com.epam.bankproject.bankproject.domain.User;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.service.AccountService;
import com.epam.bankproject.bankproject.service.ChargeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ChargeControllerTest {

    private MockMvc mvc;

    @Mock
    private AccountService accountService;

    @Mock
    private ChargeService chargeService;

    @Mock
    private AuthenticationFacade authenticationFacade;

    @InjectMocks
    private ChargeController chargeController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders
                .standaloneSetup(chargeController)
                .setHandlerExceptionResolvers(ControllerAdviceHelper.createExceptionResolver())
                .build();
    }

    @Test
    public void whenShowChargesCorrect_thenOk() throws Exception {
        User user = ObjectCreator.getUser();
        Account account = ObjectCreator.getAccount(AccountType.DEPOSIT);
        Page<Charge> page = new PageImpl(Collections.singletonList(ObjectCreator.getCharge()));


        when(authenticationFacade.getAuthenticatedUser()).thenReturn(user);
        when(accountService.findById(any(Integer.class))).thenReturn(account);
        when(chargeService.findAllByAccountId(any(Integer.class), any(Pageable.class))).thenReturn(page);

        mvc.perform(get("/user/charges")
                .param("accountid", "1")
                .param("page","0"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user", "account","page"))
                .andExpect(view().name("allCharges"));
    }

    @Test
    public void whenShowChargesIncorrect_thenErrorPage() throws Exception {
        User user = ObjectCreator.getUser();

        when(accountService.findById(any(Integer.class))).thenThrow(IllegalArgumentException.class);

        mvc.perform(get("/user/charges")
                .param("accountid", "1")
                .param("page","0"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));

        verifyNoMoreInteractions(authenticationFacade,chargeService);
    }

}
