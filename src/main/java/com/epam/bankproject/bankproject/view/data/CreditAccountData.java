package com.epam.bankproject.bankproject.view.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class CreditAccountData {

    @NotEmpty
    private String expirationDate;

    @NotNull
    @DecimalMin(value = "0",inclusive = false)
    private BigDecimal creditLimit;

    @NotNull
    @DecimalMin(value = "1")
    @DecimalMax(value = "100")
    private BigDecimal creditRate;

    @NotNull
    private final Integer owner;

    @NotNull
    private final Integer requestId;



}
