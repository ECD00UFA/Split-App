package com.example.splitit.apis.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.splitit.apis.repositories.AuthenticationRepository;
import com.example.splitit.apis.repositories.DashBoardRepository;
import com.example.splitit.apis.response.ProfileResponse;
import com.example.splitit.apis.response.ProfileUpdateResponse;
import com.example.splitit.apis.response.UserDetails;

public class DashBoardViewModel extends ViewModel {
    DashBoardRepository dashBoardRepository;

    public DashBoardViewModel() {
        dashBoardRepository = new DashBoardRepository();
    }


    public LiveData<ProfileResponse> getProfile(Context context, String userId) {
        return dashBoardRepository.getProfile(context,userId);
    }
    public LiveData<ProfileUpdateResponse> updateProfile(Context context, String mobile) {
        return dashBoardRepository.updateProfile(context,mobile);
    }


}
