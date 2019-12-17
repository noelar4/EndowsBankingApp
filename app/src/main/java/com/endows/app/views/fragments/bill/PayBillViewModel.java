package com.endows.app.views.fragments.bill;

import android.app.Application;

import com.endows.app.EndowsApplication;
import com.endows.app.callbacks.TransactionCallback;
import com.endows.app.common.BooleanLiveData;
import com.endows.app.common.StringLiveData;
import com.endows.app.constants.Constants;
import com.endows.app.helper.PreferenceManager;
import com.endows.app.models.app.TransactionResponse;
import com.endows.app.models.db.AccountDetails;
import com.endows.app.models.db.CardDetails;
import com.endows.app.models.db.Customers;
import com.endows.app.serviceimpl.TransactionServiceImpl;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class PayBillViewModel extends AndroidViewModel implements TransactionCallback {

    private StringLiveData messageLiveData;
    private StringLiveData checkingLiveData;
    private StringLiveData creditCardLiveData;
    private BooleanLiveData payStatusLiveData;

    private TransactionServiceImpl mTransactionServiceImpl;

    public PayBillViewModel(@NonNull Application application) {
        super(application);

        messageLiveData = new StringLiveData();
        checkingLiveData = new StringLiveData();
        creditCardLiveData = new StringLiveData();
        payStatusLiveData = new BooleanLiveData();

        mTransactionServiceImpl = new TransactionServiceImpl();

        setAccounts();
    }

    StringLiveData getMessageLiveData() {
        return messageLiveData;
    }

    StringLiveData getCheckingLiveData() {
        return checkingLiveData;
    }

    StringLiveData getCreditCardLiveData() {
        return creditCardLiveData;
    }

    BooleanLiveData getPayStatusLiveData() {
        return payStatusLiveData;
    }

    private void setAccounts() {
        Customers customers = ((EndowsApplication) getApplication()).getCustomers();
        if (customers == null) return;

        StringBuilder chequingBuilder = new StringBuilder("Chequing (");
        StringBuilder creditCardBuilder = new StringBuilder("Credit Card (");

        List<AccountDetails> accountDetails = customers.getAccountDetails();
        if (accountDetails != null) {
            for (AccountDetails account :
                    accountDetails) {
                int length = account.getAccountNumber().length();
                String lastFourNo = (account.getAccountNumber().subSequence(length - 4, length)).toString();
                if (account.getAccountType().equals(Constants.TransactionConstants.CHEQUING_ACCOUNT)) {
                    chequingBuilder.append(lastFourNo);
                    chequingBuilder.append(") -- ");
                    String balance = String.format(Locale.getDefault(), Constants.Templates.MONEY_TEMPLATE, account.getAccountBalance());
                    chequingBuilder.append(balance);
                    checkingLiveData.setValue(chequingBuilder.toString());
                }
            }

            List<CardDetails> details = customers.getCardDetails();
            for (CardDetails card :
                    details) {
                if (card.getCardType() == 1) {
                    int length = card.getCardNumber().length();
                    String lastFourNo = (card.getCardNumber().subSequence(length - 4, length)).toString();
                    creditCardBuilder.append(lastFourNo);
                    creditCardBuilder.append(")");
                    creditCardLiveData.setValue(creditCardBuilder.toString());
                    break;
                }
            }
        }
    }

    public void doBillPayment(Object isCreditCard, String type, String billNo, boolean isRemember, String amount) {

        if (isCreditCard == null) {
            messageLiveData.setValue("Please select ACCOUNT");
            return;
        }

        if (type == null) {
            messageLiveData.setValue("Please select UTILITY TYPE");
            return;
        }


        if (billNo.length() == 0) {
            messageLiveData.setValue("BILL NUMBER can not be empty");
            return;
        }

        if (amount.length() == 0) {
            messageLiveData.setValue("AMOUNT can not be empty");
            return;
        }

        if (isRemember) {
            saveType(type, billNo);
        }

        Customers customers = ((EndowsApplication) getApplication()).getCustomers();
        if (customers == null) {
            messageLiveData.setValue("Session expired, Please logout and then login again");
            return;
        }

        mTransactionServiceImpl.payUtilityBills(getApplication().getApplicationContext(),
                this, customers.getCustomerId(), amount, (Boolean) isCreditCard);
    }

    @Override
    public void onTransactionCallback(TransactionResponse response) {
        if (response.isSuccess()) {
            payStatusLiveData.setValue(true);
        } else {
            payStatusLiveData.setValue(false);
        }
    }

    private void saveType(String type, String billNo) {
        PreferenceManager manager = new PreferenceManager(getApplication().getApplicationContext());
        manager.setPreference(type, billNo);
    }
}
