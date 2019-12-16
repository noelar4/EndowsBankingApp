package com.endows.app.views.fragments.home.homebottom.details;

import android.app.Application;

import com.endows.app.models.db.Customers;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class DetailsViewModel extends AndroidViewModel {

    private MutableLiveData<Customers> customerLiveData;

    private MutableLiveData<Integer> accountType;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        customerLiveData = new MutableLiveData<>();
        accountType = new MutableLiveData<>();
    }

    MutableLiveData<Customers> getCustomerLiveData() {
        return customerLiveData;
    }

    MutableLiveData<Integer> getAccountType() {
        return accountType;
    }

}
