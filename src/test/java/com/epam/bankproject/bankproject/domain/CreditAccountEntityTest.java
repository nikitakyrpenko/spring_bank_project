package com.epam.bankproject.bankproject.domain;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreditAccountEntityTest {
    public CreditAccount creditAccount;

    @Parameterized.Parameter(0)
    public Double creditLimit;

    @Parameterized.Parameter(1)
    public Double creditRate ;

    @Parameterized.Parameter(2)
    public Double expectedCharge;

    @Parameterized.Parameters
    public static Collection data(){
        return Arrays.asList(
                new Object[][]{
                        {50000.0, 0.2, 1858.17},
                        {30000.0, 0.12, 996.43},
                        {5000.0, 0.12, 166.07}
                }
        );
    }

    @Before
    public void init(){
         creditAccount = CreditAccount
                 .builder()
                 .limit(creditLimit)
                 .creditRate(creditRate)
                 .build();
    }

    @Test
    public void getChargeShouldReturnValueByCreditLimitAndCreditRate(){
        assertEquals(expectedCharge,creditAccount.getCharge(),0.1);
    }


    //TODO HOW TO VALIDATE DOUBLE MIN
   /* @Test(expected = IllegalArgumentException.class)
    public void getChargeThrowIllegalArgumentExceptionIfCreditRateLessThanZero(){
        CreditAccount creditAccount = CreditAccount.builder()
                .limit(0.0)
                .rate(0.0)
                .build();
        creditAccount.getCharge();
    }
*/

}
