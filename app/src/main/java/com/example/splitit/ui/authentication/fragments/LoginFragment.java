package com.example.splitit.ui.authentication.fragments;

import static com.example.splitit.apis.repositories.AuthenticationRepository.ERROR;
import static com.example.splitit.apis.repositories.AuthenticationRepository.FAILED_MSG;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
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
import com.example.splitit.apis.response.UserDetails;
import com.example.splitit.app.BaseActivity;
import com.example.splitit.app.BaseFragment;
import com.example.splitit.app.SplititApplication;
import com.example.splitit.databinding.FragmentLoginBinding;
import com.example.splitit.helper.PreferenceManger;
import com.example.splitit.helper.Validations;
import com.example.splitit.utils.ViewUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

public class LoginFragment extends BaseFragment {
    FragmentLoginBinding binding;
    PreferenceManger preferenceManager;
    AuthenticationViewModel authenticationViewModel;
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
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
            binding.logInBtn.setBackground(ViewUtil.setViewBg(mContext, 8, R.color.appYellow));
            authenticationViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");

            binding.headerView.backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(binding.getRoot()).navigateUp();
                }
            });
            binding.logInBtn.setOnClickListener(v -> {
                if (checkValidation()) {
                    userLoginApi();
                }
            });

            binding.showPassBtn.setOnClickListener(v -> {
                showHidePass(v);
            });
            setTextChange(binding.emailEditText, val -> {
                if (val.trim().length() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        binding.emailEditText.setTextCursorDrawable(R.color.appYellow);
                    }
                }
            });
            setTextChange(binding.passwordEditText, val -> {
                if (val.trim().length() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        binding.passwordEditText.setTextCursorDrawable(R.color.appYellow);
                    }
                }
            });

        }
        return binding.getRoot();
    }

    public boolean checkValidation() {
        if (TextUtils.isEmpty(binding.emailEditText.getText().toString())) {
            showToast(mContext.getString(R.string.enter_email));
            return false;
        }
        if (!Validations.validateEmail(binding.emailEditText.getText().toString())) {
            showToast(mContext.getString(R.string.enter_email_valid));
            return false;
        }
        if (TextUtils.isEmpty(binding.passwordEditText.getText().toString())) {
            showToast(mContext.getString(R.string.please_enter_password));
            return false;
        }
        if (binding.passwordEditText.getText().toString().trim().length() < 6) {
            showToast(mContext.getString(R.string.pass_six_greater));
            return false;
        }
        return true;
    }

    public void userLoginApi() {
        progressDialog.show();
        String email  = binding.emailEditText.getText().toString();
        String password  = binding.passwordEditText.getText().toString();

        authenticationViewModel.getLogin(mContext,email,password).observe((LifecycleOwner) mContext, apiResponse -> {
            if (apiResponse == null) {
                ((BaseActivity) mContext).showAlertDialog(ERROR, FAILED_MSG, "OK", "");
            } else {
                try {
                     if (Objects.equals(apiResponse.getSuccess(), "1")) {
           //              UserDetails userDetails = new Gson().fromJson(apiResponse,UserDetails.class);
                        ((BaseActivity) mContext).openHomeActivity(mContext, 1);
                        ((BaseActivity) mContext).showToast(apiResponse.getMessage());
                        SplititApplication.getPreferenceManger().putUserDetails(apiResponse);
                        SplititApplication.getPreferenceManger().putString(preferenceManager.USER_Id,apiResponse.getUserId());
                        ((BaseActivity) mContext).openHomeActivity(mContext, 1);
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

    public void showHidePass(View view) {
        if (view.getId() == R.id.show_pass_btn) {
            if (binding.passwordEditText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())) {
                ((ImageView) (view)).setImageResource(R.drawable.ic_password_show);
                ((ImageView) (view)).setColorFilter(ContextCompat.getColor(requireContext(), R.color.appYellow));
                binding.passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                binding.passwordEditText.setSelection(binding.passwordEditText.getText().length());
            } else {
                ((ImageView) (view)).setImageResource(R.drawable.ic_password_hide);
                ((ImageView) (view)).setColorFilter(ContextCompat.getColor(requireContext(), R.color.appIconColor));
                binding.passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                binding.passwordEditText.setSelection(binding.passwordEditText.getText().length());
            }
        }
    }
}
