package com.epam.bankproject.bankproject;

import com.epam.bankproject.bankproject.domain.*;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.enums.ChargeType;
import com.epam.bankproject.bankproject.enums.Role;

import java.sql.Date;

public class ObjectCreator {

    public static User getUser() {
        return User.builder()
                .id(1)
                .name("Mykyta")
                .surname("Kyrpenko")
                .password("P@ssword97")
                .email("nikitakyrpenko@gmail.com")
                .telephone("380508321899")
                .role(Role.ROLE_USER)
                .build();
    }

    public static Account getAccount(AccountType accountType) {
        if (accountType == AccountType.DEPOSIT) {
            Account expected = DepositAccount.builder()
                    .id(1)
                    .balance(1000.0)
                    .depositAmount(1000.0)
                    .expirationDate(Date.valueOf("2021-03-12"))
                    .depositRate(0.2)
                    .accountType(AccountType.DEPOSIT)
                    .build();
        }
        return CreditAccount.builder()
                .id(1)
                .expirationDate(Date.valueOf("2021-03-12"))
                .balance(1000.0)
                .creditRate(0.2)
                .limit(50000.0)
                .liability(0.0)
                .accountType(AccountType.CREDIT)
                .build();
    }

    public static Charge getCharge() {
        return Charge.builder()
                .id(1)
                .chargeAmount(100.0)
                .chargeType(ChargeType.CREDIT_ARRIVAL)
                .account(DepositAccount.builder().build())
                .build();
    }

    public static Request getRequest() {
        return Request.builder()
                .owner(null)
                .accountType(AccountType.DEPOSIT)
                .build();
    }

    public static Operation getOperation() {
        return Operation.builder()
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
    }
}
