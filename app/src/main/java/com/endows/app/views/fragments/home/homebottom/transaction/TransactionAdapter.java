package com.endows.app.views.fragments.home.homebottom.transaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.endows.app.R;
import com.endows.app.constants.Constants;
import com.endows.app.models.db.TransactionHistory;

import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<TransactionHistory> histories;

    public TransactionAdapter(List<TransactionHistory> histories) {
        this.histories = histories;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_single_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.bind(histories.get(position));
    }

    @Override
    public int getItemCount() {
        return histories == null? 0 : histories.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {

       private AppCompatTextView tvKey;
       private AppCompatTextView tvValue;

       TransactionViewHolder(@NonNull View itemView) {
           super(itemView);

           tvKey = itemView.findViewById(R.id.tv_single_transaction_description);
           tvValue = itemView.findViewById(R.id.tv_single_transaction_amount);
       }

       void bind(TransactionHistory history) {
           String builder = (history.getFrom() == null ? "" : "From: " + history.getFrom()) +
                   (history.getTo() == null ? "" : " To: " + history.getTo());
           tvKey.setText(builder);
           tvValue.setText(String.format(Locale.getDefault(), Constants.Templates.MONEY_TEMPLATE, history.getAmount()));
       }
   }

   void clear() {
        if (histories != null) {
            histories.clear();
        }
   }
}
