package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User login(String email, String password);

    void register(User user);
}
