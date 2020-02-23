package com.epam.bankproject.bankproject.view.data;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestData {

    @NotNull
    private final Integer ownerId;

    @NotNull
    private String accountType;

}
