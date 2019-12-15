package com.endows.app.views.fragments.hometop.adapter;

import com.endows.app.views.fragments.hometop.ChequingFragment;
import com.endows.app.views.fragments.hometop.SavingsFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TopPagerAdapter extends FragmentPagerAdapter {

    public TopPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new SavingsFragment();
        } else {
            return new ChequingFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
