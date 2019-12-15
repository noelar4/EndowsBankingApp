package com.endows.app.views.fragments.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.NavigationViewHolder> {

    private String[] navItems = new String[] {"Transfer b/w accounts", "Send an Interac", "Pay Bill", "Settings", "Contact us", "Help & Support", "Sign out"};

    @NonNull
    @Override
    public NavigationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_single_nav_item, parent, false);
        return new NavigationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NavigationViewHolder holder, int position) {
        holder.bind(navItems[position]);
    }

    @Override
    public int getItemCount() {
        return navItems.length;
    }

    class NavigationViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvNavItem;

        NavigationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNavItem = itemView.findViewById(R.id.tv_nav_item_name);
        }

        void bind(String nav) {
            tvNavItem.setText(nav);
        }
    }
}
