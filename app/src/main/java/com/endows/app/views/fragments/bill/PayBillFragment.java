package com.endows.app.views.fragments.bill;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayBillFragment extends Fragment implements View.OnClickListener {

    private AppCompatTextView tvFrom;
    private AppCompatTextView tvChooseUtilityType;
    private AppCompatEditText edtBillNo;
    private AppCompatTextView tvChequing;
    private AppCompatTextView tvCreditCard;
    private AppCompatTextView tvHydro;
    private AppCompatTextView tvWater;
    private AppCompatTextView tvGas;
    private AppCompatTextView tvPhone;
    private SwitchCompat switchRemember;

    private BottomSheetBehavior accountSheet;
    private BottomSheetBehavior utilityTypeSheet;

    public PayBillFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pay_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvFrom = view.findViewById(R.id.tv_pay_bill_from_value);
        tvChooseUtilityType = view.findViewById(R.id.tv_pay_bill_utility_type_value);
        edtBillNo = view.findViewById(R.id.edt_pay_bill_bill_no_value);
        tvChequing = view.findViewById(R.id.tv_bill_chequing);
        tvCreditCard = view.findViewById(R.id.tv_bill_credit_card);
        tvHydro = view.findViewById(R.id.tv_utility_hydro);
        tvWater = view.findViewById(R.id.tv_utility_water);
        tvGas = view.findViewById(R.id.tv_utility_gas);
        tvPhone = view.findViewById(R.id.tv_utility_phone);
        switchRemember = view.findViewById(R.id.switch_remember_me);

        ConstraintLayout accountLayout = view.findViewById(R.id.bottom_sheet_pay_bill_account);
        ConstraintLayout typeLayout = view.findViewById(R.id.bottom_sheet_utility_type);
        accountSheet = BottomSheetBehavior.from(accountLayout);
        utilityTypeSheet = BottomSheetBehavior.from(typeLayout);

        accountSheet.setHideable(true);
        utilityTypeSheet.setHideable(true);
        accountSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        utilityTypeSheet.setState(BottomSheetBehavior.STATE_HIDDEN);

        tvFrom.setOnClickListener(this);
        tvChooseUtilityType.setOnClickListener(this);
        tvChequing.setOnClickListener(this);
        tvCreditCard.setOnClickListener(this);
        tvHydro.setOnClickListener(this);
        tvWater.setOnClickListener(this);
        tvGas.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == tvFrom) {
            accountSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else if (view == tvChooseUtilityType) {
            utilityTypeSheet.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else if (view == tvChequing) {
            tvFrom.setText(tvChequing.getText().toString());
            tvFrom.setTag(false);
            accountSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else if (view == tvCreditCard) {
            tvFrom.setText(tvChequing.getText().toString());
            tvFrom.setTag(true);
            accountSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else if (view == tvHydro) {
            tvChooseUtilityType.setText(tvHydro.getText().toString());
            utilityTypeSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else if (view == tvWater) {
            tvChooseUtilityType.setText(tvWater.getText().toString());
            utilityTypeSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else if (view == tvGas) {
            tvChooseUtilityType.setText(tvGas.getText().toString());
            utilityTypeSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        } else if (view == tvPhone) {
            tvChooseUtilityType.setText(tvPhone.getText().toString());
            utilityTypeSheet.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }
}
