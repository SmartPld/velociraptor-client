package com.pld.velociraptor.service;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.tools.RestClient;
import com.pld.velociraptor.tools.VeloFilter;

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
    UserService userService;

    @Inject
    TripService(){

    }


    public void loadTrips(VeloFilter filter, TripLoadedCallback tripLoadedCallback){

        LoadTripsAsyncTask asyncLoader = new LoadTripsAsyncTask(restClient, userService, context,tripLoadedCallback);

        VeloFilter[] params = {filter};
        int currentapiVersion = Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        } else {
            asyncLoader.execute(params);
        }
    }



}
