package com.epam.bankproject.bankproject.repository;

import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.entity.OperationEntity;
import com.epam.bankproject.bankproject.entity.UserEntity;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.enums.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OperationRepositoryTest {

    @Autowired
    private OperationRepository operationRepository;

    @Test
    public void whenFindAllOperationsByAccountIdPageable_thenReturnListOfOperationEntities(){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(3);

        List<OperationEntity> operations = operationRepository.findAllOperationsByAccountId(
                accountEntity.getId(),
                PageRequest.of(2, 2));

        assertTrue(operations.size() <= 2);

        assertEquals(1, operations.size() );

    }

    @Test
    public void test(){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1);
        userEntity.setName("Freya");
        userEntity.setSurname("Doe");
        userEntity.setEmail("dolor.Donec@etmagnaPraesent.net");
        userEntity.setTelephone("380984895779");
        userEntity.setPassword("P@ssword97");
        userEntity.setRole(Role.ROLE_USER);

        AccountEntity deposit = new AccountEntity();
        deposit.setId(1);
        deposit.setExpirationDate(Date.valueOf("2021-03-12"));
        deposit.setBalance(1000.0);
        deposit.setDepositRate(0.2);
        deposit.setAccountType(AccountType.DEPOSIT);
        deposit.setOwner(userEntity);

        AccountEntity credit = new AccountEntity();
        credit.setId(2);
        credit.setExpirationDate(Date.valueOf("2021-03-12"));
        credit.setBalance(1000.0);
        credit.setCreditRate(0.2);
        credit.setCreditLimit(50000.0);
        credit.setCreditLiability(0.0);
        credit.setCreditCharge(1858.17);
        credit.setAccountType(AccountType.CREDIT);
        credit.setOwner(userEntity);

        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setTransfer(100.0);
        operationEntity.setPurpose("purpose");
        operationEntity.setOperationDate(Date.valueOf("2020-01-01"));
        operationEntity.setReceiverAccount(credit);
        operationEntity.setSenderAccount(deposit);

        OperationEntity save = operationRepository.save(operationEntity);

        assertEquals(operationEntity, save);

    }

    @Test
    public void whenFindAllOperationsByAccountId_thenReturnListOfOperationEntities(){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(3);
        assertEquals(5, operationRepository.findAllOperationsByAccountId(accountEntity.getId()).size());
    }

    @Test
    public void whenFindAllByReceiverAccountId_thenReturnListOfOperationEntities(){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(3);

        List<OperationEntity> operations = operationRepository.findAllByReceiverAccountId(
                accountEntity.getId(),
                PageRequest.of(0, 2));

        assertTrue(operations.size() <= 2);

        operations
                .forEach(operationEntity -> assertEquals(accountEntity.getId(), operationEntity.getReceiverAccount().getId()));
    }

    @Test
    public void whenFindAllBySenderAccountId_thenReturnListOfOperationEntities(){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(3);

        List<OperationEntity> operations = operationRepository.findAllBySenderAccountId(
                accountEntity.getId(),
                PageRequest.of(0, 2));

        assertTrue(operations.size() <= 2);

        operations
                .forEach(operationEntity -> assertEquals(accountEntity.getId(), operationEntity.getSenderAccount().getId()));
    }
}
