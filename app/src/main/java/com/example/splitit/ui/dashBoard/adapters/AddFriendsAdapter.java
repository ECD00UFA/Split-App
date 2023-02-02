package com.example.splitit.ui.dashBoard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splitit.R;
import com.example.splitit.app.BaseFragment;
import com.example.splitit.app.BaseRecyclerAdapter;
import com.example.splitit.databinding.AdapterAddFriendLayoutBinding;

public class AddFriendsAdapter extends BaseRecyclerAdapter {
    Context mContext;
    AdapterAddFriendLayoutBinding binding;

    public AddFriendsAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        binding = DataBindingUtil.inflate(inflater, R.layout.adapter_add_friend_layout,group,false);
        return new AddFriendsViewHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       AddFriendsViewHolder addFriendsViewHolder = (AddFriendsViewHolder) holder;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    private class AddFriendsViewHolder extends RecyclerView.ViewHolder {
        AdapterAddFriendLayoutBinding binding;

        public AddFriendsViewHolder(AdapterAddFriendLayoutBinding itemView) {
            super(itemView.getRoot());
            binding= itemView;

        }
    }
}
