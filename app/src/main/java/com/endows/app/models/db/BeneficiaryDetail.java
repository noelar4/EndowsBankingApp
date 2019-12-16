package com.endows.app.models.db;

import android.os.Parcel;
import android.os.Parcelable;

public class BeneficiaryDetail implements Parcelable {
    private String beneficiaryName;
    private String beneficiaryEmailId;

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryEmailId() {
        return beneficiaryEmailId;
    }

    public void setBeneficiaryEmailId(String beneficiaryEmailId) {
        this.beneficiaryEmailId = beneficiaryEmailId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.beneficiaryName);
        dest.writeString(this.beneficiaryEmailId);
    }

    public BeneficiaryDetail() {
    }

    protected BeneficiaryDetail(Parcel in) {
        this.beneficiaryName = in.readString();
        this.beneficiaryEmailId = in.readString();
    }

    public static final Parcelable.Creator<BeneficiaryDetail> CREATOR = new Parcelable.Creator<BeneficiaryDetail>() {
        @Override
        public BeneficiaryDetail createFromParcel(Parcel source) {
            return new BeneficiaryDetail(source);
        }

        @Override
        public BeneficiaryDetail[] newArray(int size) {
            return new BeneficiaryDetail[size];
        }
    };
}
