package com.example.splitit.apis;

import com.example.splitit.apis.response.ForgotPasswordResponse;
import com.example.splitit.apis.response.ProfileResponse;
import com.example.splitit.apis.response.ProfileUpdateResponse;
import com.example.splitit.apis.response.RegisterResponse;
import com.example.splitit.apis.response.UserDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("register.php")
    Call<RegisterResponse> getRegister(@Query("name") String name,
                                       @Query("email") String email,
                                       @Query("password") String password,
                                       @Query("mobile") String mobile);

    @GET("login.php")
    Call<UserDetails> getLogin(@Query("email") String email,
                               @Query("password") String password);

    @GET("forget.php")
    Call<ForgotPasswordResponse> getForgotPassword(@Query("email") String email);

    @GET("profile.php")
    Call<ProfileResponse> getProfile(@Query("userid") String userId);


    @POST("update_profile.php")
    Call<ProfileUpdateResponse> updateProfile(@Query("mobile") String mobile);



}
