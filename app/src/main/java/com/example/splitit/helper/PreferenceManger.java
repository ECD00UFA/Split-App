package com.example.splitit.helper;

import android.content.SharedPreferences;

import com.example.splitit.apis.response.UserDetails;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class PreferenceManger {

    public static final String PREF_KEY = "Petara_Preference";
    public static final String AUTH_TOKEN = "auth_token";
    public final String USER_DETAILS = "user_details";
    public static final String USER_Id = "user_id";
    public static final String USER_NAME = "user_name";
    public static final String DEVICE_TOKEN="device_token";
    public final String CHECK_USER_IS_LOGGED_IN = "user_logged_in";
    private SharedPreferences mSharedPreferences;


    public PreferenceManger(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    private SharedPreferences.Editor getEditor() {
        return mSharedPreferences.edit();
    }


    public void remove(String key) {
        SharedPreferences.Editor editor = getEditor();
        editor.remove(key);
        editor.apply();
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, value);
        editor.commit();
    }


    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getStringValue(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public String getStringValue(String key, String def) {
        return mSharedPreferences.getString(key, def);
    }

    public boolean getBooleanValue(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public boolean getBooleanValue(String key, boolean defaultValue) {
        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public int getIntegerValue(String key) {
        return mSharedPreferences.getInt(key, 0);
    }


    public String getAuthToken(){
        return getStringValue(AUTH_TOKEN);
    }



    public void verifyUserSession(){
        putBoolean(CHECK_USER_IS_LOGGED_IN,true);
    }

    public void putUserDetails(UserDetails userDetails) {
        Gson gson = new Gson();
        String json = gson.toJson(userDetails);
        putString(USER_DETAILS, json);
    }

    public UserDetails getUserDetails() {
        Gson gson = new Gson();
        String json = getStringValue(USER_DETAILS);
        return gson.fromJson(json, UserDetails.class);
    }

    public void logoutUser(){
        SharedPreferences.Editor editor = getEditor();
        editor.clear();
        editor.commit();
    }

    public void saveArrayList(ArrayList<String> list, String key){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        putString(key,json);
    }

    public ArrayList<String> getArrayList(String key){
        Gson gson = new Gson();
        String json = getStringValue(key);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }


}
