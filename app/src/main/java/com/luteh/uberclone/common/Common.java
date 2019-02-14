package com.luteh.uberclone.common;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.luteh.uberclone.R;
import com.luteh.uberclone.remote.IGoogleAPI;
import com.luteh.uberclone.remote.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import androidx.core.app.ActivityCompat;
import dmax.dialog.SpotsDialog;

import static java.lang.Math.acos;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Created by Luthfan Maftuh on 17/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class Common {

    private static double PI_RAD = Math.PI / 180.0;

    private static android.app.AlertDialog waitingDialog;

    public static final String baseURL = "https://maps.googleapis.com";

    public static IGoogleAPI getGoogleAPI() {
        return RetrofitClient.getClient(baseURL).create(IGoogleAPI.class);
    }

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

    public static boolean isLocationPermissionGranted(Context context) {
        return (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Method to decode polyline points
     * Courtesy : jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     */
    public static List decodePoly(String encoded) {

        List poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    /**
     * Use Great Circle distance formula to calculate distance between 2 coordinates in kilometers.
     * https://software.intel.com/en-us/blogs/2012/11/25/calculating-geographic-distances-in-location-aware-apps
     */
    public static double greatCircleInKilometers(double lat1, double long1, double lat2, double long2) {
        double phi1 = lat1 * PI_RAD;
        double phi2 = lat2 * PI_RAD;
        double lam1 = long1 * PI_RAD;
        double lam2 = long2 * PI_RAD;

        return 6371.01 * acos(sin(phi1) * sin(phi2) + cos(phi1) * cos(phi2) * cos(lam2 - lam1));
    }
}
