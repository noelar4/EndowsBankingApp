package com.endows.app.models.app;

import android.os.Parcel;
import android.os.Parcelable;

import com.endows.app.common.PagetItem;
import com.endows.app.models.db.AccountDetails;
import com.endows.app.models.db.CardDetails;
import com.endows.app.models.db.CreditCardDetails;

public class AccountCardDetails implements PagetItem, Parcelable {

    private AccountDetails accountDetails;
    private CardDetails cardDetails;
    private CreditCardDetails creditCardDetails;

    public AccountDetails getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(AccountDetails accountDetails) {
        this.accountDetails = accountDetails;
    }

    public CardDetails getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(CardDetails cardDetails) {
        this.cardDetails = cardDetails;
    }

    public CreditCardDetails getCreditCardDetails() {
        return creditCardDetails;
    }

    public void setCreditCardDetails(CreditCardDetails creditCardDetails) {
        this.creditCardDetails = creditCardDetails;
    }

    public AccountCardDetails() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.accountDetails, flags);
        dest.writeParcelable(this.cardDetails, flags);
        dest.writeParcelable(this.creditCardDetails, flags);
    }

    protected AccountCardDetails(Parcel in) {
        this.accountDetails = in.readParcelable(AccountDetails.class.getClassLoader());
        this.cardDetails = in.readParcelable(CardDetails.class.getClassLoader());
        this.creditCardDetails = in.readParcelable(CreditCardDetails.class.getClassLoader());
    }

    public static final Creator<AccountCardDetails> CREATOR = new Creator<AccountCardDetails>() {
        @Override
        public AccountCardDetails createFromParcel(Parcel source) {
            return new AccountCardDetails(source);
        }

        @Override
        public AccountCardDetails[] newArray(int size) {
            return new AccountCardDetails[size];
        }
    };
}
