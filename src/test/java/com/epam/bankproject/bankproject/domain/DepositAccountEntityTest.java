package com.epam.bankproject.bankproject.domain;

import com.epam.bankproject.bankproject.domain.impl.DepositAccount;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class DepositAccountEntityTest {

   public DepositAccount depositAccount;

    @Parameterized.Parameter(0)
    public Double deposit;

    @Parameterized.Parameter(1)
    public Double depositRate ;

    @Parameterized.Parameter(2)
    public Double expectedCharge;

    @Parameterized.Parameters
    public static Collection data(){
        return Arrays.asList(
                new Object[][]{
                        {1000.0, 0.13, 130.0},
                        {557.7, 0.09, 50.19},
                        {1000.0, 0.09, 90.00}
                }
        );
    }

    @Before
    public void init(){
        depositAccount = DepositAccount.builder()
                .balance(deposit)
                .depositAmount(deposit)
                .rate(depositRate)
                .build();
    }

    @Test
    public void getChargeShouldReturnChargeValueByDepositAmountAndDepositRate(){
        assertEquals(expectedCharge, depositAccount.getCharge(),0.1);
    }
    /*@Test(expected = IllegalArgumentException.class)
    public void getChargeShouldThrowIllegalArgumentExceptionIfDepositRateLessThenZero(){
        DepositAccount illegalDepositAccount = DepositAccount.builder()
                .balance(1000.0)
                .rate(-0.1)
                .build();
        illegalDepositAccount.getCharge();
    }*/

}
