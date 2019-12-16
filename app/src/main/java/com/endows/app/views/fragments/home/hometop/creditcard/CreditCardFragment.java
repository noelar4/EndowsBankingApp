package com.endows.app.views.fragments.home.hometop.creditcard;


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
import com.endows.app.models.app.AccountCardDetails;
import com.endows.app.models.db.CardDetails;
import com.endows.app.models.db.CreditCardDetails;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreditCardFragment extends Fragment {

    private static final String MONEY_TEMPLATE = "$%s";

    private AppCompatTextView tvBalance;
    private AppCompatTextView tvCardNo;
    private AppCompatTextView tvExpiry;
    private AppCompatTextView tvLastPayment;

    private CreditCardViewModel mCreditCardViewModel;

    public CreditCardFragment() {
        // Required empty public constructor
    }

    public static CreditCardFragment getInstance(AccountCardDetails accountCardDetails) {
        CreditCardFragment fragment = new CreditCardFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BundleKeys.BUNDLE_CARD_DETAILS, accountCardDetails.getCardDetails());
        bundle.putParcelable(Constants.BundleKeys.BUNDLE_CREDIT_CARD_DETAILS, accountCardDetails.getCreditCardDetails());
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mCreditCardViewModel = ViewModelProviders.of(this).get(CreditCardViewModel.class);
        CardDetails cardDetails = (CardDetails) getArguments().get(Constants.BundleKeys.BUNDLE_CARD_DETAILS);
        CreditCardDetails creditCardDetails = (CreditCardDetails) getArguments().get(Constants.BundleKeys.BUNDLE_CREDIT_CARD_DETAILS);
        mCreditCardViewModel.getCardDetailLiveData().setValue(cardDetails);
        mCreditCardViewModel.getCreditCardLiveData().setValue(creditCardDetails);

        return inflater.inflate(R.layout.fragment_credit_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvBalance = view.findViewById(R.id.tv_credit_balance);
        tvCardNo = view.findViewById(R.id.tv_credit_credit_no);
        tvExpiry = view.findViewById(R.id.tv_credit_valid_thru_value);
        tvLastPayment = view.findViewById(R.id.tv_credit_card_payment_date);

        mCreditCardViewModel.getCardDetailLiveData().observe(this, new Observer<CardDetails>() {
            @Override
            public void onChanged(CardDetails cardDetails) {
                tvCardNo.setText(mCreditCardViewModel.formatCardNo(cardDetails.getCardNumber()));
                tvExpiry.setText(cardDetails.getExpiryDate());
            }
        });

        mCreditCardViewModel.getCreditCardLiveData().observe(this, new Observer<CreditCardDetails>() {
            @Override
            public void onChanged(CreditCardDetails creditCardDetails) {
                if (creditCardDetails == null) return;
                String debit = creditCardDetails.getDebitedAmt() == null? "0" : creditCardDetails.getDebitedAmt();
                String credit = creditCardDetails.getCreditedAmt() == null? "0" : creditCardDetails.getCreditedAmt();
                tvBalance.setText(String.format(Locale.getDefault(), MONEY_TEMPLATE, String.valueOf(Double.valueOf(debit) - Double.valueOf(credit))));
                tvLastPayment.setText(creditCardDetails.getLastPaymentDate());
            }
        });
    }
}
