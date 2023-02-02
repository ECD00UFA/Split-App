package com.example.splitit.ui.dashBoard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splitit.R;
import com.example.splitit.app.BaseRecyclerAdapter;
import com.example.splitit.databinding.AdapterActivityLayoutBinding;
import com.example.splitit.databinding.AdapterExpensesLayoutBinding;
import com.example.splitit.ui.dashBoard.dataModels.ActivityDataModel;
import com.example.splitit.ui.dashBoard.dataModels.ExpensesDataModel;

import java.util.ArrayList;

public class ExpensesAdapter extends BaseRecyclerAdapter {
    AdapterExpensesLayoutBinding binding;
    Context mContext;
    ArrayList<ExpensesDataModel> expensesDataModels;

    public ExpensesAdapter( Context mContext, ArrayList<ExpensesDataModel> expensesDataModels) {
        this.mContext = mContext;
        this.expensesDataModels = expensesDataModels;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        binding = DataBindingUtil.inflate(inflater, R.layout.adapter_expenses_layout,group,false);
        return new ExpensesAdapter.ExpensesViewHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ExpensesViewHolder activityViewHolder = (ExpensesViewHolder) holder;
        activityViewHolder.setData(expensesDataModels.get(position));
    }

    @Override
    public int getItemCount() {
        return expensesDataModels.size();
    }

    private class ExpensesViewHolder extends RecyclerView.ViewHolder {
        AdapterExpensesLayoutBinding binding;

        public ExpensesViewHolder(AdapterExpensesLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void setData(ExpensesDataModel expensesDataModel){
            binding.expenseImg.setImageResource(expensesDataModel.getExpenseImg());
            binding.groupNameTxt.setText(expensesDataModel.getGroupNameTxt());
            binding.expensesTxt.setText(expensesDataModel.getExpensesTxt());

        }
    }
}
