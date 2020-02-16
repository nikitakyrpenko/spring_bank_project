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
import com.epam.bankproject.bankproject.service.mapper.impl.OperationMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith( SpringJUnit4ClassRunner.class )
public class OperationMapperTest {

    @Mock
    private Mapper<Account, AccountEntity> accountMapper;

    @InjectMocks
    private OperationMapper operationMapper;

    @After
    public void resetMocks(){
        reset(accountMapper);
    }

    @Test
    public void whenMapDomainToEntity_thenReturnEntity(){
        Operation operation = Operation.builder()
                .id(1)
                .transfer(10.99)
                .purpose("purpose")
                .dateOfOperation(Date.valueOf("2021-01-01"))
                .receiver(DepositAccount.builder().build())
                .sender(CreditAccount.builder()
                        .creditRate(0.1)
                        .limit(100.0)
                        .build())
                .build();



        OperationEntity expected = new OperationEntity();
        expected.setId(1);
        expected.setPurpose("purpose");
        expected.setOperationDate(Date.valueOf("2021-01-01"));
        expected.setTransfer(10.99);
        expected.setReceiverAccount(new AccountEntity());
        expected.setSenderAccount(new AccountEntity());

        when(accountMapper.mapDomainToEntity(any(Account.class))).thenReturn(new AccountEntity());

        OperationEntity actual = operationMapper.mapDomainToEntity(operation);

        verify(accountMapper,atLeastOnce()).mapDomainToEntity(any(Account.class));

        assertThat(actual).isEqualToIgnoringGivenFields(expected, "senderAccount","receiverAccount");
    }

    @Test
    public void whenMapEntityToDomain_thenReturnDomain(){

        OperationEntity operationEntity = new OperationEntity();
        operationEntity.setId(1);
        operationEntity.setPurpose("purpose");
        operationEntity.setOperationDate(Date.valueOf("2021-03-12"));
        operationEntity.setTransfer(100.0);
        operationEntity.setReceiverAccount(new AccountEntity());
        operationEntity.setSenderAccount(new AccountEntity());

        Operation expected = Operation.builder()
                .id(1)
                .purpose("purpose")
                .dateOfOperation(Date.valueOf("2021-03-12"))
                .transfer(100.0)
                .sender(DepositAccount.builder().build())
                .receiver(DepositAccount.builder().build())
                .build();

        when(accountMapper.mapEntityToDomain(any(AccountEntity.class))).thenReturn(DepositAccount.builder().build());
        Operation actual = operationMapper.mapEntityToDomain(operationEntity);

        verify(accountMapper,atLeastOnce()).mapEntityToDomain(any(AccountEntity.class));

        assertThat(actual).isEqualToIgnoringGivenFields(expected, "sender","receiver");
    }

}
