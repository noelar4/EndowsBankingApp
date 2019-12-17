package com.endows.app.views.fragments.home.homebottom.adapters;

import com.endows.app.models.db.Customers;
import com.endows.app.views.fragments.home.homebottom.details.DetailFragment;
import com.endows.app.views.fragments.home.homebottom.transaction.TransactionFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BottomViewPagerAdapter extends FragmentPagerAdapter {

    private String[] tabs = new String[] {"Transactions", "Details"};

    private Customers mCustomers;
    private int accountType;

    public BottomViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return TransactionFragment.getInstance(accountType);
        } else {
            return DetailFragment.getInstance(mCustomers, accountType);
        }
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    public void setCustomer(Customers customer) {
        mCustomers = customer;
        notifyDataSetChanged();
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
        notifyDataSetChanged();
    }
}
