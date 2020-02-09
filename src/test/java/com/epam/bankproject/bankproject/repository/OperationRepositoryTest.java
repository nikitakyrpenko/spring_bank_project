package com.epam.bankproject.bankproject.repository;

import com.epam.bankproject.bankproject.domain.impl.User;
import com.epam.bankproject.bankproject.entity.AccountEntity;
import com.epam.bankproject.bankproject.entity.OperationEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

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
