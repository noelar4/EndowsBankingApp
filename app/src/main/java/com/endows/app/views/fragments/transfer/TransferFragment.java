package com.endows.app.views.fragments.transfer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;
import com.endows.app.common.Toast;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class TransferFragment extends Fragment {

    private AppCompatTextView tvFrom;
    private AppCompatTextView tvTo;
    private AppCompatEditText edtAmount;
    private AppCompatTextView tvChecking;
    private AppCompatTextView tvSavings;
    private AppCompatTextView tvCreditCard;

    private BottomSheetBehavior behavior;

    private TransferViewModel mTransferViewModel;

    private int selectedItem = 0;

    private NavController navController;

    public TransferFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mTransferViewModel = ViewModelProviders.of(this).get(TransferViewModel.class);
        return inflater.inflate(R.layout.fragment_transfer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        tvFrom = view.findViewById(R.id.tv_transfer_from_account);
        tvTo = view.findViewById(R.id.tv_transfer_to_account);
        edtAmount = view.findViewById(R.id.edt_transfer_amount);
        ConstraintLayout bottomSheet = view.findViewById(R.id.bottom_sheet);
        tvChecking = view.findViewById(R.id.tv_transfer_chequing);
        tvSavings = view.findViewById(R.id.tv_transfer_savings);
        tvCreditCard = view.findViewById(R.id.tv_transfer_credit_card);

        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        mTransferViewModel.getMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeToast(getActivity(), s);
            }
        });

        tvFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItem = 1;
                tvCreditCard.setVisibility(View.GONE);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        tvTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItem = 2;
                tvCreditCard.setVisibility(View.VISIBLE);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        final AppCompatTextView tvContinue = view.findViewById(R.id.tv_transfer_continue);
        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = edtAmount.getText().toString();
                mTransferViewModel.transferAmount(tvFrom.getTag(), tvTo.getTag(), amount);
            }
        });

        tvChecking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                populateDataOnSelectedPart(tvChecking.getText().toString(), 1);
            }
        });

        tvSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                populateDataOnSelectedPart(tvSavings.getText().toString(), 2);
            }
        });

        tvCreditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                populateDataOnSelectedPart(tvCreditCard.getText().toString(), 3);
            }
        });

        mTransferViewModel.getChequingLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvChecking.setText(s);
            }
        });

        mTransferViewModel.getSavingsLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvSavings.setText(s);
            }
        });

        mTransferViewModel.getCreditCardLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvCreditCard.setText(s);
            }
        });

        mTransferViewModel.getTransferLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeSuccessToast(getActivity(), "Transaction successful");
                } else {
                    Toast.makeFailureToast(getActivity(), "Transaction failed");
                }

                goBackToHomePage();
            }
        });
    }

    private void populateDataOnSelectedPart(String accountText, int accountSelected) {
        if (selectedItem == 1) {
            tvFrom.setText(accountText);
            tvFrom.setTag(accountSelected);
        } else {
            tvTo.setText(accountText);
            tvTo.setTag(accountSelected);
        }
    }

    private void goBackToHomePage() {
        if (getView() == null) return;

        navController.navigate(R.id.nav_home);
    }
}
