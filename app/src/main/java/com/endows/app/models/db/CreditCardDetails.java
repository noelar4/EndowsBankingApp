package com.endows.app.models.db;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class CreditCardDetails implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.creditedAmt);
        dest.writeString(this.debitedAmt);
        dest.writeString(this.lastPaymentDate);
        dest.writeTypedList(this.transactionHistory);
    }

    public CreditCardDetails() {
    }

    protected CreditCardDetails(Parcel in) {
        this.creditedAmt = in.readString();
        this.debitedAmt = in.readString();
        this.lastPaymentDate = in.readString();
        this.transactionHistory = in.createTypedArrayList(TransactionHistory.CREATOR);
    }

    public static final Parcelable.Creator<CreditCardDetails> CREATOR = new Parcelable.Creator<CreditCardDetails>() {
        @Override
        public CreditCardDetails createFromParcel(Parcel source) {
            return new CreditCardDetails(source);
        }

        @Override
        public CreditCardDetails[] newArray(int size) {
            return new CreditCardDetails[size];
        }
    };
}
