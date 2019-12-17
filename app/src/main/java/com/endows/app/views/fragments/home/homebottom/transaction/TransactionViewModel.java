package com.endows.app.views.fragments.home.homebottom.transaction;

import android.app.Application;

import com.endows.app.EndowsApplication;
import com.endows.app.constants.Constants;
import com.endows.app.models.db.AccountDetails;
import com.endows.app.models.db.Customers;
import com.endows.app.models.db.TransactionHistory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class TransactionViewModel extends AndroidViewModel {

    private MutableLiveData<List<TransactionHistory>> historyLiveData;

    private List<TransactionHistory> histories;

    private MutableLiveData<Integer> accountTypeLiveData;


    public TransactionViewModel(@NonNull Application application) {
        super(application);
        histories = new ArrayList<>();
        historyLiveData = new MutableLiveData<>();
        accountTypeLiveData = new MutableLiveData<>();
        historyLiveData.setValue(histories);
    }

    MutableLiveData<List<TransactionHistory>> getHistoryLiveData() {
        return historyLiveData;
    }

    MutableLiveData<Integer> getAccountTypeLiveData() {
        return accountTypeLiveData;
    }

    void getTransactionHistory() {
        Customers customers = ((EndowsApplication) getApplication()).getCustomers();
        if (customers != null) {
            int accountType = ((EndowsApplication) getApplication()).getAccountType();
            if (accountType == 0) {
                List<AccountDetails> accountDetails = customers.getAccountDetails();
                for (AccountDetails details:
                        accountDetails) {
                    if (details.getAccountType().equals(Constants.TransactionConstants.CHEQUING_ACCOUNT)) {
                        histories.clear();
                        histories.addAll(details.getTransactionHistory());
                        Collections.reverse(histories);
                        historyLiveData.setValue(histories);
                        break;
                    }
                }
            } else if (accountType == 1) {
                List<AccountDetails> accountDetails = customers.getAccountDetails();
                for (AccountDetails details:
                        accountDetails) {
                    if (details.getAccountType().equals(Constants.TransactionConstants.SAVINGS_ACCOUNT)) {
                        histories.clear();
                        histories.addAll(details.getTransactionHistory());
                        Collections.reverse(histories);
                        historyLiveData.setValue(histories);
                        break;
                    }
                }
            } else {
                histories.clear();
                if (customers.getCreditCardDetails() != null && customers.getCreditCardDetails().getTransactionHistory() != null) {
                    histories.addAll(customers.getCreditCardDetails().getTransactionHistory());
                    Collections.reverse(histories);
                    historyLiveData.setValue(histories);
                }
            }
        }
    }

}
