package com.luteh.uberclone.ui.maps;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Luthfan Maftuh on 19/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public interface IMapsActivityPresenter {

    void initMap(GoogleMap googleMap);

    void initLocationUtils();

    /**
     * To update current location when user turn on the on/off switch
     */
    void startLocationUpdates();

    /**
     * To stop update current location when user turn off the on/off switch
     */
    void stopLocationUpdates();
}
