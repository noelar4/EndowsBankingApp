package com.endows.app.views.fragments.interac;

import android.app.Application;

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

public class InteracViewModel extends AndroidViewModel implements TransactionCallback {

    private static final String TAG = "InteracViewModel";

    private StringLiveData messageLiveData;
    private StringLiveData chequingLiveData;
    private StringLiveData savingsLiveData;
    private BooleanLiveData interacLiveData;

    private TransactionServiceImpl mTransactionServiceImp;

    public InteracViewModel(@NonNull Application application) {
        super(application);

        messageLiveData = new StringLiveData();
        chequingLiveData = new StringLiveData();
        savingsLiveData = new StringLiveData();
        interacLiveData = new BooleanLiveData();

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

    BooleanLiveData getInteracLiveData() {
        return interacLiveData;
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
        }
    }

    public void doInterac(String receiverEmailId, String amount) {
        Customers customers = ((EndowsApplication) getApplication()).getCustomers();
        mTransactionServiceImp.interacMoneyTransfer(getApplication().getApplicationContext(),
                this, String.valueOf(customers.getCustomerId()),
                receiverEmailId, amount);
    }

    @Override
    public void onTransactionCallback(TransactionResponse response) {
        if (response.isSuccess()) {
            ((EndowsApplication) getApplication()).setCustomers(response.getCustomerObj());
            interacLiveData.setValue(true);
        } else {
            interacLiveData.setValue(false);
        }
    }
}
