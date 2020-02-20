package com.epam.bankproject.bankproject.domain;

import com.epam.bankproject.bankproject.enums.AccountType;
import com.epam.bankproject.bankproject.enums.ChargeType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import java.sql.Date;

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreditAccount extends Account implements InterestChargeable {

    private static final int CREDIT_PERIOD = 12;
    private static final int TOTAL_CREDIT_PERIOD_IN_MONTH = 36;

    @Getter
    private final Double limit;

    @Getter
    private final Double creditRate;

    @Getter
    private final AccountType accountType;

    @Getter
    private  Double charge;

    @Getter
    private Double liability;

    @Builder
    public CreditAccount(Integer id,
                         Date expirationDate,
                         Double balance,
                         Double limit,
                         Double creditRate,
                         Double liability,
                         User owner,
                         AccountType accountType) {
        super(id, expirationDate, balance, owner);
        super.setBalance(limit);
        this.limit = limit;
        this.creditRate = creditRate;
        this.accountType = accountType;
        this.liability = liability;
        this.charge = calculateCreditLiabilityPerMonth();
    }

    private Double percents() {
        return creditRate / CREDIT_PERIOD;
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
