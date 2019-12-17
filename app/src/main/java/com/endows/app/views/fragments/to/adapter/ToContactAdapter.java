package com.endows.app.views.fragments.to.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;
import com.endows.app.models.db.BeneficiaryDetail;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class ToContactAdapter extends RecyclerView.Adapter<ToContactAdapter.ToContactViewHolder> {

    private List<BeneficiaryDetail> beneficiaryDetailList;

    private OnItemClickListener mListener;

    public ToContactAdapter(List<BeneficiaryDetail> beneficiaryDetailList) {
        this.beneficiaryDetailList = beneficiaryDetailList;
    }

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
        holder.bind(beneficiaryDetailList.get(position));
    }

    @Override
    public int getItemCount() {
        return beneficiaryDetailList.size();
    }

    public void setItemListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    class ToContactViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvName;
        private AppCompatImageView ivImage;

        ToContactViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_contact_name);
            ivImage = itemView.findViewById(R.id.iv_contact_photo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.itemClicked(beneficiaryDetailList.get(getLayoutPosition()));
                    }
                }
            });
        }

        void bind(BeneficiaryDetail detail) {
            tvName.setText(detail.getBeneficiaryName());
            if (detail.getBeneficiaryEmailId().length() == 0) {
                ivImage.setImageResource(R.drawable.ic_plus);
            } else {
                ivImage.setImageResource(R.drawable.ic_account);
            }
        }

    }

    public interface OnItemClickListener {
        void itemClicked(BeneficiaryDetail detail);
    }
}
