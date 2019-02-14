package com.luteh.uberclone.ui.maps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.LinearInterpolator;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.luteh.uberclone.common.Common;

import static com.luteh.uberclone.common.AppConstant.ARG_DRIVERS;
import static com.luteh.uberclone.common.AppConstant.DISPLACMENT;
import static com.luteh.uberclone.common.AppConstant.FATEST_INTERVAL;
import static com.luteh.uberclone.common.AppConstant.MAPS_ACTIVITY_TAG;
import static com.luteh.uberclone.common.AppConstant.UPDATE_INTERVAL;

/**
 * Created by Luthfan Maftuh on 19/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class MapsActivityPresenterImp implements IMapsActivityPresenter {

    private Context context;
    private IMapsActivityView iMapsActivityView;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location lastLocation;

    public MapsActivityPresenterImp(Context context, IMapsActivityView iMapsActivityView) {
        this.context = context;
        this.iMapsActivityView = iMapsActivityView;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void startLocationUpdates() {
        if (!Common.isLocationPermissionGranted(context)) {
            iMapsActivityView.onLocationPermissionNotGranted();
            return;
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

        iMapsActivityView.onStartLocationUpdated();
    }

    @Override
    public void stopLocationUpdates() {
        if (!Common.isLocationPermissionGranted(context)) {
            return;
        }
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);

//        if (this.currentMarker != null) this.currentMarker.remove();

        iMapsActivityView.onStopLocationUpdates();
    }

    /**
     * To display the current location of user in Map
     */
    private void displayLocation() {
        if (!Common.isLocationPermissionGranted(context)) {
            return;
        }

        if (lastLocation != null) {
            final double latitude = lastLocation.getLatitude();
            final double longitude = lastLocation.getLongitude();

            // Update to Firebase
            new GeoFire(FirebaseDatabase.getInstance().getReference(ARG_DRIVERS))
                    .setLocation(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                            new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
                @Override
                public void onComplete(String key, DatabaseError error) {
                    //Add Marker
                    /*if (currentMarker != null) {
                        currentMarker.remove();
                    }
                    currentMarker = mMap.addMarker(new MarkerOptions()
//                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))
                            .position(new LatLng(latitude, longitude))
                            .title("You Location"));

                    // Move camera to this position
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));*/
                    /*// Draw animation rotate marker
                    rotateMarker(currentMarker, -360, mMap);*/

                    iMapsActivityView.onDisplayLocation(latitude, longitude);
                }
            });
        }
    }

    /**
     * To animate map marker
     */
    private void rotateMarker(Marker currentMarker, int i, GoogleMap mMap) {
        Handler handler = new Handler();
        long start = SystemClock.uptimeMillis();
        float startRotation = currentMarker.getRotation();
        long duration = 1500;

        LinearInterpolator interpolator = new LinearInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                float rot = t * i + (1 - t) * startRotation;
                currentMarker.setRotation(-rot > 180 ? rot / 2 : rot);
                if (t < 1.0) {
                    handler.postDelayed(this, 16);
                }
            }
        });
    }

    @Override
    public void initLocationUtils() {
        if (Common.isLocationPermissionGranted(context)) {
            buildLocationRequest();
            buildLocationCallback();

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        }
    }

    private void buildLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FATEST_INTERVAL)
                .setSmallestDisplacement(DISPLACMENT);
    }

    private void buildLocationCallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    lastLocation = location;
                }
                Log.d(MAPS_ACTIVITY_TAG, String.format("Latitude: %f | Longitude: %f", lastLocation.getLatitude(), lastLocation.getLongitude()));
                displayLocation();
            }
        };
    }
}
