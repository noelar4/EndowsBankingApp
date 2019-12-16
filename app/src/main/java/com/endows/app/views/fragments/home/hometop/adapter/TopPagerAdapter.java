package com.endows.app.views.fragments.home.hometop.adapter;

import com.endows.app.common.PagetItem;
import com.endows.app.models.app.AccountCardDetails;
import com.endows.app.models.db.AccountDetails;
import com.endows.app.views.fragments.home.hometop.chequing.ChequingFragment;
import com.endows.app.views.fragments.home.hometop.creditcard.CreditCardFragment;
import com.endows.app.views.fragments.home.hometop.savings.SavingsFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TopPagerAdapter extends FragmentPagerAdapter {

    private List<PagetItem> mAccountDetails;

    public TopPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        PagetItem pagetItem = mAccountDetails.get(position);
        if (pagetItem instanceof AccountDetails) {
            AccountDetails details = (AccountDetails) pagetItem;
            return SavingsFragment.getInstance(details);
        } else {
            AccountCardDetails details = (AccountCardDetails) pagetItem;
            if (details.getAccountDetails() == null) {
                return CreditCardFragment.getInstance(details);
            } else {
                return ChequingFragment.getInstance(details.getCardDetails(), details.getAccountDetails());
            }
        }
    }

    @Override
    public int getCount() {
        return mAccountDetails == null? 0 : mAccountDetails.size();
    }

    public void setAccountDetails(List<PagetItem> accountDetails) {
        mAccountDetails = accountDetails;
        notifyDataSetChanged();
    }
}
