package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.entity.AccountEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    Page<Account> findAllByOwnerId(Integer id, Pageable pageable);

    Account findById(Integer id);

    Account save(Account account);

    List<Account> findAll(Pageable pageable);

    List<Account> findAll();

    List<Account> findAllByExpirationDate(Date date);

    long countAllByOwnerId(Integer id);

}
