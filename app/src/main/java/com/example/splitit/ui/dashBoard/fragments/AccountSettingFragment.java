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
import com.example.splitit.databinding.FragmentAccountSettingsBinding;

public class AccountSettingFragment extends BaseFragment {
    FragmentAccountSettingsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_settings, container, false);
            binding.headerView.headTxt.setText("Account Settings");
            binding.headerView.doneTxt.setVisibility(View.GONE);
            binding.headerView.headerSideImg.setVisibility(View.GONE);
        }
        return binding.getRoot();
    }
}
