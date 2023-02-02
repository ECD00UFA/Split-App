package com.example.splitit.ui.authentication.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.splitit.R;
import com.example.splitit.app.BaseActivity;
import com.example.splitit.databinding.ActivityAuthBinding;

public class AuthActivity extends BaseActivity {
    ActivityAuthBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(AuthActivity.this, R.layout.activity_auth);

    }
}
