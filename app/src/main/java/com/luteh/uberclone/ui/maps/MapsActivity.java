package com.luteh.uberclone.ui.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import butterknife.BindView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.github.glomadrian.materialanimatedswitch.MaterialAnimatedSwitch;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.luteh.uberclone.R;
import com.luteh.uberclone.common.BaseActivity;
import com.luteh.uberclone.common.Common;

import static com.luteh.uberclone.common.AppConstant.REQUEST_CODE_LOCATION;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        MaterialAnimatedSwitch.OnCheckedChangeListener,
        IMapsActivityView {

    private GoogleApiClient googleApiClient;

    private IMapsActivityPresenter iMapsActivityPresenter;

    @BindView(R.id.switchLocation)
    MaterialAnimatedSwitch switchLocation;

    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        switchLocation.setOnCheckedChangeListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onInit() {
        super.onInit();

        iMapsActivityPresenter = new MapsActivityPresenterImp(this, this);
        iMapsActivityPresenter.initLocationUtils();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_LOCATION:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        iMapsActivityPresenter.initLocationUtils();

                        onCheckedChanged(switchLocation.isChecked());
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        switchLocation.toggle();
                    }
                }
        }
    }

    @Override
    public void onCheckedChanged(boolean isOnline) {
        if (isOnline) {
            iMapsActivityPresenter.startLocationUpdates();
        } else {
            iMapsActivityPresenter.stopLocationUpdates();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       iMapsActivityPresenter.initMap(googleMap);
    }

    @Override
    public void onLocationChanged(Location location) {
        /*lastLocation = location;
        displayLocation();*/
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        /*displayLocation();
        startLocationUpdates();*/
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onLocationPermissionNotGranted() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, REQUEST_CODE_LOCATION);
    }

    @Override
    public void onStartLocationUpdated() {
        Common.showSnackBar(mapFragment.getView(), "You are online");
    }

    @Override
    public void onStopLocationUpdates() {
        Common.showSnackBar(mapFragment.getView(), "You are offline");
    }
}
