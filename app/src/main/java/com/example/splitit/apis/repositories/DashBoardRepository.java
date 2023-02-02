package com.example.splitit.apis.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.splitit.apis.ApiClient;
import com.example.splitit.apis.ApiInterface;
import com.example.splitit.apis.response.ProfileResponse;
import com.example.splitit.apis.response.ProfileUpdateResponse;
import com.example.splitit.app.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardRepository {
    private ApiInterface apiInterface ;
    public static String FAILED_MSG = "Something went wrong!";
    public static String ERROR="Error";

    public DashBoardRepository() {

        apiInterface = ApiClient.getRetrofit().create(ApiInterface.class);

    }

    public LiveData<ProfileResponse> getProfile(Context context, String userId){
        MutableLiveData<ProfileResponse> data= new MutableLiveData<>();
        apiInterface.getProfile(userId).enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileResponse> call, @NonNull Response<ProfileResponse> response) {
                if (response.isSuccessful()){
                    Log.e("###",response.body().toString());
                    Log.e("###" , String.valueOf(response.code()));
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                Log.e("LOGIN API FAILED ", t.getLocalizedMessage());
                ((BaseActivity) context).showAlertDialog(ERROR, FAILED_MSG, "OK", "");
            }
        });
        return data;
    }

    public LiveData<ProfileUpdateResponse> updateProfile(Context context, String mobile){
        MutableLiveData<ProfileUpdateResponse> data= new MutableLiveData<>();
        apiInterface.updateProfile(mobile).enqueue(new Callback<ProfileUpdateResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProfileUpdateResponse> call, @NonNull Response<ProfileUpdateResponse> response) {
                if (response.isSuccessful()){
                    Log.e("###",response.body().toString());
                    Log.e("###" , String.valueOf(response.code()));
                    data.setValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<ProfileUpdateResponse> call, Throwable t) {
                Log.e("LOGIN API FAILED ", t.getLocalizedMessage());
                ((BaseActivity) context).showAlertDialog(ERROR, FAILED_MSG, "OK", "");
            }
        });
        return data;
    }


}
