package com.pld.velociraptor.service;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.pld.velociraptor.tools.RestClient;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by thomas on 28/04/2016.
 */
@Singleton
public class TripService {


    @Inject
    RestClient restClient;

    @Inject
    Context context;

    @Inject
    TripService(){

    }


    public void loadTrips(TripLoadedCallback tripLoadedCallback){
        LoadTripsAsyncTask asyncLoader = new LoadTripsAsyncTask(restClient, context,tripLoadedCallback);

        int currentapiVersion = Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        } else {
            asyncLoader.execute();
        }
    }

}
