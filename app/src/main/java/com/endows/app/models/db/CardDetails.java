package com.endows.app.models.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.endows.app.common.PagetItem;

public class CardDetails implements Parcelable, PagetItem {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cardNumber);
        dest.writeValue(this.cardType);
        dest.writeString(this.expiryDate);
        dest.writeString(this.cvv);
        dest.writeString(this.pinNumber);
    }

    public CardDetails() {
    }

    protected CardDetails(Parcel in) {
        this.cardNumber = in.readString();
        this.cardType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.expiryDate = in.readString();
        this.cvv = in.readString();
        this.pinNumber = in.readString();
    }

    public static final Parcelable.Creator<CardDetails> CREATOR = new Parcelable.Creator<CardDetails>() {
        @Override
        public CardDetails createFromParcel(Parcel source) {
            return new CardDetails(source);
        }

        @Override
        public CardDetails[] newArray(int size) {
            return new CardDetails[size];
        }
    };
}
