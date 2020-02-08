package com.epam.bankproject.bankproject.domain.impl;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.domain.InterestChargeable;
import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.enums.ChargeType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.sql.Date;

@ToString(callSuper = true)
@EqualsAndHashCode
public class CreditAccount extends Account implements InterestChargeable {

    private static final int CREDIT_PERIOD                = 12;
    private static final int TOTAL_CREDIT_PERIOD_IN_MONTH = 36;

    @Getter
    private final Double limit;

    @Getter
    @DecimalMin("0.001")
    private final Double rate;

    @Getter
    private final AccountType accountType;

    @Getter
    private final Double charge;

    @Getter
    private Double liability;

    @Builder
    public CreditAccount(Integer id,
                         Date expirationDate,
                         Double balance,
                         Double limit,
                         Double rate,
                         Double liability,
                         AccountType accountType){
        super(id,expirationDate,balance);
        this.limit       = limit;
        this.rate        = rate;
        this.accountType = accountType;
        this.liability   = liability;
        this.charge      = calculateCreditLiabilityPerMonth();
    }

    private Double percents() {
        return rate / CREDIT_PERIOD;
    }

    private Double calculateCreditLiabilityPerMonth() {
        return (limit * percents()) / (1 - Math.pow(1 + percents(), -TOTAL_CREDIT_PERIOD_IN_MONTH));
    }

    @Override
    public Charge processCharge() {
        this.liability = this.liability + this.charge;
        return Charge.builder()
                .chargeAmount(this.charge)
                .chargeType(ChargeType.CREDIT_ARRIVAL)
                .account(this)
                .build();
    }
}
