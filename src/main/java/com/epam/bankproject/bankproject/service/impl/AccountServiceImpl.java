package com.epam.bankproject.bankproject.service.impl;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.repository.AccountRepository;
import com.epam.bankproject.bankproject.service.AccountService;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.stream.Collectors;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private Mapper<Account, AccountEntity> accountMapper;


    @Override
    public List<Account> findAllByOwnerId(@NonNull Integer id, @NonNull Pageable pageable) {
        return accountRepository.findAllByOwnerId(id, pageable)
                .stream()
                .map(accountMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> findAllByOwnerId(@NonNull Integer id) {
        return accountRepository.findAllByOwnerId(id)
                .stream()
                .map(accountMapper::mapEntityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Account findById(@NonNull Integer id) {
        return accountRepository.findById(id)
                .map(accountMapper::mapEntityToDomain)
                .orElseThrow();
    }

    @Override
    public List<Account> findAll(Pageable pageable) {
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
    public Account save(@NonNull Account account) {
        AccountEntity save = accountRepository.save(accountMapper.mapDomainToEntity(account));
        return accountMapper.mapEntityToDomain(save);
    }
}
