package com.endows.app.models.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.endows.app.common.PagetItem;

import java.util.List;

public class AccountDetails implements Parcelable, PagetItem {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accountType);
        dest.writeString(this.accountNumber);
        dest.writeString(this.accountBalance);
        dest.writeString(this.creditedAmt);
        dest.writeString(this.debitedAmt);
        dest.writeTypedList(this.transactionHistory);
    }

    public AccountDetails() {
    }

    protected AccountDetails(Parcel in) {
        this.accountType = in.readString();
        this.accountNumber = in.readString();
        this.accountBalance = in.readString();
        this.creditedAmt = in.readString();
        this.debitedAmt = in.readString();
        this.transactionHistory = in.createTypedArrayList(TransactionHistory.CREATOR);
    }

    public static final Parcelable.Creator<AccountDetails> CREATOR = new Parcelable.Creator<AccountDetails>() {
        @Override
        public AccountDetails createFromParcel(Parcel source) {
            return new AccountDetails(source);
        }

        @Override
        public AccountDetails[] newArray(int size) {
            return new AccountDetails[size];
        }
    };
}
