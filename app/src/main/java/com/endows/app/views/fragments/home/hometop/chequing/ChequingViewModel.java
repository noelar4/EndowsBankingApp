package com.endows.app.views.fragments.home.hometop.chequing;

import android.app.Application;

import com.endows.app.models.db.AccountDetails;
import com.endows.app.models.db.CardDetails;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class ChequingViewModel extends AndroidViewModel {

    private MutableLiveData<AccountDetails> accountLiveData;
    private MutableLiveData<CardDetails> cardLiveData;

    public ChequingViewModel(@NonNull Application application) {
        super(application);
        accountLiveData = new MutableLiveData<>();
        cardLiveData = new MutableLiveData<>();
    }

    MutableLiveData<AccountDetails> getAccountLiveData() {
        return accountLiveData;
    }

    MutableLiveData<CardDetails> getCardLiveData() {
        return cardLiveData;
    }

    String formatCardNo(String cardNo) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cardNo.length(); i++) {
            builder.append(cardNo.charAt(i));
            if (i != 0 && (i + 1) % 4 == 0) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }
}
