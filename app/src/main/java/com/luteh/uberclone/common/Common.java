package com.luteh.uberclone.common;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

/**
 * Created by Luthfan Maftuh on 17/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class Common {

    public static void showSnackBar(View rootLayout, String message) {
        Snackbar.make(rootLayout, message, Snackbar.LENGTH_SHORT)
                .show();
    }
}
