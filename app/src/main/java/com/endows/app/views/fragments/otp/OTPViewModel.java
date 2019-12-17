package com.endows.app.views.fragments.otp;

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

public class OTPViewModel extends AndroidViewModel {

    private StringLiveData messageLiveData;
    private BooleanLiveData verificationLiveData;


    public OTPViewModel(@NonNull Application application) {
        super(application);
        messageLiveData = new StringLiveData();
        verificationLiveData = new BooleanLiveData();
    }

    StringLiveData getMessageLiveData() {
        return messageLiveData;
    }

    BooleanLiveData getVerificationLiveData() {
        return verificationLiveData;
    }

    void validateCode(String code) {
        if (code.length() != 6) {
            messageLiveData.setValue("Enter the code");
            return;
        }

        Customers customers = ((EndowsApplication) getApplication()).getCustomers();
        if (customers == null) {
            messageLiveData.setValue("User does not exist, Please do it again");
            return;
        }

        if (customers.getVerificationCode().equals(code)) {
            verificationLiveData.setValue(true);
        } else {
            messageLiveData.setValue("Entered CODE is wrong");
        }
    }
}
