package com.example.splitit.utils;

import android.util.Log;

import com.example.splitit.BuildConfig;


public class Logger {

    public static void ErrorLog(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
        }
    }
    public static void DebugLog(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }
}
