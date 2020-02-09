package com.epam.bankproject.bankproject.domain.impl;

import com.epam.bankproject.bankproject.domain.Account;
import com.epam.bankproject.bankproject.enums.ChargeType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;

@Data
@Builder(toBuilder = true)
public class Charge {

    private final Integer id;

    @NotEmpty
    @NonNull
    private final Double chargeAmount;

    @NotEmpty
    @NonNull
    private final ChargeType chargeType;

    @NotEmpty
    @NonNull
    private final Account account;

}
