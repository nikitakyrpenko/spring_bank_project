package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User login(String email, String password);

    User findById(Integer id);

    void register(User user);
}
