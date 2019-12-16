package com.endows.app.models.db;

import android.os.Parcel;
import android.os.Parcelable;

public class TransactionHistory implements Parcelable {
    private String isDebit;
    private String isCredit;
    private String from;
    private String to;
    private String amount;
    private String timestamp;

    public String getIsDebit() {
        return isDebit;
    }

    public void setIsDebit(String isDebit) {
        this.isDebit = isDebit;
    }

    public String getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(String isCredit) {
        this.isCredit = isCredit;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "isDebit='" + isDebit + '\'' +
                ", isCredit='" + isCredit + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", amount='" + amount + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.isDebit);
        dest.writeString(this.isCredit);
        dest.writeString(this.from);
        dest.writeString(this.to);
        dest.writeString(this.amount);
        dest.writeString(this.timestamp);
    }

    public TransactionHistory() {
    }

    protected TransactionHistory(Parcel in) {
        this.isDebit = in.readString();
        this.isCredit = in.readString();
        this.from = in.readString();
        this.to = in.readString();
        this.amount = in.readString();
        this.timestamp = in.readString();
    }

    public static final Parcelable.Creator<TransactionHistory> CREATOR = new Parcelable.Creator<TransactionHistory>() {
        @Override
        public TransactionHistory createFromParcel(Parcel source) {
            return new TransactionHistory(source);
        }

        @Override
        public TransactionHistory[] newArray(int size) {
            return new TransactionHistory[size];
        }
    };
}
