package com.endows.app.views.fragments.interac;


import android.os.Bundle;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

/**
 * A simple {@link Fragment} subclass.
 */
public class InteracFragment extends Fragment {

    private AppCompatTextView tvFrom;
    private AppCompatTextView tvTo;
    private AppCompatEditText edtAmount;
    private AppCompatTextView tvChecking;
    private AppCompatTextView tvSavings;

    private BottomSheetBehavior behavior;

    private InteracViewModel interacViewModel;

    private int selectedItem = 0;

    private NavController navController;

    public InteracFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interac, container, false);
        interacViewModel = ViewModelProviders.of(this).get(InteracViewModel.class);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        tvFrom = view.findViewById(R.id.tv_interac_from_account);
        tvTo = view.findViewById(R.id.tv_interac_to_account);
        edtAmount = view.findViewById(R.id.edt_interac_amount);
        ConstraintLayout bottomSheet = view.findViewById(R.id.bottom_sheet_interac);
        tvChecking = view.findViewById(R.id.tv_interac_chequing);
        tvSavings = view.findViewById(R.id.tv_interac_savings);

        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setHideable(true);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        AppCompatTextView tvContinue = view.findViewById(R.id.tv_interac_continue);
        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        tvFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedItem = 1;
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        tvTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_interacFragment_to_toFragment);
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

        interacViewModel.getChequingLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvChecking.setText(s);
            }
        });

        interacViewModel.getSavingsLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvSavings.setText(s);
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
}
