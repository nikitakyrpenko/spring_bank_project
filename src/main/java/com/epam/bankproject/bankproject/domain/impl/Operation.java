package com.epam.bankproject.bankproject.domain.impl;

import com.epam.bankproject.bankproject.domain.Account;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
@Builder(toBuilder = true)
public class Operation {

    private final Integer id;
    private final String purpose;
    private final Double transfer;
    private final Date dateOfOperation;
    private final Account sender;
    private final Account receiver;
}
