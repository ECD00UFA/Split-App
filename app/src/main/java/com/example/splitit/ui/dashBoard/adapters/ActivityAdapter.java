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
import com.example.splitit.ui.dashBoard.dataModels.ActivityDataModel;
import com.example.splitit.utils.ViewUtil;

import java.util.ArrayList;

public class ActivityAdapter extends BaseRecyclerAdapter {

    AdapterActivityLayoutBinding binding;
    Context mContext;
    ArrayList<ActivityDataModel> activityDataModels;

    public ActivityAdapter( Context mContext, ArrayList<ActivityDataModel> activityDataModels) {
        this.mContext = mContext;
        this.activityDataModels = activityDataModels;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        binding = DataBindingUtil.inflate(inflater, R.layout.adapter_activity_layout,group,false);
        return new ActivityViewHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
     ActivityViewHolder activityViewHolder = (ActivityViewHolder) holder;
     activityViewHolder.setData(activityDataModels.get(position));

    }

    @Override
    public int getItemCount() {
        return activityDataModels.size();
    }

    private class ActivityViewHolder extends RecyclerView.ViewHolder {
        AdapterActivityLayoutBinding binding;

        public ActivityViewHolder(AdapterActivityLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void setData(ActivityDataModel activityDataModel){
            binding.activityImg.setImageResource(activityDataModel.getActivityImg());
            binding.activityDetailTxt.setText(activityDataModel.getActivityDetailTxt());
            binding.activityTimeTxt.setText(activityDataModel.getActivityTimeTxt());
            binding.moneyDetailsTxt.setText(activityDataModel.getMoneyDetailTxt());
        }
    }
}
