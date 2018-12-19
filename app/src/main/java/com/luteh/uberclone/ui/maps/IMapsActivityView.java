package com.luteh.uberclone.ui.maps;

/**
 * Created by Luthfan Maftuh on 19/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public interface IMapsActivityView {
    /**
     * Show request permissions if not granted
     */
    void onLocationPermissionNotGranted();

    /**
     * Show message if user is online
     */
    void onStartLocationUpdated();

    /**
     * Show message if user is offline
     */
    void onStopLocationUpdates();
}
