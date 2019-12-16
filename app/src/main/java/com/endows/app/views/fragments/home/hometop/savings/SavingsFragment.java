package com.endows.app.views.fragments.home.hometop.savings;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;
import com.endows.app.constants.Constants;
import com.endows.app.models.db.AccountDetails;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SavingsFragment extends Fragment {

    private static final String MONEY_TEMPLATE = "$%s";
    private AppCompatTextView tvBalance;
    private AppCompatTextView tvIncome;
    private AppCompatTextView tvExpence;

    private SavingsViewModel mSavingsViewModel;

    public SavingsFragment() {
    }

    public static SavingsFragment getInstance(AccountDetails details) {
        SavingsFragment fragment = new SavingsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BundleKeys.BUNDLE_ACCOUNT_DETAILS, details);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mSavingsViewModel = ViewModelProviders.of(this).get(SavingsViewModel.class);
        AccountDetails details = (AccountDetails) getArguments().get(Constants.BundleKeys.BUNDLE_ACCOUNT_DETAILS);
        mSavingsViewModel.getAccountLiveData().setValue(details);

        return inflater.inflate(R.layout.fragment_savings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvBalance = view.findViewById(R.id.tv_savings_balance);
        tvIncome = view.findViewById(R.id.tv_savings_income_value);
        tvExpence = view.findViewById(R.id.tv_savings_expense_value);

        mSavingsViewModel.getAccountLiveData().observe(this, new Observer<AccountDetails>() {
            @Override
            public void onChanged(AccountDetails details) {
                tvBalance.setText(String.format(Locale.getDefault(), MONEY_TEMPLATE, details.getAccountBalance() == null? "0" : details.getAccountBalance()));
                tvIncome.setText(String.format(Locale.getDefault(), MONEY_TEMPLATE, details.getCreditedAmt() == null? "0" : details.getCreditedAmt()));
                tvExpence.setText(String.format(Locale.getDefault(), MONEY_TEMPLATE, details.getDebitedAmt() == null? "0" : details.getDebitedAmt()));
            }
        });
    }
}
