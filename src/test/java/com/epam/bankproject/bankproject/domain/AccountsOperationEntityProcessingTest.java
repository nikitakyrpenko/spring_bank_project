package com.epam.bankproject.bankproject.domain;

import com.epam.bankproject.bankproject.domain.impl.CreditAccount;
import com.epam.bankproject.bankproject.domain.impl.DepositAccount;
import com.epam.bankproject.bankproject.domain.impl.Operation;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class AccountsOperationEntityProcessingTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    public Account sender;
    public Account receiver;
    public Operation operationEntity;

    @Parameterized.Parameter(0)
    public Double transfer;

    @Parameterized.Parameter(1)
    public Double senderAccountBalance;

    @Parameterized.Parameter(2)
    public Double receiverAccountBalance;

    @Parameterized.Parameter(3)
    public Double expectedSenderBalance;

    @Parameterized.Parameter(4)
    public Double expectedReceiverBalance;

    @Parameterized.Parameters
    public static Collection data(){
        return Arrays.asList(
                new Object[][]{
                        {45.90, 1000.0, 130.0, 954.10, 175.90},
                        {1200.0, 5780.0, 0.0, 4580.0, 1200.0},
                        {0.54, 1.0, 0.0, 0.46, 0.54}
                }
        );
    }

    @Before
    public void init(){
        sender = DepositAccount.builder()
                .depositAmount(senderAccountBalance)
                .depositRate(0.1)
                .build();


        receiver = CreditAccount.builder()
                .balance(receiverAccountBalance)
                .limit(0.1)
                .creditRate(0.1)
                .build();

        operationEntity = Operation.builder()
                .transfer(transfer)
                .purpose("test")
                .sender(sender)
                .receiver(receiver)
                .dateOfOperation(Date.valueOf("2020-01-01"))
                .build();

        sender.processTransfer(operationEntity);
        receiver.processTransfer(operationEntity);
    }

    @Test
    public void getBalanceShouldReturnProperBalanceAfterTransactionAndContainItAfterOperationBeingProcessed(){
        assertEquals(expectedReceiverBalance,receiver.getBalance(),0.01);
        assertEquals(expectedSenderBalance,sender.getBalance(),0.01);

    }




}
