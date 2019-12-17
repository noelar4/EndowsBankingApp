package com.endows.app.views.fragments.findaccount;


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
import com.endows.app.constants.Constants;
import com.endows.app.models.db.Customers;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindAccountFragment extends Fragment {

    private AppCompatEditText edtEmail;

    private NavController controller;

    private FindAccountViewModel findAccountViewModel;

    public FindAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        findAccountViewModel = ViewModelProviders.of(this).get(FindAccountViewModel.class);
        return inflater.inflate(R.layout.fragment_find_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        controller = Navigation.findNavController(view);
        edtEmail = view.findViewById(R.id.edt_find_account_phone_or_email);
        AppCompatTextView tvSubmit = view.findViewById(R.id.tv_submit);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEmail.getText() == null) return;

                String emailId = edtEmail.getText().toString();
                findAccountViewModel.setEmail(emailId);
            }
        });

        findAccountViewModel.getMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeToast(getContext(), s);
            }
        });

        findAccountViewModel.getValidEmail().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

            }
        });

        findAccountViewModel.getCustomersMutableLiveData().observe(this, new Observer<Customers>() {
            @Override
            public void onChanged(Customers customers) {
                controller.navigate(R.id.enterOTPFragment);
            }
        });
    }
}
