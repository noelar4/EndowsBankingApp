package com.endows.app.views.fragments.home.hometop.chequing;


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
import com.endows.app.models.db.CardDetails;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChequingFragment extends Fragment {

    private static final String MONEY_TEMPLATE = "$%s";

    private AppCompatTextView tvBalance;
    private AppCompatTextView tvCardNo;
    private AppCompatTextView tvExpiry;
    private AppCompatTextView tvIncome;
    private AppCompatTextView tvExpense;

    private ChequingViewModel mChequingViewModel;

    public ChequingFragment() {
        // Required empty public constructor
    }

    public static ChequingFragment getInstance(CardDetails cardDetails, AccountDetails details) {
        ChequingFragment fragment = new ChequingFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BundleKeys.BUNDLE_ACCOUNT_DETAILS, details);
        bundle.putParcelable(Constants.BundleKeys.BUNDLE_DEBIT_CARD_DETAILS, cardDetails);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mChequingViewModel = ViewModelProviders.of(this).get(ChequingViewModel.class);
        AccountDetails details = (AccountDetails) getArguments().get(Constants.BundleKeys.BUNDLE_ACCOUNT_DETAILS);
        CardDetails cardDetails = (CardDetails) getArguments().get(Constants.BundleKeys.BUNDLE_DEBIT_CARD_DETAILS);
        mChequingViewModel.getAccountLiveData().setValue(details);
        mChequingViewModel.getCardLiveData().setValue(cardDetails);
        return inflater.inflate(R.layout.fragment_chequing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvBalance = view.findViewById(R.id.tv_chequing_balance);
        tvCardNo = view.findViewById(R.id.tv_chequing_credit_no);
        tvExpiry = view.findViewById(R.id.tv_chequing_valid_thru_value);
        tvIncome = view.findViewById(R.id.tv_chequing_income_value);
        tvExpense = view.findViewById(R.id.tv_chequing_expense_value);

        mChequingViewModel.getAccountLiveData().observe(this, new Observer<AccountDetails>() {
            @Override
            public void onChanged(AccountDetails details) {
                tvBalance.setText(String.format(Locale.getDefault(), MONEY_TEMPLATE, details.getAccountBalance() == null? "0" : details.getAccountBalance()));
                tvIncome.setText(String.format(Locale.getDefault(), MONEY_TEMPLATE, details.getCreditedAmt() == null? "0" : details.getCreditedAmt()));
                tvExpense.setText(String.format(Locale.getDefault(), MONEY_TEMPLATE, details.getDebitedAmt() == null? "0" : details.getDebitedAmt()));
            }
        });

        mChequingViewModel.getCardLiveData().observe(this, new Observer<CardDetails>() {
            @Override
            public void onChanged(CardDetails cardDetails) {
                tvCardNo.setText(mChequingViewModel.formatCardNo(cardDetails.getCardNumber()));
                tvExpiry.setText(cardDetails.getExpiryDate());
            }
        });
    }
}
