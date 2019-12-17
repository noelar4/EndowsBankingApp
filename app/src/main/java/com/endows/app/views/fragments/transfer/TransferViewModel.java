package com.endows.app.views.fragments.transfer;

import android.app.Application;
import android.util.Log;

import com.endows.app.EndowsApplication;
import com.endows.app.callbacks.TransactionCallback;
import com.endows.app.common.BooleanLiveData;
import com.endows.app.common.StringLiveData;
import com.endows.app.constants.Constants;
import com.endows.app.models.app.TransactionResponse;
import com.endows.app.models.db.AccountDetails;
import com.endows.app.models.db.CardDetails;
import com.endows.app.models.db.Customers;
import com.endows.app.serviceimpl.TransactionServiceImpl;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class TransferViewModel extends AndroidViewModel implements TransactionCallback {

    private static final String TAG = "TransferViewModel";

    private StringLiveData messageLiveData;
    private StringLiveData chequingLiveData;
    private StringLiveData savingsLiveData;
    private StringLiveData creditCardLiveData;
    private BooleanLiveData transferLiveData;
    private TransactionServiceImpl mTransactionServiceImp;

    public TransferViewModel(@NonNull Application application) {
        super(application);
        messageLiveData = new StringLiveData();
        chequingLiveData = new StringLiveData();
        savingsLiveData = new StringLiveData();
        creditCardLiveData = new StringLiveData();
        transferLiveData = new BooleanLiveData();

        mTransactionServiceImp = new TransactionServiceImpl();
        setAccounts();
    }

    StringLiveData getMessageLiveData() {
        return messageLiveData;
    }

    StringLiveData getChequingLiveData() {
        return chequingLiveData;
    }

    StringLiveData getSavingsLiveData() {
        return savingsLiveData;
    }

    StringLiveData getCreditCardLiveData() {
        return creditCardLiveData;
    }

    BooleanLiveData getTransferLiveData() {
        return transferLiveData;
    }

    void transferAmount(Object fromAccount, Object toAccount, String amount) {

        if (fromAccount == null) {
            messageLiveData.setValue("Please select FROM account to do transfer");
            return;
        } else if (toAccount == null) {
            messageLiveData.setValue("Please select TO account to do transfer");
            return;
        }

        int from = (int) fromAccount;
        int to = (int) toAccount;
        Customers customers = ((EndowsApplication) getApplication()).getCustomers();
        if (customers == null) {
            messageLiveData.setValue("Session expired, Please logout and then login again for transaction");
            return;
        }

        String customerId = customers.getCustomerId();
        mTransactionServiceImp.transferBetweenAccounts(getApplication().getApplicationContext(), this, customerId, String.valueOf(from), String.valueOf(to), amount);
    }

    @Override
    public void onTransactionCallback(TransactionResponse response) {
        if (response.isSuccess()) {
            Log.d(TAG, "-------- onTransactionCallback SUCCESS ----------");
            ((EndowsApplication) getApplication()).setCustomers(response.getCustomerObj());
            transferLiveData.setValue(true);
        } else {
            Log.d(TAG, "-------- onTransactionCallback FAILED ----------");
            transferLiveData.setValue(false);
        }
    }

    private void setAccounts() {
        Customers customers = ((EndowsApplication) getApplication()).getCustomers();
        if (customers == null) return;

        StringBuilder chequingBuilder = new StringBuilder("Chequing (");
        StringBuilder savingsBuilder = new StringBuilder("Savings (");
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
                    chequingLiveData.setValue(chequingBuilder.toString());
                } else {
                    savingsBuilder.append(lastFourNo);
                    savingsBuilder.append(") -- ");
                    String balance = String.format(Locale.getDefault(), Constants.Templates.MONEY_TEMPLATE, account.getAccountBalance());
                    savingsBuilder.append(balance);
                    savingsLiveData.setValue(savingsBuilder.toString());
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
}
