package com.example.splitit.app;


import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.splitit.ui.dashBoard.activities.MainActivity;
import com.example.splitit.R;
import com.example.splitit.ui.authentication.activities.AuthActivity;
import com.example.splitit.utils.StatusBarUtils;
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class BaseActivity extends AppCompatActivity {
    public static String activeActivity = "HomeActivity";
    public String NODATAFOUND = "No Record Found!";
    public String FAILED_TEXT = "Something went wrong!";
    public String SESSION_EXPIRED_TEXT = "Session expired,Please Login Again.";
    Bundle bndlAnimation;
    public   final int REQ_CODE_CAMERA = 1;
    public  final int PIC_CROP=200;
//    public PreferenceManger preferenceManger;

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    public static String getActiveActivity() {
        return activeActivity;
    }

    public static void setActiveActivity(String activeActivity) {
        BaseActivity.activeActivity = activeActivity;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       StatusBarUtils.setLightStatusBar(this, R.color.white);
       // preferenceManger = SplititApplication.getPreferenceManger();
        bndlAnimation = ActivityOptions.makeCustomAnimation(this, R.animator.enter_from_right, R.animator.exit_to_left).toBundle();
    }
    public String getMimeType(Context context, Uri uri) {
        String mimeType = null;
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            ContentResolver cr = context.getContentResolver();
            mimeType = cr.getType(uri);
        } else {
            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(uri
                    .toString());
            mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                    fileExtension.toLowerCase());
        }
        return mimeType;
    }

    public boolean isFileMoreThan2MB(File file) {
        int maxFileSize = 2 * 1024 * 1024;
        Long l = file.length();
        String sizeNew="";
        double size = (double) l / 1000.0;
        if (size >= 1024) {
            sizeNew= (size / 1024) + " MB";
        } else {
            sizeNew= size + " KB";
        }
        String fileSize = l.toString();
        Log.e("fileSize",sizeNew);
        int finalFileSize = Integer.parseInt(fileSize);
        return finalFileSize >= maxFileSize;
    }

    public boolean isFileMoreThan25MB(File file) {
        int maxFileSize = 25 * 1024 * 1024;
        Long l = file.length();
        String sizeNew="";
        double size = (double) l / 1000.0;
        if (size >= 1024) {
            sizeNew= (size / 1024) + " MB";
        } else {
            sizeNew= size + " KB";
        }
        String fileSize = l.toString();
        Log.e("fileSize",sizeNew);
        int finalFileSize = Integer.parseInt(fileSize);
        return finalFileSize >= maxFileSize;
    }


    public void showAlertDialog(String title, String msg, String positiveBtn, String negativeBtn) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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


    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void showSnackBar(View view, String msg) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show();
    }

//    public RequestBody toRequestBody(String value) {
//        return !TextUtils.isEmpty(value) ? RequestBody.create(MediaType.parse("text/plain"), value) : null;
//    }

//    public void showLoader(Context context) {
//      SplititLoader.showLoaderDialog(context);
//    }
//
//    public void hideLoader() {
//        SplititLoader.hideLoaderDialog();
//    }


//    public void openWebViewActivity(Context context,String fileUrl,int fileType) {
//        Bundle bndlAnimation = ActivityOptions.makeCustomAnimation(context, R.animator.enter_from_right, R.animator.exit_to_left).toBundle();
//        Intent intent = new Intent(context, WebViewActivity.class);
//        intent.putExtra("fileUrl", fileUrl);
//        intent.putExtra("fileType", fileType);
//        context.startActivity(intent, bndlAnimation);
//    }
    public void openAuthActivity(Context context, int from) {
        Bundle bndlAnimation = ActivityOptions.makeCustomAnimation(context, R.animator.enter_from_right, R.animator.exit_to_left).toBundle();
        Intent intent = new Intent(context, AuthActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent, bndlAnimation);

    }

    public void openHomeActivity(Context context, int from) {
        Bundle bndlAnimation = ActivityOptions.makeCustomAnimation(context, R.animator.enter_from_right, R.animator.exit_to_left).toBundle();
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent, bndlAnimation);
        finish();
    }

    public String getCountryCode(String countryName) {
        String[] isoCountryCodes = Locale.getISOCountries();
        for (String code : isoCountryCodes) {
            Locale locale = new Locale("", code);
            if (countryName.equalsIgnoreCase(locale.getDisplayCountry())) {
                return code;
            }
        }
        return "";
    }

//    public void clearLocalData() {
//        AppDelegate.setAd(null);
//        SplititApplication.getPreferenceManger().logoutUser();
//        startActivity(new Intent(BaseActivity.this, SplashActivity.class));
//        finishAffinity();
//    }

    public File createImageFile() throws IOException {
        String imageFileName = "SPLITIT_Image-" + System.currentTimeMillis() + "_";
        File storageDir = getFilesDir();
        File image = File.createTempFile(imageFileName, ".png", storageDir);
        return image;
    }

    public String encodeFileToBase64Binary(File yourFile) {
        int size = (int) yourFile.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(yourFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public int getIntFromString(String value) {
        if (TextUtils.isEmpty(value))
            return 0;
        else return Integer.parseInt(value);
    }

    public void shareData(String filename, String content) {
      //  showLoader(this);
//        Bitmap bitmap=null;
//        try {
//            bitmap= convertToBitmap(getAssetImage(filename));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            hideLoader();
//            Intent intent = new Intent(Intent.ACTION_SEND);
//            intent.putExtra(Intent.EXTRA_TEXT, content);
//            String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "", null);
//            Uri screenshotUri = Uri.parse(path);
//            intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
//            intent.setType("image/*");
//            startActivity(Intent.createChooser(intent, "Share image via..."));
//        } catch (Exception exception) {
//            hideLoader();
//            Log.e("error", Objects.requireNonNull(exception.getMessage()));
//        }

        Uri imageUri = Uri.fromFile(new File("//android_asset/" + filename));
        Glide.with(this)
                .asBitmap()
                .load(imageUri)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        try {
                          //  hideLoader();
                            Intent intent = new Intent(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT, content + "\n" + "https://www.tghclinic.com");
                            String path = MediaStore.Images.Media.insertImage(getContentResolver(), resource, "", null);
                            Uri screenshotUri = Uri.parse(path);
                            intent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                            intent.setType("image/*");
                            startActivity(Intent.createChooser(intent, "Share image via..."));
                        } catch (Exception exception) {
                          //  hideLoader();
                            Log.e("error", Objects.requireNonNull(exception.getMessage()));
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                      //  hideLoader();
                    }
                });
    }


}

