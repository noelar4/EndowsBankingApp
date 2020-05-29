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
import com.endows.app.common.Toast;
import com.endows.app.constants.Constants;
import com.endows.app.models.db.BeneficiaryDetail;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

/**
 * A simple {@link Fragment} subclass.
 */
public class InteracFragment extends Fragment {

    private AppCompatTextView tvFrom;
    private AppCompatTextView tvTo;
    private AppCompatEditText edtAmount;

    private InteracViewModel interacViewModel;

    private NavController navController;

    public InteracFragment() {

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

        setTo();

        AppCompatTextView tvContinue = view.findViewById(R.id.tv_interac_continue);
        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvTo.getText() == null || edtAmount.getText() == null) return;

                String receiverEmailId = tvTo.getText().toString();
                String amount = edtAmount.getText().toString();

                interacViewModel.doInterac(receiverEmailId, amount);
            }
        });

        tvTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_interacFragment_to_toFragment);
            }
        });

        interacViewModel.getChequingLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvFrom.setText(s);
            }
        });

        interacViewModel.getInteracLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeSuccessToast(getContext(), "Transferred Successfully");
                } else {
                    Toast.makeFailureToast(getContext(), "Transfer failed");
                }

                goBackToHomePage();
            }
        });
    }

    private void setTo() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            BeneficiaryDetail detail = bundle.getParcelable(Constants.BundleKeys.BUNDLE_BENEFITIARY_DETAILS);
            if (detail != null) {
                tvTo.setText(detail.getBeneficiaryEmailId());
            }
        }
    }

    private void goBackToHomePage() {
        navController.navigate(R.id.nav_home);
    }
}
