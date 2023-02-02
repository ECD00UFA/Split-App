package com.example.splitit.app;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.splitit.interfaces.TextChangeListener;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;



public class BaseFragment extends Fragment {
    public Context mContext;
//    public UserDetails userDetails=null;
    public String SOMETHING_WENT_WRONG="Something went wrong!";
    public String APILOADINGTEXT="Please wait...";
    public String NODATAFOUND="Nothing to show here yet!";
    public String SESSION_EXPIRED_TEXT="Session expired,Please Login Again.";
    // public PreferenceManger preferenceManger;

    public String[] mediaPermissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        mContext = context;
//        preferenceManger=PetaraApplication.getPreferenceManger();
//        userDetails=preferenceManger.getUserDetails();
    }


    public void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(View view, String msg) {
        Snackbar.make(view,msg, Snackbar.LENGTH_LONG).show();
    }

//    public RequestBody toRequestBody (String value) {
//        return !TextUtils.isEmpty(value) ? RequestBody.create(MediaType.parse("text/plain"), value) : null;
//    }
    public  String getFileNameFromUrl(String url) {
        if(TextUtils.isEmpty(url))
            return "";
        else
        return url.substring(url.lastIndexOf('/') + 1);
    }

    public void showAlertDialog(String title, String msg, String positiveBtn, String negativeBtn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setPositiveButton(positiveBtn, (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        builder.setNegativeButton(negativeBtn, (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    public void showLoader(Context context) {
//        SplititLoader.showLoaderDialog(context);
//    }
//
//    public void hideLoader() {
//        SplititLoader.hideLoaderDialog();
//    }

    public String capitalizeFirstLetter(String str)
    {
        if(str == null) return "";
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }


    public double calculateAverage(ArrayList<Integer> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }

        double sum = 0;
        for (Integer mark : list) {
            sum += mark;
        }
        return sum / list.size();
    }

    public double calculateFloatAverage(ArrayList<Float> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }
        int count=0;

        double sum = 0;
        for (Float mark : list) {
            if(Float.compare(mark,.1f)>0){
                count++;
                sum += mark;
            }
        }
        return sum / count;
    }

    public double calculateDoubleAverage(ArrayList<Double> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }

        double sum = 0;
        for (Double mark : list) {
            sum += mark;
        }
        return sum / list.size();
    }



    public String getSplitString(String txt,String regex,int pos){
        if(TextUtils.isEmpty(txt))
            return "";
        String[] s=txt.split(regex);
        return s[pos];
    }


    public int getIntFromString(String value){
      if(TextUtils.isEmpty(value))
          return 0;
      else return Integer.parseInt(value);
    }

    public String setStringValue(String value){
        if(TextUtils.isEmpty(value))
            return "";
        else return value;
    }

    public void setTextChange(EditText editText, TextChangeListener listener)
    {
        editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                listener.onTextChange(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }



}
