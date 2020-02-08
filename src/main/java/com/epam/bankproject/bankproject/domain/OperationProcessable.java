package com.epam.bankproject.bankproject.domain;

import com.epam.bankproject.bankproject.domain.impl.Operation;

public interface OperationProcessable {

    void processTransfer(Operation operation);
}
