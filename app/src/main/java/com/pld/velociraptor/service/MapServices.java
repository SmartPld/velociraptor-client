package com.pld.velociraptor.service;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.google.android.gms.maps.GoogleMap;
import com.pld.velociraptor.tools.RestClient;

import javax.inject.Inject;

/**
 * Created by Thibault on 28/04/2016.
 */
public class MapServices {

    @Inject
    protected RestClient restClient;

    @Inject
    protected Context context;

    @Inject
    UserServiceApi userServiceApi;

    @Inject
    public MapServices() {

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void drawTrip(TripDrawnCallBack callback, GoogleMap googleMap, String depart, String arrivee)  {

        ItineraireAsyncTask asyncLoader = new ItineraireAsyncTask(restClient, context, callback, googleMap);

        String[] params = {depart, arrivee};
        int currentapiVersion = Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        } else {
            asyncLoader.execute(params);
        }

    }
}
