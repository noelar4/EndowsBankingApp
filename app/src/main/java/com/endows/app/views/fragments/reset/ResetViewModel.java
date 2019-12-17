package com.endows.app.views.fragments.reset;

import android.app.Application;

import com.endows.app.EndowsApplication;
import com.endows.app.callbacks.LoginCallback;
import com.endows.app.common.BooleanLiveData;
import com.endows.app.common.StringLiveData;
import com.endows.app.models.app.LoginResponse;
import com.endows.app.models.db.Customers;
import com.endows.app.serviceimpl.LoginServiceImpl;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ResetViewModel extends AndroidViewModel {

    private StringLiveData messageLiveData;
    private BooleanLiveData resetStatusLiveData;

    private LoginServiceImpl loginService;

    public ResetViewModel(@NonNull Application application) {
        super(application);
        messageLiveData = new StringLiveData();
        resetStatusLiveData = new BooleanLiveData();

        loginService = new LoginServiceImpl();
    }

    StringLiveData getMessageLiveData() {
        return messageLiveData;
    }

    BooleanLiveData getResetStatusLiveData() {
        return resetStatusLiveData;
    }

    void checkPassword(String password, String cPassword) {
        if (password.length() == 0) {
            messageLiveData.setValue("PASSWORD field can't be empty");
        } else if (cPassword.length() == 0) {
            messageLiveData.setValue("CONFIRM PASSWORD field can't be empty");
        } else if (!password.equals(cPassword)) {
            messageLiveData.setValue("PASSWORD mismatch");
        } else {
            Customers customers = ((EndowsApplication) getApplication()).getCustomers();
            if (customers == null) {
                messageLiveData.setValue("User doesn't exist, Please try again...");
                return;
            }

           LoginResponse response = loginService.saveNewPassword(customers.getCustomerId(), password);
            if (response.isSuccess()) {
                customers = response.getCustomerObj();
                ((EndowsApplication) getApplication()).setCustomers(customers);
                resetStatusLiveData.setValue(true);
            } else {
                messageLiveData.setValue("Something went wrong, Please try again...");
            }
        }
    }
}
