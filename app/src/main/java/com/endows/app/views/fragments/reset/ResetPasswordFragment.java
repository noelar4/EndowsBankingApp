package com.endows.app.views.fragments.reset;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
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


public class ResetPasswordFragment extends Fragment {

    private AppCompatEditText edtPassword;
    private AppCompatEditText edtConfirmPassword;

    private NavController controller;

    private ResetViewModel resetViewModel;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resetViewModel = ViewModelProviders.of(this).get(ResetViewModel.class);
        return inflater.inflate(R.layout.fragment_reset_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        controller = Navigation.findNavController(view);
        edtPassword = view.findViewById(R.id.edt_reset_password_new);
        edtConfirmPassword = view.findViewById(R.id.edt_reset_password_new_confirm);

        AppCompatTextView tvContinue = view.findViewById(R.id.tv_reset_password_continue);
        AppCompatTextView tvCancel = view.findViewById(R.id.tv_reset_password_cancel);

        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPassword.getText() == null || edtConfirmPassword.getText() == null) {
                    return;
                }

                String password = edtPassword.getText().toString();
                String cPassword = edtConfirmPassword.getText().toString();

                resetViewModel.checkPassword(password, cPassword);

            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.navigate(R.id.loginFragment);
            }
        });

        resetViewModel.getMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeToast(getContext(), s);
            }
        });

        resetViewModel.getResetStatusLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeSuccessToast(getContext(), "User changed their password");
                    controller.navigate(R.id.loginFragment);
                } else {

                }
            }
        });
    }
}
