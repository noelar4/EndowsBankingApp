package com.endows.app.views.fragments.confirm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.endows.app.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class ConfirmAccountFragment extends Fragment {

    private AppCompatTextView tvUsername;
    private AppCompatTextView tvMobile;
    private AppCompatTextView tvEmail;
    private AppCompatRadioButton rbSMS;
    private AppCompatRadioButton rbEmail;

    private ConfirmAccountViewModel confirmAccountViewModel;

    private NavController navController;

    public ConfirmAccountFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        confirmAccountViewModel = ViewModelProviders.of(this).get(ConfirmAccountViewModel.class);
        return inflater.inflate(R.layout.fragment_confirm_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        tvUsername = view.findViewById(R.id.tv_confirm_account_user_name);
        tvMobile = view.findViewById(R.id.tv_confirm_account_sms);
        tvEmail = view.findViewById(R.id.tv_confirm_account_email);
        rbSMS = view.findViewById(R.id.rb_confirm_account_sms);
        rbEmail = view.findViewById(R.id.rb_confirm_account_email);

        RadioGroup radioGroup = new RadioGroup(getContext());
        radioGroup.addView(rbSMS);
        radioGroup.addView(rbEmail);

        confirmAccountViewModel.setCustomer();

        confirmAccountViewModel.getUsernameLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvUsername.setText(s);
            }
        });

        confirmAccountViewModel.getSmsLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvMobile.setText(s);
            }
        });

        confirmAccountViewModel.getEmailLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tvEmail.setText(s);
            }
        });

        AppCompatTextView tvContinue = view.findViewById(R.id.tv_confirm_account_continue);
        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        AppCompatTextView tvCancel = view.findViewById(R.id.tv_cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.loginFragment);
            }
        });

    }
}
