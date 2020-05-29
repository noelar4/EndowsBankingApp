package com.endows.app.views.fragments.home.homebottom.transaction;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;
import com.endows.app.common.Toast;
import com.endows.app.constants.Constants;
import com.endows.app.models.db.TransactionHistory;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransactionFragment extends Fragment implements View.OnClickListener {

    private TransactionAdapter mAdapter;
    private AppCompatTextView tvEmpty;
    private AppCompatImageView ivGenerate;
    private AppCompatTextView tvGenerate;

    private TransactionViewModel transactionViewModel;

    public TransactionFragment() {
        // Required empty public constructor
    }

    public static TransactionFragment getInstance(int accountType) {
        TransactionFragment fragment = new TransactionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.BundleKeys.BUNDLE_ACCOUNT_TYPE, accountType);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);
        return inflater.inflate(R.layout.fragment_transaction, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        transactionViewModel.getAccountTypeLiveData().setValue(0);
        tvEmpty = view.findViewById(R.id.tv_empty);
        ivGenerate = view.findViewById(R.id.iv_generate);
        tvGenerate = view.findViewById(R.id.tv_generate);

        RecyclerView rvTransaction = view.findViewById(R.id.rv_transaction);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvTransaction.setLayoutManager(manager);
        mAdapter = new TransactionAdapter(transactionViewModel.getHistoryLiveData().getValue());
        rvTransaction.setAdapter(mAdapter);

        transactionViewModel.getAccountTypeLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                transactionViewModel.getTransactionHistory();
                checkEmpty();
            }
        });

        transactionViewModel.getHistoryLiveData().observe(this, new Observer<List<TransactionHistory>>() {
            @Override
            public void onChanged(List<TransactionHistory> transactionHistories) {
                mAdapter.notifyDataSetChanged();
                checkEmpty();
            }
        });

        tvGenerate.setOnClickListener(this);
    }

    private void checkEmpty() {
        if (mAdapter.getItemCount() == 0) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeToast(getContext(), "Permission required for this feature");
        } else {
            transactionViewModel.generatePDF();
        }
    }
}
