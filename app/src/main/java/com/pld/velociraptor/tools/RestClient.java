package com.pld.velociraptor.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.service.TripServiceApi;
import com.pld.velociraptor.service.UserServiceApi;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by a607937 on 11/06/2015.
 */
@Singleton
public class RestClient {


    private static final String TAG = "RestClient";
    @Inject
    protected UserServiceApi userService;

    @Inject
    protected TripServiceApi tripServiceApi;

    @Inject
    protected Context context;

    @Inject
    protected Properties properties;


    @Inject
    public RestClient() {
    }

    public UserServiceApi getUserService() {
        return userService;
    }


    public static boolean isConnected(Context context) {
        ConnectivityManager cm
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();

    }

    /**
     * Gets the user
     *
     * @param sessionToken
     * @return the user
     */
    public UserProfile getUserProfile(String sessionToken) {

        UserProfile user = this.userService.getUserProfile(sessionToken);

        return user;
    }

    /**
     * gets the trips
     * @return
     */
    public List<Trip> getTrips() {

        List<Trip> trips = this.tripServiceApi.loadTrips(10);
        return trips;
    }
}