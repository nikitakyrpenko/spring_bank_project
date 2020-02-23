package com.epam.bankproject.bankproject.controller;

import com.epam.bankproject.bankproject.ObjectCreator;
import com.epam.bankproject.bankproject.contoller.TransactionController;
import com.epam.bankproject.bankproject.contoller.util.AuthenticationFacade;
import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.Operation;
import com.epam.bankproject.bankproject.domain.User;
import com.epam.bankproject.bankproject.service.AccountService;
import com.epam.bankproject.bankproject.service.OperationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import static com.epam.bankproject.bankproject.controller.util.ControllerAdviceHelper.createExceptionResolver;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TransactionControllerTest {

    private MockMvc mvc;

    @Mock
    private OperationService operationService;

    @Mock
    private AccountService accountService;

    @Mock
    private AuthenticationFacade authenticationFacade;

    @InjectMocks
    private TransactionController transactionController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders
                .standaloneSetup(transactionController)
                .setHandlerExceptionResolvers(createExceptionResolver())
                .build();
    }

    @Test
    public void whenShowTransactionViewCorrect_thenOk() throws Exception {
        User user = ObjectCreator.getUser();

        when(authenticationFacade.getAuthenticatedUser()).thenReturn(user);

        mvc.perform(get("/user/transaction")
                .param("accountid", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("transactions"))
                .andExpect(model().attributeExists("user", "operation"));

        verify(authenticationFacade).getAuthenticatedUser();
    }

    @Test
    public void whenShowTransactionViewAccountIdNotCorrect_thenErrorPage() throws Exception {

        when(accountService.findById(any(Integer.class))).thenThrow(NoSuchElementException.class);

        mvc.perform(get("/user/transaction")
                .param("accountid", "99"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));

        verifyNoMoreInteractions(authenticationFacade, operationService);
    }

    @Test
    public void whenShowTransactionViewUserNotAuthenticated_thenErrorPage() throws Exception {
        Account account = mock(Account.class);

        when(accountService.findById(anyInt())).thenReturn(account);
        when(authenticationFacade.getAuthenticatedUser()).thenThrow(NoSuchElementException.class);

        mvc.perform(get("/user/transaction")
                .param("accountid", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));

        verify(accountService).findById(anyInt());
        verifyNoMoreInteractions(operationService);
    }

    @Test
    public void whenSaveTransactionNotValid_thenReturnViewWithValidationError() throws Exception {
        String wrongTransfer = BigDecimal.valueOf(-30).toString();
        String wrongSenderId = "abc";
        String wrongReceiverId = "qwe";

        mvc.perform(post("/user/transaction")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("transfer", wrongTransfer)
                .param("receiverId", wrongReceiverId)
                .param("senderId", wrongSenderId))
                .andExpect(status().is(302))
                .andExpect(model().hasErrors());
        verifyNoMoreInteractions(operationService);
    }

    @Test
    public void whenSaveTransactionValid_thenOk() throws Exception {
        Operation operation = ObjectCreator.getOperation();

        String transfer = BigDecimal.valueOf(40).toString();
        String senderId = "1";
        String receiverId = "2";

        when(operationService.save(any(Operation.class))).thenReturn(operation);

        mvc.perform(post("/user/transaction")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("transfer", transfer)
                .param("receiverId", receiverId)
                .param("senderId", senderId))
                .andExpect(status().is(302))
                .andExpect(model().hasNoErrors());

        verify(operationService).save(any(Operation.class));
    }

}
