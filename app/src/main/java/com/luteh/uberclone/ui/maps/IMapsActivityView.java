package com.luteh.uberclone.ui.maps;

/**
 * Created by Luthfan Maftuh on 19/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public interface IMapsActivityView {
    /**
     * Request permissions if not granted
     */
    void onLocationPermissionNotGranted();

    void onStartLocationUpdated();

    void onStopLocationUpdates();
}
