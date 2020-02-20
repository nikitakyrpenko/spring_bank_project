package com.epam.bankproject.bankproject.config;

import com.epam.bankproject.bankproject.domain.User;
import com.epam.bankproject.bankproject.enums.Role;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        if (user.getRole() == Role.ROLE_USER){
            httpServletResponse.sendRedirect("/user/accounts?page=0");
        }else{
            httpServletResponse.sendRedirect("/admin/requests?page=0");
        }
    }
}
