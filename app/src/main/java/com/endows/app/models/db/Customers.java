package com.endows.app.models.db;

import java.util.List;

public class Customers {
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
}
