package com.epam.bankproject.bankproject.domain;

import com.epam.bankproject.bankproject.domain.impl.Operation;
import com.epam.bankproject.bankproject.domain.impl.User;
import com.epam.bankproject.bankproject.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@ToString
@AllArgsConstructor
public abstract class Account implements OperationProcessable{

    @Getter
    private final Integer id;

    @Getter
    private final Date expirationDate;

    @Getter
    @Setter
    private Double balance;

    @Getter
    private User owner;


    @Override
    public void processTransfer(Operation operation) {
        final Double transfer = operation.getTransfer();
        final Account sender = operation.getSender();
        final Account receiver = operation.getReceiver();

        if (this.equals(sender)){
            this.balance = this.balance - transfer;
        }else if (this.equals(receiver)){
            this.balance = this.balance + transfer;
        }
    }

    public abstract Double getCharge();

    public abstract AccountType getAccountType();
}
