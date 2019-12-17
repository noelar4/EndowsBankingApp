package com.endows.app.views.fragments.findaccount;

import android.app.Application;
import android.util.Patterns;

import com.endows.app.EndowsApplication;
import com.endows.app.callbacks.LoginCallback;
import com.endows.app.common.BooleanLiveData;
import com.endows.app.common.StringLiveData;
import com.endows.app.models.app.LoginResponse;
import com.endows.app.models.db.Customers;
import com.endows.app.serviceimpl.LoginServiceImpl;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class FindAccountViewModel extends AndroidViewModel implements LoginCallback {

    private StringLiveData messageLiveData;
    private BooleanLiveData validEmail;
    private MutableLiveData<Customers> customersMutableLiveData;

    private LoginServiceImpl loginService;

    public FindAccountViewModel(@NonNull Application application) {
        super(application);
        messageLiveData = new StringLiveData();
        validEmail = new BooleanLiveData();
        customersMutableLiveData = new MutableLiveData<>();

        loginService = new LoginServiceImpl();
    }

    StringLiveData getMessageLiveData() {
        return messageLiveData;
    }

    BooleanLiveData getValidEmail() {
        return validEmail;
    }

    MutableLiveData<Customers> getCustomersMutableLiveData() {
        return customersMutableLiveData;
    }


    void setEmail(String email) {
        if (email.length() == 0) {
            messageLiveData.setValue("EMAIL ID can not be empty");
            validEmail.setValue(false);
        }

        if (isNumeric(email)) {
            if (email.length() == 10) {
                loginService.generateSmsVerificationCode(this, email);
            } else {
                messageLiveData.setValue("Email a valid MOBILE NUMBER");
                validEmail.setValue(false);
            }
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                messageLiveData.setValue("Email a valid EMAIL ID");
                validEmail.setValue(false);
            } else {
                loginService.generateEmailVerificationCode(this, email, getApplication().getApplicationContext());
            }
        }
    }

    @Override
    public void onLoginCallback(LoginResponse response) {
        if (response.isSuccess()) {
            Customers customers = response.getCustomerObj();
            ((EndowsApplication) getApplication()).setCustomers(customers);
            customersMutableLiveData.setValue(customers);
        } else {
            messageLiveData.setValue("User details not available... Please contact the nearest bank!");
        }
    }

    static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
