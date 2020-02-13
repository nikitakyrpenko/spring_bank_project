package com.epam.bankproject.bankproject.mapper;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.CreditAccount;
import com.epam.bankproject.bankproject.domain.DepositAccount;
import com.epam.bankproject.bankproject.domain.Operation;
import com.epam.bankproject.bankproject.domain.User;
import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.entity.OperationEntity;
import com.epam.bankproject.bankproject.entity.UserEntity;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.enums.Role;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
public class OperationMapperTest {

    //TODO SWITCH TO MOCKITO
    @Autowired
    private Mapper<Operation, OperationEntity> operationMapper;

    @Autowired
    private Mapper<Account, AccountEntity> accountMapper;

    @Test
    public void whenMapDomainToEntity_thenReturnEntity(){
        User me = User.builder()
                .id(1)
                .name("Jon")
                .surname("Doe")
                .email("jondoe@gmail.com")
                .password("P@ssword97")
                .telephone("380508321899")
                .role(Role.ROLE_USER)
                .build();

        Account sender = CreditAccount.builder()
                .id(1)
                .expirationDate(Date.valueOf("2021-03-12"))
                .balance(1000.0)
                .accountType(AccountType.CREDIT)
                .owner(me)
                .limit(1000.0)
                .creditRate(0.1)
                .liability(0.0)
                .build();

        Account receiver = DepositAccount.builder()
                .id(2)
                .balance(1000.0)
                .depositAmount(1000.0)
                .expirationDate(Date.valueOf("2021-03-12"))
                .depositRate(0.2)
                .owner(me)
                .accountType(AccountType.DEPOSIT)
                .build();

        Operation operation = Operation.builder()
                .id(1)
                .transfer(10.99)
                .purpose("purpose")
                .dateOfOperation(Date.valueOf("2021-01-01"))
                .receiver(receiver)
                .sender(sender)
                .build();

        OperationEntity expected = new OperationEntity();
        expected.setId(1);
        expected.setPurpose("purpose");
        expected.setOperationDate(Date.valueOf("2021-01-01"));
        expected.setTransfer(10.99);
        expected.setReceiverAccount(accountMapper.mapDomainToEntity(receiver));
        expected.setSenderAccount(accountMapper.mapDomainToEntity(sender));

        OperationEntity actual = operationMapper.mapDomainToEntity(operation);
        System.out.println(actual);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void whenMapEntityToDomain_thenReturnDomain(){
        UserEntity meEntity = new UserEntity();
        meEntity.setId(1);
        meEntity.setName("Jon");
        meEntity.setSurname("Doe");
        meEntity.setEmail("jondoe@gmail.com");
        meEntity.setPassword("P@ssword97");
        meEntity.setTelephone("380508321899");
        meEntity.setRole(Role.ROLE_USER);

        AccountEntity receiver = new AccountEntity();
        receiver.setId(1);
        receiver.setExpirationDate(Date.valueOf("2021-01-01"));
        receiver.setBalance(1000.0);
        receiver.setDepositRate(0.1);
        receiver.setOwner(meEntity);
        receiver.setAccountType(AccountType.DEPOSIT);

        AccountEntity sender = new AccountEntity();
        sender.setId(2);
        sender.setExpirationDate(Date.valueOf("2021-12-12"));
        sender.setBalance(1000.0);
        sender.setDepositRate(0.1);
        sender.setOwner(meEntity);
        sender.setAccountType(AccountType.DEPOSIT);

        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setId(1);
        operationEntity.setPurpose("purpose");
        operationEntity.setOperationDate(Date.valueOf("2021-03-12"));
        operationEntity.setTransfer(100.0);
        operationEntity.setReceiverAccount(receiver);
        operationEntity.setSenderAccount(sender);

        Operation expected = Operation.builder()
                .id(1)
                .purpose("purpose")
                .dateOfOperation(Date.valueOf("2021-03-12"))
                .transfer(100.0)
                .sender(accountMapper.mapEntityToDomain(sender))
                .receiver(accountMapper.mapEntityToDomain(receiver))
                .build();

        Operation actual = operationMapper.mapEntityToDomain(operationEntity);
        System.out.println(actual);
        assertThat(actual).isEqualTo(expected);
    }

}
