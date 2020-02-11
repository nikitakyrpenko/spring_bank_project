package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.entity.AccountEntity;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> findAllByOwnerId(@NonNull Integer id, Pageable pageable);

    List<Account> findAllByOwnerId(@NonNull Integer id);

    Account findById(@NonNull Integer id);

    Account save(@NonNull Account account);

    List<Account> findAll(Pageable pageable);

    List<Account> findAll();

}
