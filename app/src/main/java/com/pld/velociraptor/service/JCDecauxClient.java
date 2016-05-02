package com.pld.velociraptor.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.pld.velociraptor.model.Station;
import com.pld.velociraptor.model.UserProfile;

import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Thibault on 02/05/2016.
 */
@Singleton
public class JCDecauxClient {

    @Inject
    JCDecauxServiceApi jcDecauxServiceApi;

    @Inject
    protected Properties properties;


    @Inject
    public JCDecauxClient() {
    }



    public static boolean isConnected(Context context) {
        ConnectivityManager cm
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();

    }

    /**
     * Gets the list of the stations
     * @return the user
     */
    public List<Station> getStations() {

        List<Station> result = jcDecauxServiceApi.getStations().getStations();

        return result;
    }


}
