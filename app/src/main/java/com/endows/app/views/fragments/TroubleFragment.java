package com.endows.app.views.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TroubleFragment extends Fragment {

    private NavController controller;
    public TroubleFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trouble, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        controller = Navigation.findNavController(view);
        AppCompatTextView tvForgotPassword = view.findViewById(R.id.tv_trouble_forgot_password);
        AppCompatTextView tvNotEnrolled = view.findViewById(R.id.tv_trouble_not_enrolled);

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.navigate(R.id.findAccountFragment);
            }
        });

        tvNotEnrolled.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.navigate(R.id.findAccountFragment);
            }
        });

        AppCompatButton btnSignIn = view.findViewById(R.id.btn_sign_in);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.navigate(R.id.loginFragment);
            }
        });

    }
}
