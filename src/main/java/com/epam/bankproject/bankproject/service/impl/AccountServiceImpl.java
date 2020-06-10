package com.epam.bankproject.bankproject.service.impl;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.repository.AccountRepository;
import com.epam.bankproject.bankproject.service.AccountService;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private Mapper<Account, AccountEntity> accountMapper;

    @Override
    public Page<Account> findAllByOwnerId(@NonNull Integer id, @NonNull Pageable pageable) {
        List<Account> accounts;

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        long totalRecords = accountRepository.countAllByOwnerId(id);

        accounts = accountRepository.findAllByOwnerId(id, pageable)
                .stream()
                .map(accountMapper::mapEntityToDomain)
                .collect(Collectors.toList());

        return new PageImpl<Account>(accounts, PageRequest.of(currentPage, pageSize), totalRecords);
    }

    @Override
    public Account findById(@NonNull Integer id) {
        return accountRepository.findById(id)
                .map(accountMapper::mapEntityToDomain)
                .<NoSuchElementException>orElseThrow(() -> {
                    throw new NoSuchElementException();
        });
    }

    @Override
    public List<Account> findAll(@NonNull Pageable pageable) {
        return accountRepository.findAll(pageable)
                .stream()
                .map(accountMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> findAllByExpirationDate(@NonNull Date date) {
        return accountRepository.findAllByExpirationDate(date)
                .stream()
                .map(accountMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long countAllByOwnerId(@NonNull Integer id) {
        return accountRepository.countAllByOwnerId(id);
    }

    @Override
    public Account save(@NonNull Account account) {
        AccountEntity save = accountRepository.save(accountMapper.mapDomainToEntity(account));
        return accountMapper.mapEntityToDomain(save);
    }
}
