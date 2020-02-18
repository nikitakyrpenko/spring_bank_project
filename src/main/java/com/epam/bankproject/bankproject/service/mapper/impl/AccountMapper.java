package com.epam.bankproject.bankproject.service.mapper.impl;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.CreditAccount;
import com.epam.bankproject.bankproject.domain.DepositAccount;
import com.epam.bankproject.bankproject.domain.User;
import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.entity.UserEntity;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper implements Mapper<Account, AccountEntity> {

    @Autowired
    private Mapper<User, UserEntity> userMapper;

    @Override
    public AccountEntity mapDomainToEntity(Account domain) {
        AccountEntity accountEntity = new AccountEntity();

        accountEntity.setId(domain.getId());
        accountEntity.setBalance(domain.getBalance());
        accountEntity.setAccountType(domain.getAccountType());
        accountEntity.setExpirationDate(domain.getExpirationDate());
        accountEntity.setOwner(userMapper.mapDomainToEntity(domain.getOwner()));

        if (domain.getAccountType() == AccountType.DEPOSIT) {
            DepositAccount depositAccount = (DepositAccount) domain;
            accountEntity.setDepositRate(depositAccount.getRate());
        } else if (domain.getAccountType() == AccountType.CREDIT) {
            CreditAccount creditAccount = (CreditAccount) domain;
            accountEntity.setCreditCharge(creditAccount.getCharge());
            accountEntity.setCreditLimit(creditAccount.getLimit());
            accountEntity.setCreditRate(creditAccount.getCreditRate());
            accountEntity.setCreditLiability(creditAccount.getLiability());
        }
        return accountEntity;
    }

    @Override
    public Account mapEntityToDomain(AccountEntity entity) {
        if (entity.getAccountType() == AccountType.DEPOSIT) {
            return DepositAccount.builder()
                    .id(entity.getId())
                    .expirationDate(entity.getExpirationDate())
                    .balance(entity.getBalance())
                    .depositAmount(entity.getBalance())
                    .depositRate(entity.getDepositRate())
                    .accountType(entity.getAccountType())
                    .owner(userMapper.mapEntityToDomain(entity.getOwner()))
                    .build();
        }
        return CreditAccount.builder()
                .id(entity.getId())
                .expirationDate(entity.getExpirationDate())
                .balance(entity.getBalance())
                .limit(entity.getCreditLimit())
                .creditRate(entity.getCreditRate())
                .liability(entity.getCreditLiability())
                .accountType(entity.getAccountType())
                .owner(userMapper.mapEntityToDomain(entity.getOwner()))
                .build();

    }
}
