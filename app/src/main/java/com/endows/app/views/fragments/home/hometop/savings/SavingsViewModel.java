package com.endows.app.views.fragments.home.hometop.savings;

import android.app.Application;

import com.endows.app.models.db.AccountDetails;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class SavingsViewModel extends AndroidViewModel {

    private MutableLiveData<AccountDetails> accountLiveData;

    public SavingsViewModel(@NonNull Application application) {
        super(application);
        accountLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<AccountDetails> getAccountLiveData() {
        return accountLiveData;
    }

}
