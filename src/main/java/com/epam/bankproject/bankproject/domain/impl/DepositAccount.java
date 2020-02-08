package com.epam.bankproject.bankproject.domain.impl;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.enums.AccountType;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import java.sql.Date;

@ToString(callSuper = true)
@EqualsAndHashCode
public class DepositAccount extends Account {

    @Getter
    public final Double depositAmount;

    @Getter
    @DecimalMin("0.001")
    public final Double rate;

    @Getter
    public final AccountType accountType;

    @Builder
    public DepositAccount(Integer id,
                         Date expirationDate,
                         Double balance,
                         Double depositAmount,
                         Double rate,
                         AccountType accountType) {
        super(id, expirationDate, balance);
        super.setBalance(depositAmount);
        this.depositAmount = depositAmount;
        this.rate          = rate;
        this.accountType   = accountType;
    }

    @Override
    public Double getCharge() {
        return this.getBalance() * rate;
    }
}
