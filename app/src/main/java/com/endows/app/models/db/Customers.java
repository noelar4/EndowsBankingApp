package com.endows.app.models.db;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Customers implements Parcelable {
    private String customerId;
    private String firstName;
    private String lastName;
    private String address;
    private String dob;
    private String sinNumber;
    private String phoneNumber;
    private String credited_amount;
    private String debited_amount;
    private String emailId;
    private CreditCardDetails creditCardDetails;
    private List<AccountDetails> accountDetails;
    private List<CardDetails> cardDetails;
    private List<BeneficiaryDetail> beneficiaryDetails;
    private String mobileAppPassword;
    private String verificationCode;
    private String isFirstTimeLogin;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSinNumber() {
        return sinNumber;
    }

    public void setSinNumber(String sinNumber) {
        this.sinNumber = sinNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public List<CardDetails> getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(List<CardDetails> cardDetails) {
        this.cardDetails = cardDetails;
    }

    public List<BeneficiaryDetail> getBeneficiaryDetails() {
        return beneficiaryDetails;
    }

    public void setBeneficiaryDetails(List<BeneficiaryDetail> beneficiaryDetails) {
        this.beneficiaryDetails = beneficiaryDetails;
    }

    public String getMobileAppPassword() {
        return mobileAppPassword;
    }

    public void setMobileAppPassword(String mobileAppPassword) {
        this.mobileAppPassword = mobileAppPassword;
    }

    public String getIsFirstTimeLogin() {
        return isFirstTimeLogin;
    }

    public void setIsFirstTimeLogin(String isFirstTimeLogin) {
        this.isFirstTimeLogin = isFirstTimeLogin;
    }

    public String getCredited_amount() {
        return credited_amount;
    }

    public void setCredited_amount(String credited_amount) {
        this.credited_amount = credited_amount;
    }

    public String getDebited_amount() {
        return debited_amount;
    }

    public void setDebited_amount(String debited_amount) {
        this.debited_amount = debited_amount;
    }

    public List<AccountDetails> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(List<AccountDetails> accountDetails) {
        this.accountDetails = accountDetails;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public CreditCardDetails getCreditCardDetails() {
        return creditCardDetails;
    }

    public void setCreditCardDetails(CreditCardDetails creditCardDetails) {
        this.creditCardDetails = creditCardDetails;
    }

    @Override
    public String toString() {
        return "Customers{" +
                "customerId='" + customerId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", dob='" + dob + '\'' +
                ", sinNumber='" + sinNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", credited_amount='" + credited_amount + '\'' +
                ", debited_amount='" + debited_amount + '\'' +
                ", emailId='" + emailId + '\'' +
                ", creditCardDetails=" + creditCardDetails +
                ", accountDetails=" + accountDetails +
                ", cardDetails=" + cardDetails +
                ", beneficiaryDetails=" + beneficiaryDetails +
                ", mobileAppPassword='" + mobileAppPassword + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", isFirstTimeLogin='" + isFirstTimeLogin + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.customerId);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.address);
        dest.writeString(this.dob);
        dest.writeString(this.sinNumber);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.credited_amount);
        dest.writeString(this.debited_amount);
        dest.writeString(this.emailId);
        dest.writeParcelable(this.creditCardDetails, flags);
        dest.writeTypedList(this.accountDetails);
        dest.writeTypedList(this.cardDetails);
        dest.writeTypedList(this.beneficiaryDetails);
        dest.writeString(this.mobileAppPassword);
        dest.writeString(this.verificationCode);
        dest.writeString(this.isFirstTimeLogin);
    }

    public Customers() {
    }

    protected Customers(Parcel in) {
        this.customerId = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.address = in.readString();
        this.dob = in.readString();
        this.sinNumber = in.readString();
        this.phoneNumber = in.readString();
        this.credited_amount = in.readString();
        this.debited_amount = in.readString();
        this.emailId = in.readString();
        this.creditCardDetails = in.readParcelable(CreditCardDetails.class.getClassLoader());
        this.accountDetails = in.createTypedArrayList(AccountDetails.CREATOR);
        this.cardDetails = in.createTypedArrayList(CardDetails.CREATOR);
        this.beneficiaryDetails = in.createTypedArrayList(BeneficiaryDetail.CREATOR);
        this.mobileAppPassword = in.readString();
        this.verificationCode = in.readString();
        this.isFirstTimeLogin = in.readString();
    }

    public static final Parcelable.Creator<Customers> CREATOR = new Parcelable.Creator<Customers>() {
        @Override
        public Customers createFromParcel(Parcel source) {
            return new Customers(source);
        }

        @Override
        public Customers[] newArray(int size) {
            return new Customers[size];
        }
    };
}
