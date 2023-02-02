package com.example.splitit.apis.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.splitit.apis.repositories.AuthenticationRepository;
import com.example.splitit.apis.response.ForgotPasswordResponse;
import com.example.splitit.apis.response.RegisterResponse;
import com.example.splitit.apis.response.UserDetails;

public class AuthenticationViewModel extends ViewModel {
         
    AuthenticationRepository authenticationRepository;

    public AuthenticationViewModel() {
        authenticationRepository = new AuthenticationRepository();
    }

    public LiveData<RegisterResponse> getRegister(Context context,String name, String email, String password, String mobile) {
        return authenticationRepository.getRegister(context,name,email,password,mobile);
    }

    public LiveData<UserDetails> getLogin(Context context,String mobile, String password) {
        return authenticationRepository.getLogin(context,mobile, password);
    }

    public LiveData<ForgotPasswordResponse> getForgotPassword(Context context,String email) {
        return authenticationRepository.getForgotPassword(context,email);
    }
}
