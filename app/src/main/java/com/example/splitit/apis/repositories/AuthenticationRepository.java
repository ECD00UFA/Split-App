package com.example.splitit.apis.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.splitit.apis.ApiClient;
import com.example.splitit.apis.ApiInterface;
import com.example.splitit.apis.response.ForgotPasswordResponse;
import com.example.splitit.apis.response.RegisterResponse;
import com.example.splitit.apis.response.UserDetails;
import com.example.splitit.app.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationRepository {
    private ApiInterface apiInterface ;
    public static String FAILED_MSG = "Something went wrong!";
    public static String ERROR="Error";
    public AuthenticationRepository() {

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

    }

    public LiveData<RegisterResponse> getRegister(Context context,String name, String email, String password,String mobile){
        MutableLiveData<RegisterResponse> data = new MutableLiveData<>();
        apiInterface.getRegister(name,email,password,mobile).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                if (response.isSuccessful()){
                    Log.e("###",response.body().toString());
                    Log.e("###" , String.valueOf(response.code()));
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.e("LOGIN API FAILED ", t.getLocalizedMessage());
                ((BaseActivity) context).showAlertDialog(ERROR, FAILED_MSG, "OK", "");
            }
        });
        return data;
    }


    public LiveData<UserDetails> getLogin(Context context,String mobile, String password){
        MutableLiveData<UserDetails> data= new MutableLiveData<>();
        apiInterface.getLogin(mobile, password).enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(@NonNull Call<UserDetails> call, @NonNull Response<UserDetails> response) {
                if (response.isSuccessful()){
                    Log.e("###",response.body().toString());
                    Log.e("###" , String.valueOf(response.code()));
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Log.e("LOGIN API FAILED ", t.getLocalizedMessage());
                ((BaseActivity) context).showAlertDialog(ERROR, FAILED_MSG, "OK", "");
            }
        });
        return data;
    }


    public LiveData<ForgotPasswordResponse> getForgotPassword(Context context,String email){
        MutableLiveData<ForgotPasswordResponse> data= new MutableLiveData<>();
        apiInterface.getForgotPassword(email).enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(@NonNull Call<ForgotPasswordResponse> call, @NonNull Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful()){
                    Log.e("###",response.body().toString());
                    Log.e("###" , String.valueOf(response.code()));
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                Log.e("LOGIN API FAILED ", t.getLocalizedMessage());
                ((BaseActivity) context).showAlertDialog(ERROR, FAILED_MSG, "OK", "");
            }
        });
        return data;
    }



}
