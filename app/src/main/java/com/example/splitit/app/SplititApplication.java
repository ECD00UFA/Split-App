package com.example.splitit.app;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.example.splitit.R;
import com.example.splitit.helper.CheckInternetConnection;
import com.example.splitit.helper.PreferenceManger;


public class SplititApplication extends MultiDexApplication {
    private static final String TAG = "PetaraApplication";
    private static SplititApplication instance;
    private static PreferenceManger preferenceManger;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        ViewPump.init(ViewPump.builder()
//                .addInterceptor(new CalligraphyInterceptor(
//                        new CalligraphyConfig.Builder()
//                                .setFontAttrId(io.github.inflationx.calligraphy3.R.attr.fontPath)
//                                .build())).build());

    }



    public static SplititApplication getInstance() {
        return instance;
    }

    public static PreferenceManger getPreferenceManger() {
        if (preferenceManger == null && getInstance()!=null) {

            preferenceManger = new PreferenceManger(getInstance().getSharedPreferences(PreferenceManger.PREF_KEY, Context.MODE_PRIVATE));
        }
        return preferenceManger;
    }


    public boolean isInternetConnected(Context context) {
        if (new CheckInternetConnection(this).isConnected())
            return true;
        else {
            ((BaseActivity)context).showAlertDialog("Network Error!!","Please enable data or wifi connection","OK","");
        }

        return false;
    }


}
