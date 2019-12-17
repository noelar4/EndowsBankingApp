package com.endows.app.views.fragments.confirm;

import android.app.Application;

import com.endows.app.EndowsApplication;
import com.endows.app.common.StringLiveData;
import com.endows.app.models.db.Customers;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ConfirmAccountViewModel extends AndroidViewModel {

    private StringLiveData usernameLiveData;
    private StringLiveData smsLiveData;
    private StringLiveData emailLiveData;


    public ConfirmAccountViewModel(@NonNull Application application) {
        super(application);
        usernameLiveData = new StringLiveData();
        smsLiveData = new StringLiveData();
        emailLiveData = new StringLiveData();
    }

    void setCustomer() {
        Customers customers = ((EndowsApplication) getApplication()).getCustomers();
        usernameLiveData.setValue(customers.getFirstName() + " " + customers.getLastName());
        smsLiveData.setValue(customers.getPhoneNumber());
        emailLiveData.setValue(customers.getEmailId());
    }

    StringLiveData getUsernameLiveData() {
        return usernameLiveData;
    }

    StringLiveData getSmsLiveData() {
        return smsLiveData;
    }

    StringLiveData getEmailLiveData() {
        return emailLiveData;
    }
}
