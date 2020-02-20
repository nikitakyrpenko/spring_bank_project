package com.epam.bankproject.bankproject.contoller.util;

public class DepositAccountData {

    private String expirationDate;
    private Double depositAmount;
    private Double depositRate;
    private Integer owner;
    private Integer requestId;

    public DepositAccountData(Integer owner, Integer requestId){
        this.owner = owner;
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "DepositAccountData{" +
                "expirationDate='" + expirationDate + '\'' +
                ", depositAmount=" + depositAmount +
                ", depositRate=" + depositRate +
                ", owner=" + owner +
                ", requestId=" + requestId +
                '}';
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Double getDepositRate() {
        return depositRate;
    }

    public void setDepositRate(Double depositRate) {
        this.depositRate = depositRate;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }
}
