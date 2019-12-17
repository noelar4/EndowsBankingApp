package com.endows.app.views.fragments.to;

import android.app.Application;

import com.endows.app.EndowsApplication;
import com.endows.app.models.db.BeneficiaryDetail;
import com.endows.app.models.db.Customers;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class ToViewModel extends AndroidViewModel {

    private MutableLiveData<List<BeneficiaryDetail>> beneficiaryLiveData;

    public ToViewModel(@NonNull Application application) {
        super(application);

        beneficiaryLiveData = new MutableLiveData<>();
        Customers customers = ((EndowsApplication) getApplication()).getCustomers();
        List<BeneficiaryDetail> beneficiaryDetails = customers.getBeneficiaryDetails();
        BeneficiaryDetail beneficiaryDetail = new BeneficiaryDetail();
        beneficiaryDetail.setBeneficiaryEmailId("");
        beneficiaryDetail.setBeneficiaryName("Add new\n contact");
        if (beneficiaryDetails != null) {
            BeneficiaryDetail zero = beneficiaryDetails.get(0);
            if (!(zero.getBeneficiaryEmailId().length() == 0)) {
                beneficiaryDetails.add(0, beneficiaryDetail);
            }
            beneficiaryLiveData.setValue(beneficiaryDetails);
        } else {
            List<BeneficiaryDetail> beneficiaryList = new ArrayList<>();
            beneficiaryList.add(beneficiaryDetail);
            beneficiaryLiveData.setValue(beneficiaryList);
        }
    }

    MutableLiveData<List<BeneficiaryDetail>> getBeneficiaryLiveData() {
        return beneficiaryLiveData;
    }
}
