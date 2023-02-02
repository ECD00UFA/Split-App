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
import com.example.splitit.databinding.FragmentGroupSettingsBinding;

public class GroupSettingsFragment extends BaseFragment {

    FragmentGroupSettingsBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_group_settings, container, false);
            binding.headerView.headerSideImg.setImageResource(R.drawable.ic_back_arrow);
            binding.headerView.doneTxt.setVisibility(View.GONE);
            binding.headerView.settingImg.setVisibility(View.GONE);

        }
        return binding.getRoot();
    }
}
