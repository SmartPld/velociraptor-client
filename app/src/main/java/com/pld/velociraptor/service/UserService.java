package com.pld.velociraptor.service;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;

import com.google.gson.Gson;
import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.tools.RestClient;
import com.pld.velociraptor.tools.VeloTokenCredentials;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by a607937 on 09/06/2015.
 */
@Singleton
public class UserService {

    private static final String KEY_VELO_CREDENTIALS = "velo_credentials";
    @Inject
    protected RestClient restClient;

    @Inject
    protected Context context;


    @Inject
    Gson gson;

    UserProfile user;

    @Inject
    public UserService() {

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void getUserToken(String username, String password, UserLoggedCallBack callback)
    {

        LoginUserAsyncTask asyncLoader = new LoginUserAsyncTask(restClient, context, callback);

        String[] params = {username, password};
        int currentapiVersion = Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        } else {
            asyncLoader.execute(params);
        }
    }



    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void acceptTrip(UserProfile user, Trip trip, TripAcceptedCallBack tripAcceptedCallback){
        AcceptTripAsyncTask asyncLoader = new AcceptTripAsyncTask(restClient, context, tripAcceptedCallback);

        Integer[] params = {user.getId(), trip.getId()};
        int currentapiVersion = Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        } else {
            asyncLoader.execute(params);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void terminateTtrip(UserProfile user, TripTerminatedCallBack callBack) {
        TerminateTripAsyncTask asyncLoader = new TerminateTripAsyncTask(restClient, context, callBack);

        UserProfile[] params = {user};
        int currentapiVersion = Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        } else {
            asyncLoader.execute(params);
        }
    }

    public UserProfile getCurrentUser(){
        return user;
    }

    public VeloTokenCredentials getCredentials() {
        SharedPreferences sharedPref = context.getSharedPreferences("Users", Context.MODE_PRIVATE);
        String json = sharedPref.getString(KEY_VELO_CREDENTIALS, "null");

        if (json.compareTo("null") == 0) {
            return null;
        }
        VeloTokenCredentials veloTokeCredentials = gson.fromJson(json, VeloTokenCredentials.class);

        //TODO check validity

        return veloTokeCredentials;
    }


    public void storeCredentials(VeloTokenCredentials credentials){
        SharedPreferences sharedPref = context.getSharedPreferences("Users",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String json = gson.toJson(credentials);

        editor.putString(KEY_VELO_CREDENTIALS, json);
    }


    public void getUserProfile(VeloTokenCredentials credentials, UserLoadedCallBack callBack) {

        LoadUserAsyncTask asyncLoader = new LoadUserAsyncTask(restClient, context, callBack);

        VeloTokenCredentials[] params = {credentials};

        int currentapiVersion = Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        } else {
            asyncLoader.execute(params);
        }

    }

    /**
     * Stocks the current user profile locally so all activties can acces it using this userservice entity (provided by DI)
     * @param user
     */
    public void setUser(UserProfile user) {
        this.user = user;
    }

    public void logout(UserProfile user, UserLoggedOutCallBack callBack) {
        //TODO: tell server token is not valid any more + delete local token
    }
}
