package com.endows.app.views.fragments.homebottom.adapters;

import com.endows.app.views.fragments.homebottom.DetailFragment;
import com.endows.app.views.fragments.homebottom.TransactionFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BottomViewPagerAdapter extends FragmentPagerAdapter {

    private String[] tabs = new String[] {"Transactions", "Details"};

    public BottomViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new TransactionFragment();
        } else {
            return new DetailFragment();
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
}
