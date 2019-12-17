package com.endows.app.views.fragments.otp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;
import com.endows.app.common.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


public class EnterOTPFragment extends Fragment {

    private AppCompatTextView tvMessage;
    private AppCompatEditText edtOTP;

    private NavController controller;

    private OTPViewModel otpViewModel;

    public EnterOTPFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        otpViewModel = ViewModelProviders.of(this).get(OTPViewModel.class);
        return inflater.inflate(R.layout.fragment_enter_otp, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        controller = Navigation.findNavController(view);

        tvMessage = view.findViewById(R.id.tv_enter_otp_title);
        edtOTP = view.findViewById(R.id.edt_enter_otp_code);

        AppCompatTextView tvContinue = view.findViewById(R.id.tv_enter_otp_continue);
        AppCompatTextView tvCancel = view.findViewById(R.id.tv_enter_otp_cancel);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.navigate(R.id.action_enterOTPFragment_to_loginFragment);
            }
        });

        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtOTP.getText() == null) return;

                String code = edtOTP.getText().toString();
                otpViewModel.validateCode(code);
            }
        });

        otpViewModel.getMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeToast(getContext(), s);
            }
        });

        otpViewModel.getVerificationLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    controller.navigate(R.id.action_enterOTPFragment_to_resetPasswordFragment);
                }
            }
        });
    }
}
