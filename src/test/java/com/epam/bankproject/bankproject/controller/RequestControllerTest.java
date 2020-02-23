package com.epam.bankproject.bankproject.controller;

import com.epam.bankproject.bankproject.ObjectCreator;
import com.epam.bankproject.bankproject.contoller.RequestController;
import com.epam.bankproject.bankproject.contoller.util.AuthenticationFacade;
import com.epam.bankproject.bankproject.domain.Request;
import com.epam.bankproject.bankproject.domain.User;
import com.epam.bankproject.bankproject.service.RequestService;
import com.epam.bankproject.bankproject.service.UserService;
import org.junit.After;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import static com.epam.bankproject.bankproject.controller.util.ControllerAdviceHelper.createExceptionResolver;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RequestControllerTest {

    private MockMvc mvc;

    @Mock
    private UserService userService;

    @Mock
    private RequestService requestService;

    @Mock
    private AuthenticationFacade authenticationFacade;

    @InjectMocks
    private RequestController requestController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders
                .standaloneSetup(requestController)
                .setHandlerExceptionResolvers(createExceptionResolver())
                .build();
    }

    @After
    public void resetMocks() {
        reset(authenticationFacade, userService, requestService);
    }

    @Test
    public void whenShowRequestsGetMethodIsCorrect_thenOK() throws Exception {
        PageImpl<Request> page = new PageImpl<>(Collections.singletonList(ObjectCreator.getRequest()));
        User user = ObjectCreator.getUser();

        when(requestService.findAll(any(Pageable.class))).thenReturn(page);
        when(authenticationFacade.getAuthenticatedUser()).thenReturn(user);
        when(requestService.countAll()).thenReturn(1L);

        mvc.perform(get("/admin/requests")
                .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("requestedAccounts"))
                .andExpect(model().attributeExists("page", "user"));

        verify(authenticationFacade, atLeastOnce()).getAuthenticatedUser();
        verify(requestService).findAll(any(Pageable.class));
        verify(requestService).countAll();
    }

    @Test
    public void whenSaveAccountRequest_thenOk() throws Exception {
        User user = ObjectCreator.getUser();

        when(userService.findById(any(Integer.class))).thenReturn(user);
        doNothing().when(requestService).save(any(Request.class));

        mvc.perform(get("/user/requestAccount")
                .param("userId", "1")
                .param("type", "DEPOSIT"))
                .andExpect(status().is(302));

        verify(userService).findById(anyInt());
        verify(requestService).save(any(Request.class));
    }

    @Test
    public void whenSaveAccountRequestUserIdNotExist_thenErrorPage() throws Exception {
        when(userService.findById(any(Integer.class))).thenThrow(NoSuchElementException.class);

        mvc.perform(get("/user/requestAccount")
                .param("userId", "1")
                .param("type", "DEPOSIT"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

}
