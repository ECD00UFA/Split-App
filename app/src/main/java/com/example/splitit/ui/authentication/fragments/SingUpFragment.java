package com.example.splitit.ui.authentication.fragments;

import static com.example.splitit.apis.repositories.AuthenticationRepository.ERROR;
import static com.example.splitit.apis.repositories.AuthenticationRepository.FAILED_MSG;

import android.app.AuthenticationRequiredException;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.splitit.R;
import com.example.splitit.apis.ViewModel.AuthenticationViewModel;
import com.example.splitit.apis.repositories.AuthenticationRepository;
import com.example.splitit.app.BaseActivity;
import com.example.splitit.app.BaseFragment;
import com.example.splitit.databinding.FragmentSignUpBinding;
import com.example.splitit.helper.Validations;
import com.example.splitit.utils.ViewUtil;
import com.google.gson.JsonObject;

import java.util.Objects;

public class SingUpFragment extends BaseFragment {
     FragmentSignUpBinding binding;
    PreferenceManager preferenceManager;
    AuthenticationViewModel authenticationViewModel;
    AuthenticationRepository authenticationRepository;

    ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(binding.getRoot()).navigateUp();
            }
        });
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (binding == null) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false);
            binding.headerView.backBtn.setImageDrawable(getResources().getDrawable(R.mipmap.ic_launcher));
            binding.headerView.headTxt.setVisibility(View.VISIBLE);
            authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
            binding.backBtn.setBackground(ViewUtil.setViewBg(mContext, 8, R.color.white));
            binding.doneBtn.setBackground(ViewUtil.setViewBg(mContext, 8, R.color.appYellow));
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");


            binding.countryCodeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    binding.countryCodePicker.launchCountrySelectionDialog();
                }
            });
            binding.backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(binding.getRoot()).navigateUp();
                }
            });
            binding.doneBtn.setOnClickListener(v ->{
                if (checkValidation()) {
                    getRegisterApi();
                }
            });

            binding.showPassBtn.setOnClickListener(v -> {
                showHidePass(v);
            });

            setTextChange(binding.fullNameEdt,val -> {
                if (val.trim().length()>0){
                    binding.detailView.setVisibility(View.VISIBLE);
                }
            });
        }
        return binding.getRoot();
    }

    public void getRegisterApi() {
        progressDialog.show();
        String name  = binding.fullNameEdt.getText().toString();
        String email  = binding.emailEdt.getText().toString();
        String password  = binding.passwordEdt.getText().toString();
        String mobile  = binding.phoneNoEt.getText().toString();

        authenticationViewModel.getRegister(mContext,name,email,password,mobile).observe((LifecycleOwner) mContext, apiResponse -> {
            if (apiResponse == null) {
                ((BaseActivity) mContext).showAlertDialog(ERROR, FAILED_MSG, "OK", "");
            } else {
                if (Objects.equals(apiResponse.getSuccess(), "1")) {
                    Navigation.findNavController(binding.getRoot()).navigateUp();
                    ((BaseActivity) mContext).showToast(apiResponse.getMessage());

                } else {
                    ((BaseActivity) mContext).showAlertDialog(ERROR, apiResponse.getMessage(), "OK", "");
                }

            }
            progressDialog.dismiss();
        });
    }

    public boolean checkValidation() {
        if (TextUtils.isEmpty(binding.fullNameEdt.getText().toString())) {
            showToast(mContext.getString(R.string.enter_email));
            return false;
        }

        if (TextUtils.isEmpty(binding.emailEdt.getText().toString())) {
            showToast(mContext.getString(R.string.enter_email));
            return false;
        }
        if (!Validations.validateEmail(binding.emailEdt.getText().toString())) {
            showToast(mContext.getString(R.string.enter_email_valid));
            return false;
        }
        if (TextUtils.isEmpty(binding.passwordEdt.getText().toString())) {
            showToast(mContext.getString(R.string.please_enter_password));
            return false;
        }
        if (binding.passwordEdt.getText().toString().trim().length() < 6) {
            showToast(mContext.getString(R.string.pass_six_greater));
            return false;
        }


        if (binding.passwordEdt.getText().toString().trim().length() < 6) {
            showToast(mContext.getString(R.string.pass_six_greater));
            return false;
        }

        if (binding.phoneNoEt.getText().toString().trim().length() < 6) {
            showToast(mContext.getString(R.string.pass_six_greater));
            return false;
        }


        if (binding.phoneNoEt.getText().toString().trim().length() < 10) {
            showToast(mContext.getString(R.string.phn_ten_greater));
            return false;
        }

        return true;
    }

    public void showHidePass(View view) {
        if (view.getId() == R.id.show_pass_btn) {
            if (binding.passwordEdt.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.ic_password_show);
                ((ImageView) (view)).setColorFilter(ContextCompat.getColor(requireContext(), R.color.appYellow));
                binding.passwordEdt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.passwordEdt.setSelection(binding.passwordEdt.getText().length());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.ic_password_hide);
                ((ImageView) (view)).setColorFilter(ContextCompat.getColor(requireContext(), R.color.appIconColor));
                binding.passwordEdt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.passwordEdt.setSelection(binding.passwordEdt.getText().length());
            }
        }
    }


}
