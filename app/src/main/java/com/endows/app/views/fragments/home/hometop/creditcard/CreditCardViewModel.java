package com.endows.app.views.fragments.home.hometop.creditcard;

import android.app.Application;

import com.endows.app.models.db.CardDetails;
import com.endows.app.models.db.CreditCardDetails;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class CreditCardViewModel extends AndroidViewModel {

    private MutableLiveData<CardDetails> cardDetailLiveData;
    private MutableLiveData<CreditCardDetails> creditCardLiveData;

    public CreditCardViewModel(@NonNull Application application) {
        super(application);
        cardDetailLiveData = new MutableLiveData<>();
        creditCardLiveData = new MutableLiveData<>();
    }

    MutableLiveData<CardDetails> getCardDetailLiveData() {
        return cardDetailLiveData;
    }

    MutableLiveData<CreditCardDetails> getCreditCardLiveData() {
        return creditCardLiveData;
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
