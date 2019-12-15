package com.endows.app.views.fragments.login;

import android.app.Application;

import com.endows.app.callbacks.LoginCallback;
import com.endows.app.common.MessageLiveData;
import com.endows.app.models.app.LoginResponse;
import com.endows.app.serviceimpl.LoginServiceImpl;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LoginViewModel extends AndroidViewModel implements LoginCallback {

    private MessageLiveData errorIndicator;

    private LoginServiceImpl mLoginServiceImpl;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        errorIndicator = new MessageLiveData();

        mLoginServiceImpl = new LoginServiceImpl();
    }

    MessageLiveData getErrorIndicator() {
        return errorIndicator;
    }

    void loginUser(String cardNo, String password) {
        if (validate(cardNo, password)) {
            mLoginServiceImpl.validateUserSignIn(this, cardNo, password);
        }
    }

    private boolean validate(String cardNo, String password) {
        if (cardNo == null || cardNo.length() == 0) {
            errorIndicator.setValue("Card no can not be empty");
            return false;
        } else if (cardNo.length() < 16) {
            errorIndicator.setValue("Enter a valid card no");
            return false;
        } else if (password == null || password.length() == 0) {
            errorIndicator.setValue("Password can not be empty");
            return false;
        } else if (password.length() < 5) {
            errorIndicator.setValue("Enter a valid password");
            return false;
        }

        return true;
    }

    @Override
    public void onLoginCallback(LoginResponse response) {
        if (response.isSuccess()) {
            errorIndicator.setValue("Login Successful");
        } else {
            errorIndicator.setValue("Login failed");
        }
    }
}
