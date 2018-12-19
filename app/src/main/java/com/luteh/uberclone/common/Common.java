package com.luteh.uberclone.common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.luteh.uberclone.R;

import androidx.core.app.ActivityCompat;
import dmax.dialog.SpotsDialog;

/**
 * Created by Luthfan Maftuh on 17/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class Common {

    private static android.app.AlertDialog waitingDialog;

    public static void showSnackBar(View rootLayout, String message) {
        Snackbar.make(rootLayout, message, Snackbar.LENGTH_SHORT)
                .show();
    }

    public static void showSpotsProgressDialog(Context context) {
        waitingDialog = new SpotsDialog.Builder()
                .setContext(context)
                .setMessage("Loading . . . ")
                .setCancelable(false)
                .setTheme(R.style.CustomSpotsDialog)
                .build();
        if (!waitingDialog.isShowing()) {
            waitingDialog.show();
        }
    }

    public static void dismissSpotsProgress() {
        if (waitingDialog.isShowing())
            waitingDialog.dismiss();
    }

    public static boolean isLocationPermissionGranted(Context context){
        return (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED);
    }
}
