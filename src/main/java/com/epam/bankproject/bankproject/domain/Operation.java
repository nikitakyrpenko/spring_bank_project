package com.epam.bankproject.bankproject.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Data
@Builder(toBuilder = true)
public class Operation {

    private final Integer id;

    @NonNull
    @NotEmpty
    private final String purpose;

    @NonNull
    @NotEmpty
    private final Double transfer;

    @NonNull
    @NotEmpty
    private final Date dateOfOperation;

    @NonNull
    private final Account sender;

    @NotEmpty
    private final Account receiver;
}
