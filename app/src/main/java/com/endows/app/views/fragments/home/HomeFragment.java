package com.endows.app.views.fragments.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.endows.app.common.PagetItem;
import com.endows.app.constants.Constants;
import com.endows.app.models.db.Customers;
import com.google.android.material.tabs.TabLayout;
import com.endows.app.R;
import com.endows.app.views.fragments.home.homebottom.adapters.BottomViewPagerAdapter;
import com.endows.app.views.fragments.home.hometop.adapter.TopPagerAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private ViewPager vpTopPager;
    private TopPagerAdapter mTopAdapter;
    private ViewPager vpBottomPager;
    private BottomViewPagerAdapter mBottomAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vpTopPager = view.findViewById(R.id.view_pager_home_top);
        mTopAdapter = new TopPagerAdapter(getChildFragmentManager());
        vpTopPager.setAdapter(mTopAdapter);

        vpBottomPager = view.findViewById(R.id.view_pager_home_bottom);
        mBottomAdapter = new BottomViewPagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpBottomPager.setAdapter(mBottomAdapter);

        TabLayout tabLayout = view.findViewById(R.id.home_tab);
        tabLayout.setupWithViewPager(vpBottomPager);

        vpTopPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
                vpBottomPager.setAdapter(mBottomAdapter);
            }
        });


        homeViewModel.getAccountsLiveData().observe(this, new Observer<List<PagetItem>>() {
            @Override
            public void onChanged(List<PagetItem> pagetItems) {
                mTopAdapter.setAccountDetails(pagetItems);
                mBottomAdapter.setCustomer(homeViewModel.getCustomerMutableLiveData().getValue());
            }
        });


    }
}