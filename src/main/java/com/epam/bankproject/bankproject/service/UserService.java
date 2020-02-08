package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.impl.User;

public interface UserService {

    User login(String email, String password);

    void register(User user);

}
