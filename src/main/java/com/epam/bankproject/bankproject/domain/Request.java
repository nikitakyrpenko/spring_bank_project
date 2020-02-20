package com.epam.bankproject.bankproject.domain;

import com.epam.bankproject.bankproject.enums.AccountType;
import lombok.*;

@Builder(toBuilder = true)
@Data
public class Request {

    private Integer id;

    private final User owner;

    private final AccountType accountType;

}
