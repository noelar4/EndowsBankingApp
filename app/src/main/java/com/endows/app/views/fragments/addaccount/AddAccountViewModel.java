package com.endows.app.views.fragments.addaccount;

import android.app.Application;

import com.endows.app.EndowsApplication;
import com.endows.app.callbacks.TransactionCallback;
import com.endows.app.common.BooleanLiveData;
import com.endows.app.common.StringLiveData;
import com.endows.app.models.app.TransactionResponse;
import com.endows.app.models.db.BeneficiaryDetail;
import com.endows.app.models.db.Customers;
import com.endows.app.serviceimpl.TransactionServiceImpl;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class AddAccountViewModel extends AndroidViewModel implements TransactionCallback {

    private StringLiveData messageLiveData;
    private BooleanLiveData addStatusLiveData;
    private MutableLiveData<BeneficiaryDetail> newBenefiatiaryDetails;

    private TransactionServiceImpl mTransactionServiceImpl;

    public AddAccountViewModel(@NonNull Application application) {
        super(application);

        messageLiveData = new StringLiveData();
        addStatusLiveData = new BooleanLiveData();
        newBenefiatiaryDetails = new MutableLiveData<>();

        mTransactionServiceImpl = new TransactionServiceImpl();
    }

    StringLiveData getMessageLiveData() {
        return messageLiveData;
    }

    BooleanLiveData getAddStatusLiveData() {
        return addStatusLiveData;
    }

    MutableLiveData<BeneficiaryDetail> getNewBenefiatiaryDetails() {
        return newBenefiatiaryDetails;
    }


    void doAddContact(BeneficiaryDetail detail) {
        if (detail.getBeneficiaryName().length() == 0) {
            messageLiveData.setValue("Payee NAME can not be empty");
            return;
        }

        if (detail.getBeneficiaryEmailId().length() == 0) {
            messageLiveData.setValue("Payee EMAIL ID can not be empty");
            return;
        }


        Customers customers = ((EndowsApplication) getApplication()).getCustomers();
        if (customers != null) {
            String custId = customers.getCustomerId();
            newBenefiatiaryDetails.setValue(detail);
            mTransactionServiceImpl.addBeneficiary(getApplication().getApplicationContext(),
                    this, custId, detail.getBeneficiaryName(), detail.getBeneficiaryEmailId());
        } else {
            messageLiveData.setValue("Session expired, please logout and then login again");
        }
    }

    @Override
    public void onTransactionCallback(TransactionResponse response) {
        if (response.isSuccess()) {
            addStatusLiveData.setValue(true);
        } else {
            addStatusLiveData.setValue(false);
        }
    }
}
