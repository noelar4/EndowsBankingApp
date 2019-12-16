package com.endows.app.views.fragments.transfer;

import android.app.Application;

import com.endows.app.callbacks.TransactionCallback;
import com.endows.app.common.StringLiveData;
import com.endows.app.models.app.TransactionResponse;
import com.endows.app.serviceimpl.TransactionServiceImpl;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class TransferViewModel extends AndroidViewModel implements TransactionCallback {

    private StringLiveData messageLiveData;
    private TransactionServiceImpl mTransactionServiceImp;

    public TransferViewModel(@NonNull Application application) {
        super(application);
        messageLiveData = new StringLiveData();
        mTransactionServiceImp = new TransactionServiceImpl();
    }

    public StringLiveData getMessageLiveData() {
        return messageLiveData;
    }

    void transferAmount(String accountFrom, String toAccount, String amount) {
        mTransactionServiceImp.transferBetweenAccounts(getApplication().getApplicationContext(), this , "1", "1", "2", "100");
    }

    @Override
    public void onTransactionCallback(TransactionResponse response) {
        if (response.isSuccess()) {
            messageLiveData.setValue("Transferred Successful");
        }
    }
}
