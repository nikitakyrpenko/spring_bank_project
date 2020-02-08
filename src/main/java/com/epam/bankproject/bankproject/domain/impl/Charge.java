package com.epam.bankproject.bankproject.domain.impl;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.enums.ChargeType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Charge {

    private final Integer id;
    private final Double chargeAmount;
    private final ChargeType chargeType;
    private final Account account;

}
