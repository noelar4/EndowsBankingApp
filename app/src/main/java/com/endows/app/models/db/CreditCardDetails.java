package com.endows.app.models.db;

import java.util.List;

public class CreditCardDetails {
    private String creditedAmt;
    private String debitedAmt;
    private String lastPaymentDate;
    private List<TransactionHistory> transactionHistory;

    public String getCreditedAmt() {
        return creditedAmt;
    }

    public void setCreditedAmt(String creditedAmt) {
        this.creditedAmt = creditedAmt;
    }

    public String getDebitedAmt() {
        return debitedAmt;
    }

    public void setDebitedAmt(String debitedAmt) {
        this.debitedAmt = debitedAmt;
    }

    public String getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(String lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }

    public List<TransactionHistory> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<TransactionHistory> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    @Override
    public String toString() {
        return "CreditCardDetails{" +
                "creditedAmt='" + creditedAmt + '\'' +
                ", debitedAmt='" + debitedAmt + '\'' +
                ", lastPaymentDate='" + lastPaymentDate + '\'' +
                ", transactionHistory=" + transactionHistory +
                '}';
    }
}
