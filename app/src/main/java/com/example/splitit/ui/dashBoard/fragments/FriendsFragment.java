package com.example.splitit.ui.dashBoard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.splitit.R;
import com.example.splitit.app.BaseFragment;
import com.example.splitit.databinding.FragmentFreindsBinding;
import com.example.splitit.ui.dashBoard.adapters.AddFriendsAdapter;
import com.example.splitit.utils.ViewUtil;

public class FriendsFragment extends BaseFragment {
    FragmentFreindsBinding binding;
    AddFriendsAdapter addFriendsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_freinds, container, false);
            binding.startGroupView.setBackground(ViewUtil.setViewBgDiffRadiusWithBorder(mContext,5,5,5,5,R.color.transparent,R.color.appYellow,2));

            addFriendsAdapter = new AddFriendsAdapter(mContext);
            binding.addFrndRev.setAdapter(addFriendsAdapter);
        }
        return binding.getRoot();
    }
}
