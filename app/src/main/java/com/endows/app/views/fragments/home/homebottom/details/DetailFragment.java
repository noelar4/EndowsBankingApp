package com.endows.app.views.fragments.home.homebottom.details;


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
import com.endows.app.models.db.CreditCardDetails;
import com.endows.app.models.db.Customers;

import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    public static final int TYPE_CHEQUING = 0;
    public static final int TYPE_SAVINGS = 1;
    public static final int TYPE_CREDIT_CARD = 2;

    private AppCompatTextView tvBalance;
    private AppCompatTextView tvAccountName;
    private AppCompatTextView tvAccountNo;
    private AppCompatTextView tvAccountNoTitle;


    private DetailsViewModel mDetailsViewModel;

    public DetailFragment() {

    }

    public static DetailFragment getInstance(Customers customers, int type) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BundleKeys.BUNDLE_CUSTOMER_DETAILS, customers);
        bundle.putInt(Constants.BundleKeys.BUNDLE_ACCOUNT_TYPE, type);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDetailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        mDetailsViewModel.updateCustomer((Customers) getArguments().get(Constants.BundleKeys.BUNDLE_CUSTOMER_DETAILS));
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tvBalance = view.findViewById(R.id.tv_details_balance_amount);
        tvAccountName = view.findViewById(R.id.tv_details_account_name_value);
        tvAccountNo = view.findViewById(R.id.tv_details_account_no);
        tvAccountNoTitle = view.findViewById(R.id.tv_details_account_number_title);

        mDetailsViewModel.getCustomerLiveData().observe(this, new Observer<Customers>() {
            @Override
            public void onChanged(Customers customers) {
                if (mDetailsViewModel.getAccountType().getValue() == TYPE_CHEQUING) {
                    tvAccountNoTitle.setText(R.string.label_details_account_no);
                    List<AccountDetails> accountDetails = customers.getAccountDetails();
                    for (AccountDetails details:
                         accountDetails) {
                        if (details.getAccountType().equals(Constants.TransactionConstants.CHEQUING_ACCOUNT)) {
                            tvBalance.setText(String.format(Locale.getDefault(), Constants.Templates.MONEY_TEMPLATE, details.getAccountBalance()));
                            tvAccountNo.setText(details.getAccountNumber());
                            break;
                        }
                    }

                } else if (mDetailsViewModel.getAccountType().getValue() == TYPE_SAVINGS) {
                    tvAccountNoTitle.setText(R.string.label_details_account_no);
                    List<AccountDetails> accountDetails = customers.getAccountDetails();
                    for (AccountDetails details:
                            accountDetails) {
                        if (details.getAccountType().equals(Constants.TransactionConstants.SAVINGS_ACCOUNT)) {
                            tvBalance.setText(String.format(Locale.getDefault(), Constants.Templates.MONEY_TEMPLATE, details.getAccountBalance()));
                            tvAccountNo.setText(details.getAccountNumber());
                            break;
                        }
                    }
                } else {
                    for (CardDetails card:
                            customers.getCardDetails()) {
                        if (card.getCardType() == 2) {
                            tvAccountNo.setText(card.getCardNumber());
                        }
                    }

                    CreditCardDetails creditCardDetails = customers.getCreditCardDetails();
                    if (creditCardDetails != null) {
                        String debit = creditCardDetails.getDebitedAmt() == null? "0" : creditCardDetails.getDebitedAmt();
                        String credit = creditCardDetails.getCreditedAmt() == null? "0" : creditCardDetails.getCreditedAmt();
                        tvBalance.setText(String.format(Locale.getDefault(), Constants.Templates.MONEY_TEMPLATE, String.valueOf(Double.valueOf(debit) - Double.valueOf(credit))));
                        tvAccountNoTitle.setText(R.string.label_creadit_card_number_title);
                    }
                }

                String name = customers.getFirstName() + " " + customers.getLastName();
                tvAccountName.setText(name);
            }
        });
    }
}
