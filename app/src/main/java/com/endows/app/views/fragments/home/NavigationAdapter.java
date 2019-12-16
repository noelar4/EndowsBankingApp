package com.endows.app.views.fragments.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;
import com.endows.app.constants.Constants;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationAdapter.NavigationViewHolder> {

    private String[] navItems = new String[] {
            Constants.NavItems.TRANSFER_BETWEEN,
            Constants.NavItems.INTERAC,
            Constants.NavItems.PAY_BILL,
            Constants.NavItems.HELP_SUPPORT,
            Constants.NavItems.SETTING,
            Constants.NavItems.CONTACT_US,
            Constants.NavItems.SIGN_OUT
    };

    private OnItemClickListener mListener;

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

    public void setItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    class NavigationViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvNavItem;

        NavigationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNavItem = itemView.findViewById(R.id.tv_nav_item_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.itemClicked(navItems[getLayoutPosition()]);
                    }
                }
            });
        }

        void bind(String nav) {
            tvNavItem.setText(nav);
        }
    }


    public interface OnItemClickListener {
        void itemClicked(String item);
    }

}
