package com.epam.bankproject.bankproject.controller;

import com.epam.bankproject.bankproject.ObjectCreator;
import com.epam.bankproject.bankproject.contoller.AccountController;
import com.epam.bankproject.bankproject.contoller.util.AuthenticationFacade;
import com.epam.bankproject.bankproject.domain.*;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.service.AccountService;
import com.epam.bankproject.bankproject.service.OperationService;
import com.epam.bankproject.bankproject.service.RequestService;
import com.epam.bankproject.bankproject.service.UserService;
import org.junit.After;
import org.junit.Before;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import java.util.*;

import static com.epam.bankproject.bankproject.ObjectCreator.*;
import static com.epam.bankproject.bankproject.controller.util.ControllerAdviceHelper.createExceptionResolver;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@EnableWebMvc
public class AccountControllerTest {

    private MockMvc mvc;

    @Mock
    private OperationService operationService;

    @Mock
    private AuthenticationFacade authenticationFacade;

    @Mock
    private AccountService accountService;

    @Mock
    private UserService userService;

    @Mock
    private RequestService requestService;

    @InjectMocks
    private AccountController accountController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders
                .standaloneSetup(accountController)
                .setHandlerExceptionResolvers(createExceptionResolver())
                .build();
    }

    @After
    public void resetMocks() {
        reset(authenticationFacade, accountService, operationService);
    }

    @Test
    public void whenShowUsersAccountsGetMethodIsCorrect_thenOK() throws Exception {
        User user = getUser();
        PageImpl<Account> accounts = new PageImpl<>(Arrays.asList(getAccount(AccountType.DEPOSIT)));

        when(authenticationFacade.getAuthenticatedUser()).thenReturn(user);
        when(accountService.findAllByOwnerId(any(Integer.class), any(Pageable.class))).thenReturn(accounts);
        when(accountService.countAllByOwnerId(any(Integer.class))).thenReturn(1L);

        mvc.perform(get("/user/accounts").param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("accountsInfo"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attribute("page", accounts));

        verify(authenticationFacade, atLeastOnce()).getAuthenticatedUser();
        verify(accountService).findAllByOwnerId(any(Integer.class), any(Pageable.class));
        verify(accountService).countAllByOwnerId(any(Integer.class));
    }

    @Test
    public void whenShowUsersAccountsUserDoesNotExist_thenReturnErrorPage() throws Exception {
        when(authenticationFacade.getAuthenticatedUser()).thenThrow(NoSuchElementException.class);

        mvc.perform(get("/user/accounts").param("page", "0"))
                .andExpect(view().name("error"));

        verify(authenticationFacade).getAuthenticatedUser();
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void whenShowUsersAccountsDetailsIsCorrect_thenReturnCredit() throws Exception {
        User user = getUser();
        Account account = getAccount(AccountType.CREDIT);
        PageImpl<Operation> operations = new PageImpl<>(Collections.singletonList(getOperation()));

        when(authenticationFacade.getAuthenticatedUser()).thenReturn(user);
        when(accountService.findById(any(Integer.class))).thenReturn(account);
        when(operationService.findAllOperationsByAccountId(any(Integer.class), any(Pageable.class))).thenReturn(operations);

        mvc.perform(get("/user/accountDetails")
                .param("page", "0")
                .param("type", "CREDIT")
                .param("id", "1"))
                .andExpect(view().name("creditAccountDetails"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attribute("page", operations))
                .andExpect(model().attribute("account", account));

        verify(authenticationFacade, atLeastOnce()).getAuthenticatedUser();
        verify(accountService).findById(any(Integer.class));
        verify(operationService).findAllOperationsByAccountId(any(Integer.class), any(Pageable.class));
    }

    @Test
    public void whenShowUsersAccountsDetailsIsCorrect_thenDeposit() throws Exception {
        User user = getUser();
        Account depositAccount = getAccount(AccountType.DEPOSIT);
        PageImpl<Operation> operations = new PageImpl<>(Collections.singletonList(getOperation()));

        when(authenticationFacade.getAuthenticatedUser()).thenReturn(user);
        when(accountService.findById(any(Integer.class))).thenReturn(depositAccount);
        when(operationService.findAllOperationsByAccountId(any(Integer.class), any(Pageable.class))).thenReturn(operations);

        mvc.perform(get("/user/accountDetails")
                .param("page", "0")
                .param("type", "DEPOSIT")
                .param("id", "1"))
                .andExpect(view().name("depositAccountDetails"))
                .andExpect(model().attribute("user", user))
                .andExpect(model().attribute("page", operations))
                .andExpect(model().attribute("account", depositAccount));

        verify(authenticationFacade, atLeastOnce()).getAuthenticatedUser();
        verify(accountService).findById(any(Integer.class));
        verify(operationService).findAllOperationsByAccountId(any(Integer.class), any(Pageable.class));
    }

    @Test
    public void whenShowUsersAccountsDetailsDoesNotExist_thenReturnErrorPage() throws Exception {
        User user = getUser();

        when(authenticationFacade.getAuthenticatedUser()).thenReturn(user);
        when(accountService.findById(any(Integer.class))).thenThrow(NoSuchElementException.class);

        mvc.perform(get("/user/accountDetails")
                .param("page", "0")
                .param("type", "DEPOSIT")
                .param("id", "1"))
                .andExpect(view().name("error"));

        verifyNoMoreInteractions(operationService);
    }

    @Test
    public void whenShowDepositAccountRegistrationFormParametersIsOk_thenOk() throws Exception {
        User user = ObjectCreator.getUser();
        Request request = ObjectCreator.getRequest();

        when(userService.findById(any(Integer.class))).thenReturn(user);
        when(requestService.findById(any(Integer.class))).thenReturn(request);
        when(authenticationFacade.getAuthenticatedUser()).thenReturn(user);

        mvc.perform(get("/admin/accountRegistration")
                .param("userId", "1")
                .param("type", "DEPOSIT")
                .param("requestId", "1"))
                .andExpect(view().name("depositAccountRegistration"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account", "user"));

        verify(userService).findById(anyInt());
        verify(requestService).findById(anyInt());

    }

    @Test
    public void whenShowCreditAccountRegistrationFormParametersIsOk_thenOk() throws Exception {
        User user = ObjectCreator.getUser();
        Request request = ObjectCreator.getRequest();

        when(userService.findById(any(Integer.class))).thenReturn(user);
        when(requestService.findById(any(Integer.class))).thenReturn(request);
        when(authenticationFacade.getAuthenticatedUser()).thenReturn(user);

        mvc.perform(get("/admin/accountRegistration")
                .param("userId", "1")
                .param("type", "CREDIT")
                .param("requestId", "1"))
                .andExpect(view().name("creditAccountRegistration"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("account", "user"));

        verify(userService).findById(anyInt());
        verify(requestService).findById(anyInt());
    }

    @Test
    public void whenShowAccountRegistrationFormUserIdNotCorrect_thenErrorPage() throws Exception {
        when(userService.findById(anyInt())).thenThrow(NoSuchElementException.class);

        mvc.perform(get("/admin/accountRegistration")
                .param("userId", "1")
                .param("type", "DEPOSIT")
                .param("requestId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));

        verifyNoInteractions(requestService,authenticationFacade);
    }

}
