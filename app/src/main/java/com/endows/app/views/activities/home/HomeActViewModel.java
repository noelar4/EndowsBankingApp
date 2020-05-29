package com.endows.app.views.activities.home;

import android.app.Application;

import com.endows.app.EndowsApplication;
import com.endows.app.common.StringLiveData;
import com.endows.app.models.db.Customers;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class HomeActViewModel extends AndroidViewModel {

    private StringLiveData messageLiveData;

    public HomeActViewModel(@NonNull Application application) {
        super(application);

        messageLiveData = new StringLiveData();

        Customers customers = ((EndowsApplication) getApplication()).getCustomers();
        messageLiveData.setValue(customers.getFirstName() + " " + customers.getLastName());
    }

    StringLiveData getMessageLiveData() {
        return messageLiveData;
    }
}
