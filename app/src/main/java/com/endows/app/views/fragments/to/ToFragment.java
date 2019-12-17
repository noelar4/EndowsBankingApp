package com.endows.app.views.fragments.to;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;
import com.endows.app.constants.Constants;
import com.endows.app.models.db.BeneficiaryDetail;
import com.endows.app.views.fragments.to.adapter.ToContactAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToFragment extends Fragment implements ToContactAdapter.OnItemClickListener {

    private AppCompatTextView tvTo;

    private ToContactAdapter mAdapter;

    private ToViewModel toViewModel;

    private NavController navController;

    public ToFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toViewModel = ViewModelProviders.of(this).get(ToViewModel.class);
        return inflater.inflate(R.layout.fragment_to, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        tvTo = view.findViewById(R.id.tv_to_account);
        RecyclerView rvContact = view.findViewById(R.id.rv_contacts);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        rvContact.setLayoutManager(manager);
        mAdapter = new ToContactAdapter(toViewModel.getBeneficiaryLiveData().getValue());
        rvContact.setAdapter(mAdapter);
        mAdapter.setItemListener(this);
    }


    @Override
    public void itemClicked(BeneficiaryDetail detail) {
        if (detail.getBeneficiaryEmailId().length() == 0) {
            navController.navigate(R.id.action_toFragment_to_addContactFragment);
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.BundleKeys.BUNDLE_BENEFITIARY_DETAILS, detail);
            navController.navigate(R.id.action_toFragment_to_interacFragment, bundle);
        }
    }
}
