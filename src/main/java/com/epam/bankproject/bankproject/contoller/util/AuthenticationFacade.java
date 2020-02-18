package com.epam.bankproject.bankproject.contoller.util;

import com.epam.bankproject.bankproject.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {

    public User getAuthenticatedUser(){
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
