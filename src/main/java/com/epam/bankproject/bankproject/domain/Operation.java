package com.epam.bankproject.bankproject.domain;

import lombok.*;
import java.sql.Date;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class Operation {

    private  Integer id;

    private  String purpose;

    private  Double transfer;

    private  Date dateOfOperation;

    private  Account sender;

    private  Account receiver;
}
