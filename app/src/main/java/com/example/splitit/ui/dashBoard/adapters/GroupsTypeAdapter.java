package com.example.splitit.ui.dashBoard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splitit.R;
import com.example.splitit.app.BaseRecyclerAdapter;
import com.example.splitit.databinding.AdapterGroupTypeLayoutBinding;
import com.example.splitit.ui.dashBoard.dataModels.GroupsTypeDataModel;

import java.util.ArrayList;

public class GroupsTypeAdapter extends BaseRecyclerAdapter {

    Context mContext;
    AdapterGroupTypeLayoutBinding binding;
    ArrayList<GroupsTypeDataModel> groupsTypeDataModels;
    public GroupsTypeDataModel selectedType;

    public GroupsTypeAdapter(Context mContext, ArrayList<GroupsTypeDataModel> groupsTypeDataModels) {
        this.mContext = mContext;
        this.groupsTypeDataModels = groupsTypeDataModels;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        binding = DataBindingUtil.inflate(inflater, R.layout.adapter_group_type_layout, group, false);
        return new GroupsTypeViewHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GroupsTypeViewHolder groupsTypeViewHolder = (GroupsTypeViewHolder) holder;
        groupsTypeViewHolder.binding.typeView.setBackground(ContextCompat.getDrawable(mContext, groupsTypeDataModels.get(groupsTypeViewHolder.getAbsoluteAdapterPosition()).isSelected() ? R.drawable.bg_group_type_filled : R.drawable.bg_group_type));
        groupsTypeViewHolder.binding.typeImg.setImageResource(groupsTypeDataModels.get(groupsTypeViewHolder.getAbsoluteAdapterPosition()).getTypeImg());
        groupsTypeViewHolder.binding.tvGroupType.setText(groupsTypeDataModels.get(groupsTypeViewHolder.getAbsoluteAdapterPosition()).getTypeTxt());
        groupsTypeViewHolder.binding.typeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetOthers();
                if(groupsTypeViewHolder.getAbsoluteAdapterPosition()>= 0) {
                    selectedType = groupsTypeDataModels.get(groupsTypeViewHolder.getAbsoluteAdapterPosition());
                    groupsTypeDataModels.get(groupsTypeViewHolder.getAbsoluteAdapterPosition()).setSelected(true);
                    notifyDataSetChanged();
                }
            }
        });
    }

    private void resetOthers() {
        for (GroupsTypeDataModel item : groupsTypeDataModels) {
            item.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    private class GroupsTypeViewHolder extends RecyclerView.ViewHolder {

        AdapterGroupTypeLayoutBinding binding;

        public GroupsTypeViewHolder(AdapterGroupTypeLayoutBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
