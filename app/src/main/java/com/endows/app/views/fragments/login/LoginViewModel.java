package com.endows.app.views.fragments.login;

import android.app.Application;

import com.endows.app.EndowsApplication;
import com.endows.app.callbacks.LoginCallback;
import com.endows.app.common.BooleanLiveData;
import com.endows.app.common.StringLiveData;
import com.endows.app.helper.PreferenceManager;
import com.endows.app.models.app.LoginResponse;
import com.endows.app.models.db.CardDetails;
import com.endows.app.models.db.Customers;
import com.endows.app.serviceimpl.LoginServiceImpl;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class LoginViewModel extends AndroidViewModel implements LoginCallback {

    private StringLiveData errorIndicator;
    private BooleanLiveData loginIndicator;
    private StringLiveData cardNoIndicator;

    private LoginServiceImpl mLoginServiceImpl;

    private boolean isCardSaveNeeded = false;

    private Customers mCustomerDetails;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        errorIndicator = new StringLiveData();
        loginIndicator = new BooleanLiveData();
        cardNoIndicator = new StringLiveData();

        getCardNoFromPreference();

        mLoginServiceImpl = new LoginServiceImpl();
    }

    StringLiveData getErrorIndicator() {
        return errorIndicator;
    }

    BooleanLiveData getLoginStatus() {
        return loginIndicator;
    }

    StringLiveData getCardNo() {
        return cardNoIndicator;
    }

    void loginUser(String cardNo, String password, boolean isCardSaveNeeded) {
        if (validate(cardNo, password)) {
            this.isCardSaveNeeded = isCardSaveNeeded;
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
            mCustomerDetails = response.getCustomerObj();
            saveCardDetails();
            EndowsApplication application = getApplication();
            application.setCustomers(mCustomerDetails);
            loginIndicator.setValue(true);
        } else {
            errorIndicator.setValue("Login failed");
        }
    }


    private void saveCardDetails() {
        if (isCardSaveNeeded) {
            if (mCustomerDetails != null) {
                List<CardDetails> cardDetails = mCustomerDetails.getCardDetails();
                if (cardDetails != null) {
                    for (CardDetails detail : cardDetails) {
                        if (detail.getCardType() == 2) {
                            saveCardNumber(detail.getCardNumber());
                        }
                    }
                }
            }
        }
    }


    private void saveCardNumber(String cardNo) {
        PreferenceManager manager = new PreferenceManager(getApplication().getApplicationContext());
        manager.setPreference(PreferenceManager.PreferenceKeys.PREF_USER_CARD_NO, cardNo);
    }


    private void getCardNoFromPreference() {
        PreferenceManager manager = new PreferenceManager(getApplication().getApplicationContext());
        cardNoIndicator.setValue(manager.getStringPreference(PreferenceManager.PreferenceKeys.PREF_USER_CARD_NO));
    }

    Customers getCustomerDetails() {
        return mCustomerDetails;
    }

}
