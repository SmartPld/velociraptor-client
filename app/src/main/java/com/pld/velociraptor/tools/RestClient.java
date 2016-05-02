package com.pld.velociraptor.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.service.TripServiceApi;
import com.pld.velociraptor.service.UserService;
import com.pld.velociraptor.service.UserServiceApi;

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
    protected UserServiceApi userServiceApi;

    @Inject
    protected TripServiceApi tripServiceApi;

    @Inject
    protected Context context;

    @Inject
    protected Properties properties;

    @Inject
    UserService userService;

    @Inject
    public RestClient() {
    }

    public UserServiceApi getUserService() {
        return userServiceApi;
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

        UserProfile user = this.userServiceApi.getUserProfile(sessionToken);

        return user;
    }

    /**
     * gets the trips
     * @return
     */
    public List<Trip> getTrips(VeloFilter filter) {

        List<Trip> trips = this.tripServiceApi.loadTrips(filter.getLimit(), filter.getMinDistance(), filter.getMaxDistance(), getAccessToken());
        return trips;
    }

    public VeloTokenCredentials loginUser(String login, String password) {

        return this.userServiceApi.login(new VeloCredentials(login, password));
    }

    public void logoutUser(String sessionToken) {
        this.userServiceApi.logout(sessionToken);
    }

    public Trip acceptTrip(Integer idUser, Integer idTrip) {
        return this.userServiceApi.acceptTrip(idUser, idTrip, "", getAccessToken());
    }

    public UserProfile terminateTrip(UserProfile user) {
        return this.userServiceApi.terminateTrip(user.getId(), "", getAccessToken() );
    }

    private String getAccessToken(){

        return userService.getCredentials().getId();
    }
}