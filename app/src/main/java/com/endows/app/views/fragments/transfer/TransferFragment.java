package com.endows.app.views.fragments.transfer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.endows.app.R;


public class TransferFragment extends Fragment {

    private AppCompatTextView tvFrom;
    private AppCompatTextView tvTo;
    private AppCompatEditText edtAmount;

    private TransferViewModel mTransferViewModel;

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
        tvFrom = view.findViewById(R.id.tv_transfer_from_account);
        tvTo = view.findViewById(R.id.tv_transfer_to_account);
        edtAmount = view.findViewById(R.id.edt_transfer_amount);

        mTransferViewModel.getMessageLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            }
        });
        AppCompatTextView tvContinue = view.findViewById(R.id.tv_transfer_continue);
        tvContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTransferViewModel.transferAmount("", "", "");
            }
        });
    }
}
