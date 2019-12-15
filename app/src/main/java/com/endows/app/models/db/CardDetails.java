package com.endows.app.models.db;

import java.util.List;

public class CardDetails {

    private String cardNumber;
    private Integer cardType;
    private String expiryDate;
    private String cvv;
    private String pinNumber;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(String pinNumber) {
        this.pinNumber = pinNumber;
    }

    @Override
    public String toString() {
        return "CardDetails{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardType=" + cardType +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvv='" + cvv + '\'' +
                ", pinNumber='" + pinNumber + '\'' +
                '}';
    }
}
