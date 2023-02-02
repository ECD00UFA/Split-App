package com.example.splitit.ui.dashBoard.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splitit.R;
import com.example.splitit.app.BaseRecyclerAdapter;
import com.example.splitit.database.data_models.Friend;
import com.example.splitit.databinding.LayoutItemContactsBinding;

import java.util.ArrayList;

public class FriendListAdapter extends BaseRecyclerAdapter {
    LayoutItemContactsBinding binding;
    Context mContext;
    ArrayList<Friend> friendList;
    onItemClicked onItemClicked;

    public FriendListAdapter(Context mContext, ArrayList<Friend> friendList, onItemClicked onItemClicked) {
        this.mContext = mContext;
        this.friendList = friendList;
        this.onItemClicked = onItemClicked;
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(LayoutInflater inflater, ViewGroup group) {
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_item_contacts, group, false);
        return new FriendListAdapter.FriendsViewHolder(binding);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return getViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FriendListAdapter.FriendsViewHolder activityViewHolder = (FriendListAdapter.FriendsViewHolder) holder;
        activityViewHolder.setData(friendList.get(activityViewHolder.getAbsoluteAdapterPosition()));
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClicked.onItemClicked(friendList.get(activityViewHolder.getAbsoluteAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    private class FriendsViewHolder extends RecyclerView.ViewHolder {
        LayoutItemContactsBinding binding;

        public FriendsViewHolder(LayoutItemContactsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        void setData(Friend friend) {
            binding.tvName.setText(friend.friendName);
            binding.tvContactNumber.setText(friend.friendContact);
        }
    }

    public interface onItemClicked {
        void onItemClicked(Friend friend);
    }

    public void filterList(ArrayList<Friend> filterlist) {
        friendList = filterlist;
        notifyDataSetChanged();
    }
}

