package com.endows.app.views.fragments.addaccount;


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
import com.endows.app.models.db.BeneficiaryDetail;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddContactFragment extends Fragment {

    private AppCompatEditText edtName;
    private AppCompatEditText edtEmailId;

    private NavController navController;

    private AddAccountViewModel addAccountViewModel;

    public AddContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        addAccountViewModel = ViewModelProviders.of(this).get(AddAccountViewModel.class);
        return inflater.inflate(R.layout.fragment_add_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        edtName = view.findViewById(R.id.edt_add_contact_name);
        edtEmailId = view.findViewById(R.id.edt_add_contact_email);

        AppCompatTextView tvCancel = view.findViewById(R.id.tv_add_contact_cancel);
        AppCompatTextView tvAddContinue = view.findViewById(R.id.tv_add_contact_continue);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_addContactFragment_to_toFragment);
            }
        });

        tvAddContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtName.getText() == null || edtEmailId.getText() == null) return;

                String name = edtName.getText().toString();
                String emailId = edtEmailId.getText().toString();

                BeneficiaryDetail detail = new BeneficiaryDetail();
                detail.setBeneficiaryName(name);
                detail.setBeneficiaryEmailId(emailId);
                addAccountViewModel.doAddContact(detail);
            }
        });

        addAccountViewModel.getMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeToast(getContext(), s);
            }
        });

        addAccountViewModel.getAddStatusLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeSuccessToast(getContext(), "Contact added successfully");
                } else {
                    Toast.makeFailureToast(getContext(), "Sorry, Could not added contact");
                }

                goBackToInteracPage();
            }
        });
    }

    private void goBackToInteracPage() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.BundleKeys.BUNDLE_BENEFITIARY_DETAILS, addAccountViewModel.getNewBenefiatiaryDetails().getValue());
        navController.navigate(R.id.action_addContactFragment_to_interacFragment, bundle);
    }
}
