package com.example.splitit.ui.authentication.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.splitit.R;
import com.example.splitit.app.BaseActivity;
import com.example.splitit.app.SplititApplication;
import com.example.splitit.databinding.ActivitySplashBinding;
import com.example.splitit.helper.PreferenceManger;
import com.example.splitit.utils.StatusBarUtils;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {
    ActivitySplashBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.setLightStatusBar(this, R.color.black);
        binding = DataBindingUtil.setContentView(SplashActivity.this, R.layout.activity_splash);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (SplititApplication.getPreferenceManger().getStringValue(PreferenceManger.USER_Id).isEmpty()) {
                    openAuthActivity(SplashActivity.this, 1);
                    finish();
                } else {
                    openHomeActivity(SplashActivity.this, 1);
                    finish();
                }
            }
        }, 3000);
    }
}
