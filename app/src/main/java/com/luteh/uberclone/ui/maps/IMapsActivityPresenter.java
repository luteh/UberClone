package com.luteh.uberclone.ui.maps;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Luthfan Maftuh on 19/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public interface IMapsActivityPresenter {

    void init();

    void initMap(GoogleMap googleMap);

    void initLocationUtils();

    void buildLocationRequest();

    void buildLocationCallback();

    void startLocationUpdates();

    void stopLocationUpdates();

    void displayLocation();
}
