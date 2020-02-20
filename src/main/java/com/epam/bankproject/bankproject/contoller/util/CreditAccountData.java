package com.epam.bankproject.bankproject.contoller.util;

public class CreditAccountData {

    private String expirationDate;
    private Double creditLimit;
    private Double creditRate;
    private Integer owner;
    private Integer requestId;
    private Double balance;

    public CreditAccountData(Integer owner, Integer requestId) {
        this.owner = owner;
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "CreditAccountData{" +
                "expirationDate='" + expirationDate + '\'' +
                ", creditLimit=" + creditLimit +
                ", creditRate=" + creditRate +
                ", owner=" + owner +
                ", requestId=" + requestId +
                ", balance=" + balance +
                '}';
    }

    public Integer getOwner() {
        return owner;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public Double getCreditRate() {
        return creditRate;
    }

    public void setCreditRate(Double creditRate) {
        this.creditRate = creditRate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
