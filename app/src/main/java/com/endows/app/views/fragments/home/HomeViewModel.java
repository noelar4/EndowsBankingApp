package com.endows.app.views.fragments.home;

import android.app.Application;

import com.endows.app.EndowsApplication;
import com.endows.app.common.PagetItem;
import com.endows.app.constants.Constants;
import com.endows.app.models.app.AccountCardDetails;
import com.endows.app.models.db.AccountDetails;
import com.endows.app.models.db.CardDetails;
import com.endows.app.models.db.Customers;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<Customers> customerMutableLiveData;

    private MutableLiveData<List<PagetItem>> accountsLiveData;

    private MutableLiveData<Integer> accountTypeLiveData;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        customerMutableLiveData = new MutableLiveData<>();
        accountsLiveData = new MutableLiveData<>();
        accountTypeLiveData = new MutableLiveData<>();
    }


    MutableLiveData<Customers> getCustomerMutableLiveData() {
        return customerMutableLiveData;
    }

    MutableLiveData<List<PagetItem>> getAccountsLiveData() {
        return accountsLiveData;
    }

    MutableLiveData<Integer> getAccountTypeLiveData() {
        return accountTypeLiveData;
    }

    void setCustomerDetails() {
        Customers customerDetails = ((EndowsApplication) getApplication()).getCustomers();
        customerMutableLiveData.setValue(customerDetails);
        List<PagetItem> pagetItems = new ArrayList<>();
        List<AccountDetails> accountDetails = customerDetails.getAccountDetails();
        for (AccountDetails details:
             accountDetails) {
            if (details.getAccountType().equals(Constants.TransactionConstants.SAVINGS_ACCOUNT)) {
                pagetItems.add(details);
            } else {
                AccountCardDetails cardDetails = new AccountCardDetails();
                cardDetails.setAccountDetails(details);
                for (CardDetails card:
                     customerDetails.getCardDetails()) {
                    if (card.getCardType() == 2) {
                        cardDetails.setCardDetails(card);
                    }
                }

                pagetItems.add(cardDetails);
            }
        }

        AccountCardDetails creditCard = new AccountCardDetails();
        for (CardDetails card:
                customerDetails.getCardDetails()) {
            if (card.getCardType() == 1) {
                creditCard.setCardDetails(card);
            }
        }

        creditCard.setCreditCardDetails(customerDetails.getCreditCardDetails());
        pagetItems.add(creditCard);

        accountsLiveData.setValue(pagetItems);
    }


    void setAccountType(int position) {
        ((EndowsApplication) getApplication()).setAccountType(position);
    }
}