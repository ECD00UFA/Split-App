package com.example.splitit.ui.dashBoard.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.splitit.R;
import com.example.splitit.app.BaseActivity;
import com.example.splitit.databinding.ActivityMainBinding;
import com.example.splitit.utils.ViewUtil;

import java.util.Objects;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    ActivityMainBinding binding;
    NavController navController;
    boolean showFilter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host_main);
        binding.appLayout.addExpenseView.setBackground(ViewUtil.setViewBg(this, 26, R.color.appYellow));
        binding.appLayout.filterVew.setBackground(ViewUtil.setViewBg(this, 8, R.color.white));
        showFilter = true;
        binding.appLayout.groupsTab.setOnClickListener(this);
        binding.appLayout.frndTab.setOnClickListener(this);
        binding.appLayout.activityTab.setOnClickListener(this);
        binding.appLayout.accountTab.setOnClickListener(this);

        binding.appLayout.headerView.addGroupImg.setOnClickListener(v -> {
            navController.navigate(R.id.createGroupFragment);
            binding.appLayout.headerView.headerView.setVisibility(View.GONE);
            binding.appLayout.filterVew.setVisibility(View.GONE);
            binding.appLayout.settledTxtView.setVisibility(View.GONE);
            binding.appLayout.filterVew.setVisibility(View.GONE);
        });
        binding.appLayout.filterImg.setOnClickListener(v -> {
            if (showFilter == true) {
                binding.appLayout.filterVew.setVisibility(View.VISIBLE);
                showFilter = false;
            } else {
                binding.appLayout.filterVew.setVisibility(View.GONE);
                showFilter = true;
            }
        });

        binding.drawerLayout.activityTab.setOnClickListener(this);
        binding.drawerLayout.groupsTab.setOnClickListener(this);
        binding.drawerLayout.frndTab.setOnClickListener(this);
        binding.drawerLayout.accountTab.setOnClickListener(this);
        binding.appLayout.headerView.menuBtn.setOnClickListener(v -> {
            drawerOpenCLose();
        });

        binding.appLayout.addExpenseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.addExpenseFragment);
            }
        });
    }

    void drawerOpenCLose() {
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            binding.mainDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    void navHandel(String type) {
        switch (type) {
            case "group":
                binding.appLayout.groupsImg.setImageResource(R.drawable.ic_selected_groups);
                binding.appLayout.frndImg.setImageResource(R.drawable.ic_freinds);
                binding.appLayout.activityImg.setImageResource(R.drawable.ic_activity);
                binding.appLayout.accountImg.setImageResource(R.drawable.profile_img);
                binding.appLayout.addExpenseView.setVisibility(View.VISIBLE);
                binding.appLayout.headerView.headerView.setVisibility(View.VISIBLE);
                binding.appLayout.headerView.addGroupImg.setImageResource(R.drawable.ic_add_more_groups);
                binding.appLayout.settledTxtView.setVisibility(View.VISIBLE);
                setTextResource(binding.appLayout.groupTxt, binding.appLayout.frndTxt, binding.appLayout.activityTxt, binding.appLayout.accountTxt);
                break;
            case "friend":
                binding.appLayout.groupsImg.setImageResource(R.drawable.ic_groups);
                binding.appLayout.frndImg.setImageResource(R.drawable.ic_selected_friends);
                binding.appLayout.activityImg.setImageResource(R.drawable.ic_activity);
                binding.appLayout.accountImg.setImageResource(R.drawable.profile_img);
                binding.appLayout.addExpenseView.setVisibility(View.VISIBLE);
                binding.appLayout.headerView.headerView.setVisibility(View.VISIBLE);
                binding.appLayout.headerView.addGroupImg.setImageResource(R.drawable.ic_add_groups);
                binding.appLayout.settledTxtView.setVisibility(View.VISIBLE);
                setTextResource(binding.appLayout.frndTxt, binding.appLayout.groupTxt, binding.appLayout.activityTxt, binding.appLayout.accountTxt);
                break;
            case "activity":
                binding.appLayout.fragmentMainView.setBackground(ViewUtil.setViewBgWithDiffRadius(this, 0, 0, 40, 40, R.color.white));
                binding.appLayout.groupsImg.setImageResource(R.drawable.ic_groups);
                binding.appLayout.frndImg.setImageResource(R.drawable.ic_freinds);
                binding.appLayout.activityImg.setImageResource(R.drawable.ic_selected_activity);
                binding.appLayout.accountImg.setImageResource(R.drawable.profile_img);
                binding.appLayout.addExpenseView.setVisibility(View.VISIBLE);
                binding.appLayout.headerView.headerView.setVisibility(View.GONE);
                binding.appLayout.filterVew.setVisibility(View.GONE);
                binding.appLayout.settledTxtView.setVisibility(View.GONE);
                binding.appLayout.filterVew.setVisibility(View.GONE);
                setTextResource(binding.appLayout.activityTxt, binding.appLayout.frndTxt, binding.appLayout.groupTxt, binding.appLayout.accountTxt);
                break;
            case "account":
                binding.appLayout.groupsImg.setImageResource(R.drawable.ic_groups);
                binding.appLayout.frndImg.setImageResource(R.drawable.ic_freinds);
                binding.appLayout.activityImg.setImageResource(R.drawable.ic_activity);
                binding.appLayout.activityImg.setImageResource(R.drawable.ic_activity);
                binding.appLayout.addExpenseView.setVisibility(View.GONE);
                binding.appLayout.headerView.headerView.setVisibility(View.GONE);
                binding.appLayout.filterVew.setVisibility(View.GONE);
                binding.appLayout.settledTxtView.setVisibility(View.GONE);
                setTextResource(binding.appLayout.accountTxt, binding.appLayout.groupTxt, binding.appLayout.frndTxt, binding.appLayout.activityTxt);
                break;
        }
    }


    void setTextResource(TextView selectedText, TextView... textViews) {
        selectedText.setTextColor(getResources().getColor(R.color.appYellow));
        for (TextView textView : textViews) {
            textView.setTextColor(getResources().getColor(R.color.appIconColor));
        }
    }


    @Override
    public void onClick(View v) {
        drawerOpenCLose();
        switch (v.getId()) {
            //Main Drawer
            case R.id.groupsTab:
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                navHandel("group");
                if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.homeFragment) {
                    navController.navigate(R.id.homeFragment);
                }
                break;
            case R.id.activityTab:
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                navHandel("activity");
                if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.activityFragment) {
                    navController.navigate(R.id.activityFragment);
                }
                break;
            case R.id.accountTab:
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                navHandel("account");
                if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.accountFragment) {
                    navController.navigate(R.id.accountFragment);
                }
                break;
            case R.id.frndTab:
                binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
                navHandel("friend");
                if (Objects.requireNonNull(navController.getCurrentDestination()).getId() != R.id.friendFragment) {
                    navController.navigate(R.id.friendFragment);
                }
                break;


        }
    }

    public void hideExpenseViewFab() {
        binding.appLayout.addExpenseView.setVisibility(View.GONE);
    }
}