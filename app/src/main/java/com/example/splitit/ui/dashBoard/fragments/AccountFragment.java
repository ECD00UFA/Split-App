package com.example.splitit.ui.dashBoard.fragments;

import static com.example.splitit.apis.repositories.AuthenticationRepository.ERROR;
import static com.example.splitit.apis.repositories.AuthenticationRepository.FAILED_MSG;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.splitit.R;
import com.example.splitit.apis.ViewModel.AuthenticationViewModel;
import com.example.splitit.apis.ViewModel.DashBoardViewModel;
import com.example.splitit.app.BaseActivity;
import com.example.splitit.app.BaseFragment;
import com.example.splitit.app.SplititApplication;
import com.example.splitit.databinding.FragmentAccountBinding;
import com.example.splitit.helper.PreferenceManger;
import com.example.splitit.utils.ViewUtil;

import java.util.Objects;

public class AccountFragment extends BaseFragment implements View.OnClickListener {
    FragmentAccountBinding binding;
    DashBoardViewModel dashBoardViewModel;
    PreferenceManger preferenceManager;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
            dashBoardViewModel =new ViewModelProvider(this).get(DashBoardViewModel.class);
            binding.editImg.setOnClickListener(this);
            binding.scanTxt.setOnClickListener(this);
            binding.emailSettingTxt.setOnClickListener(this);
            binding.splititProTxt.setOnClickListener(this);
            binding.emailSettingTxt.setOnClickListener(this);
            binding.passcodeTxt.setOnClickListener(this);
            binding.rateSplititTxt.setOnClickListener(this);
            binding.contactUsTxt.setOnClickListener(this);
        }
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Main Drawer
            case R.id.editImg:
                Navigation.findNavController(v).navigate(R.id.accountFragment);
                break;
        }
    }

    private void getProfile(){
        progressDialog.show();
        dashBoardViewModel.getProfile(mContext, SplititApplication.getPreferenceManger().getStringValue(preferenceManager.USER_Id)).observe((LifecycleOwner) mContext, apiResponse -> {
            if (apiResponse == null) {
                ((BaseActivity) mContext).showAlertDialog(ERROR, FAILED_MSG, "OK", "");
            } else {
                try {
                    if (Objects.equals(apiResponse.getSuccess(), "1")) {
                        ((BaseActivity) mContext).showToast(apiResponse.getMessage());
                        binding.userName.setText(apiResponse.getName());
                        binding.userEmail.setText(apiResponse.getEmail());
                        binding.userEmail.setText(apiResponse.getEmail());
                    } else {
                        ((BaseActivity) mContext).showAlertDialog(ERROR, apiResponse.getMessage(), "OK", "");
                    }
                }catch (Exception ignored){
                    Log.e("Error",ignored.getMessage());
                }


            }
            progressDialog.dismiss();
        });
    }

}
