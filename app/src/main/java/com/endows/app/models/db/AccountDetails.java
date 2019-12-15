package com.endows.app.models.db;

import java.util.List;

public class AccountDetails {
    private String accountType;
    private String accountNumber;
    private String accountBalance;
    private String creditedAmt;
    private String debitedAmt;
    private List<TransactionHistory> transactionHistory;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public List<TransactionHistory> getTransactionHistory() {
        return transactionHistory;
    }

    public void setTransactionHistory(List<TransactionHistory> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

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

    @Override
    public String toString() {
        return "AccountDetails{" +
                "accountType='" + accountType + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountBalance='" + accountBalance + '\'' +
                ", creditedAmt='" + creditedAmt + '\'' +
                ", debitedAmt='" + debitedAmt + '\'' +
                ", transactionHistory=" + transactionHistory +
                '}';
    }
}
