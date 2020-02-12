package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.impl.User;
import lombok.NonNull;

public interface UserService {

    User login(String email, String password);

    void register(User user);
}
