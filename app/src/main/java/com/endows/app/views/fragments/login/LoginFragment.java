package com.endows.app.views.fragments.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.endows.app.R;
import com.endows.app.constants.Constants;
import com.endows.app.views.fragments.HomeActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class LoginFragment extends Fragment {

    private AppCompatEditText edtCardNo;
    private AppCompatEditText edtPassword;
    private SwitchCompat switchRemember;
    private AppCompatTextView tvLoginSubmit;
    private AppCompatTextView tvTroubleSignIn;

    private LoginViewModel mLoginViewModel;


    public LoginFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        edtCardNo = view.findViewById(R.id.edt_login_account_no);
        edtPassword = view.findViewById(R.id.edt_login_password);
        switchRemember = view.findViewById(R.id.switch_remember_me);
        tvLoginSubmit = view.findViewById(R.id.btn_login_submit);
        tvTroubleSignIn = view.findViewById(R.id.tv_login_trouble_sign_in);


        mLoginViewModel.getErrorIndicator().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });

        mLoginViewModel.getLoginStatus().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean status) {
                if (status) {
                    goToHomeScreen();
                }
            }
        });

        mLoginViewModel.getCardNo().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    edtCardNo.setText(s);
                    switchRemember.setChecked(true);
                }
            }
        });

        tvLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNo = edtCardNo.getText().toString();
                String password = edtPassword.getText().toString();

                mLoginViewModel.loginUser(cardNo, password, switchRemember.isChecked());
            }
        });

        tvTroubleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                navController.navigate(R.id.troubleFragment);
            }
        });
    }

    private void goToHomeScreen() {
        Intent intent = new Intent(getContext(), HomeActivity.class);
        intent.putExtra(Constants.BundleKeys.BUNDLE_CUSTOMER_DETAILS, mLoginViewModel.getCustomerDetails());
        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }


}
