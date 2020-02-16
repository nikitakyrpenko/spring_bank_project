package com.epam.bankproject.bankproject.service;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.CreditAccount;
import com.epam.bankproject.bankproject.domain.DepositAccount;
import com.epam.bankproject.bankproject.domain.Operation;
import com.epam.bankproject.bankproject.entity.OperationEntity;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.repository.OperationRepository;
import com.epam.bankproject.bankproject.service.exception.MonetaryException;
import com.epam.bankproject.bankproject.service.impl.OperationServiceImpl;
import com.epam.bankproject.bankproject.service.mapper.Mapper;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OperationServiceImplTest {

    public static  final Operation NOT_VALID_OPERATION;
    public static final Operation VALID_OPERATION;
    public static final Account SENDER;
    public static final Account RECEIVER;
    public static final List<OperationEntity> OPERATION_ENTITIES = new ArrayList<>();
    static {
        SENDER = CreditAccount.builder()
                .id(1)
                .expirationDate(Date.valueOf("2021-03-12"))
                .balance(1000.0)
                .accountType(AccountType.CREDIT)
                .limit(1000.0)
                .creditRate(0.1)
                .liability(0.0)
                .build();

        RECEIVER = DepositAccount.builder()
                .id(1)
                .expirationDate(Date.valueOf("2021-03-12"))
                .depositAmount(3000.0)
                .depositRate(0.1)
                .accountType(AccountType.DEPOSIT)
                .build();

        NOT_VALID_OPERATION = Operation.builder()
                .transfer(2000.0)
                .purpose("test exception")
                .dateOfOperation(Date.valueOf("2021-03-12"))
                .sender(SENDER)
                .receiver(RECEIVER)
                .build();

        VALID_OPERATION = Operation.builder()
                .transfer(730.0)
                .purpose("test exception")
                .dateOfOperation(Date.valueOf("2021-03-12"))
                .sender(RECEIVER)
                .receiver(SENDER)
                .build();

        OperationEntity expected = new OperationEntity();
        expected.setId(1);
        expected.setPurpose("purpose");
        expected.setOperationDate(Date.valueOf("2021-01-01"));
        expected.setTransfer(10.99);

        OPERATION_ENTITIES.add(expected);
    }
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private Mapper<Operation, OperationEntity> operationMapper;

    @InjectMocks
    private OperationServiceImpl operationService;

    @After
    public void resetMocks(){
        reset(operationRepository,operationMapper);
    }

    @Test
    public void whenSaveWithSenderNotEnoughBalance_thenThrowMonetaryException(){
        expectedException.expect(MonetaryException.class);
        expectedException.expectMessage("Not enough balance to perform such operation");

        operationService.save(NOT_VALID_OPERATION);

        verify(operationMapper,never()).mapDomainToEntity(any(Operation.class));
        verify(operationMapper,never()).mapEntityToDomain(any(OperationEntity.class));
        verify(operationRepository,never()).save(any(OperationEntity.class));
    }

    @Test
    public void whenSaveValid_thenReturnOperation(){
        OperationEntity operationEntityMock = mock(OperationEntity.class);
        Operation operationMock = mock(Operation.class);

        when(operationMapper.mapDomainToEntity(any(Operation.class))).thenReturn(operationEntityMock);
        when(operationRepository.save(any(OperationEntity.class))).thenReturn(operationEntityMock);
        when(operationMapper.mapEntityToDomain(any(OperationEntity.class))).thenReturn(operationMock);

        operationService.save(VALID_OPERATION);

        assertEquals(Double.valueOf(2270.0), RECEIVER.getBalance());
        assertEquals(Double.valueOf(1730.0), SENDER.getBalance());

        verify(operationRepository,times(1)).save(any(OperationEntity.class));
        verify(operationMapper,times(1)).mapEntityToDomain(any(OperationEntity.class));
        verify(operationMapper,times(1)).mapDomainToEntity(any(Operation.class));
    }

    @Test
    public void whenSavePassedNull_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("operation is marked non-null but is null");
        operationService.save(null);
    }

    @Test
    public void whenFindAllOperationByAccountId_thenReturnOperationCollection(){
        Operation operationMock = mock(Operation.class);

        when(operationRepository.findAllOperationsByAccountId(any(Integer.class),any(Pageable.class))).thenReturn(OPERATION_ENTITIES);
        when(operationMapper.mapEntityToDomain(any(OperationEntity.class))).thenReturn(operationMock);

        operationService.findAllOperationsByAccountId(1, PageRequest.of(0,1));

        verify(operationRepository,times(1)).findAllOperationsByAccountId(any(Integer.class),any(Pageable.class));
        verify(operationMapper,atLeastOnce()).mapEntityToDomain(any(OperationEntity.class));
    }

    @Test
    public void whenFindAllOperationByAccountIdPassedNull_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("id is marked non-null but is null");
        operationService.findAllOperationsByAccountId(null, PageRequest.of(1,2));
    }

    @Test
    public void whenFindAllOperationByAccountPageablePassedNull_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("pageable is marked non-null but is null");
        operationService.findAllOperationsByAccountId(1, null);
    }

    @Test
    public void whenFindAllByReceiverAccountId_thenReturnOperationCollection(){
        Operation operationMock = mock(Operation.class);
        when(operationRepository.findAllByReceiverAccountId(any(Integer.class),any(Pageable.class))).thenReturn(OPERATION_ENTITIES);
        when(operationMapper.mapEntityToDomain(any(OperationEntity.class))).thenReturn(operationMock);

        operationService.findAllByReceiverAccountId(1, PageRequest.of(0,1));

        verify(operationRepository,times(1)).findAllByReceiverAccountId(any(Integer.class),any(Pageable.class));
        verify(operationMapper,atLeastOnce()).mapEntityToDomain(any(OperationEntity.class));
    }

    @Test
    public void whenFindAllByReceiverAccountIdPageablePassedNull_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("pageable is marked non-null but is null");
        operationService.findAllByReceiverAccountId(1, null);
    }

    @Test
    public void whenFindAllByReceiverAccountIdPassedNull_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("id is marked non-null but is null");
        operationService.findAllByReceiverAccountId(null, PageRequest.of(1,2));
    }

    @Test
    public void whenFindAllBySenderAccountId_thenReturnOperationCollection(){
        Operation operationMock = mock(Operation.class);
        when(operationRepository.findAllBySenderAccountId(any(Integer.class),any(Pageable.class))).thenReturn(OPERATION_ENTITIES);
        when(operationMapper.mapEntityToDomain(any(OperationEntity.class))).thenReturn(operationMock);

        operationService.findAllBySenderAccountId(1, PageRequest.of(0,1));

        verify(operationRepository,times(1)).findAllBySenderAccountId(any(Integer.class),any(Pageable.class));
        verify(operationMapper,atLeastOnce()).mapEntityToDomain(any(OperationEntity.class));
    }

    @Test
    public void whenFindAllBySenderAccountIdPageablePassedNull_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("pageable is marked non-null but is null");
        operationService.findAllBySenderAccountId(1, null);
    }

    @Test
    public void whenFindAllBySenderAccountIdPassedNull_thenThrowException(){
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("id is marked non-null but is null");
        operationService.findAllBySenderAccountId(null, PageRequest.of(1,2));
    }
}
