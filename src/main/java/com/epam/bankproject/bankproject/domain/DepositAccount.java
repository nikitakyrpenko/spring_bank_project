package com.epam.bankproject.bankproject.domain;

import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.enums.ChargeType;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import java.sql.Date;

@ToString(callSuper = true)
@EqualsAndHashCode
public class DepositAccount extends Account implements InterestChargeable {

    @Getter
    @Setter
    private Double depositAmount;

    @Getter
    @Setter
    private Double rate;

    @Getter
    @Setter
    private AccountType accountType;

    @Builder
    public DepositAccount(Integer id,
                         Date expirationDate,
                         Double balance,
                         Double depositAmount,
                         Double depositRate,
                         User owner,
                         AccountType accountType) {
        super(id, expirationDate, balance, owner);
        super.setBalance(depositAmount);
        this.depositAmount = depositAmount;
        this.rate          = depositRate;
        this.accountType   = accountType;
    }

    @Override
    public Double getCharge() {
        return this.getBalance() * rate;
    }

    @Override
    public Charge processCharge() {
        setBalance(getBalance() + getCharge());
        return Charge.builder()
                .chargeAmount(getCharge())
                .chargeType(ChargeType.DEPOSIT_ARRIVAL)
                .account(this)
                .build();
    }
}
