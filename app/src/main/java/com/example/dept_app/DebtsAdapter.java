package com.example.dept_app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dept_app.data.Debts;

import java.util.List;

public class DebtsAdapter extends RecyclerView.Adapter<DebtsAdapter.DebtViewHolder> {
    private List<Debts> debts;
    private OnDebtClickListener listener;

    public interface OnDebtClickListener {
        void onDebtClick(Debts debt);
    }

    public DebtsAdapter(List<Debts> debts, OnDebtClickListener listener) {
        this.debts = debts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DebtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_2, parent, false);
        return new DebtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DebtViewHolder holder, int position) {
        Debts d = debts.get(position);
        holder.text1.setText(d.getDescription());
        holder.text2.setText(d.getDate() + " - " + d.getAmount() + " (" + d.getType() + ")");
        holder.itemView.setOnClickListener(v -> listener.onDebtClick(d));
    }

    @Override
    public int getItemCount() { return debts.size(); }

    static class DebtViewHolder extends RecyclerView.ViewHolder {
        TextView text1, text2;
        DebtViewHolder(View view) {
            super(view);
            text1 = view.findViewById(android.R.id.text1);
            text2 = view.findViewById(android.R.id.text2);
        }
    }
}

