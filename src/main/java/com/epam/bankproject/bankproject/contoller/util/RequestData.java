package com.epam.bankproject.bankproject.contoller.util;

import com.epam.bankproject.bankproject.enums.AccountType;

public class RequestData {


    private Integer ownerId;
    private String accountType;

    public RequestData(Integer ownerId){
        this.ownerId = ownerId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "ownerId=" + ownerId +
                ", accountType=" + accountType +
                '}';
    }
}
