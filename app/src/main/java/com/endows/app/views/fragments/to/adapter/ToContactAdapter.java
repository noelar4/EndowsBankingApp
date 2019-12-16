package com.endows.app.views.fragments.to.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class ToContactAdapter extends RecyclerView.Adapter<ToContactAdapter.ToContactViewHolder> {

    @NonNull
    @Override
    public ToContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_single_contact, parent, false);
        return new ToContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToContactViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ToContactViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvName;

        ToContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_contact_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }


    }
}
