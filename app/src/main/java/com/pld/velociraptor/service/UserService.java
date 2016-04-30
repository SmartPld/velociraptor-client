package com.pld.velociraptor.service;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.pld.velociraptor.tools.RestClient;
import com.pld.velociraptor.tools.VeloTokenCredentials;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by a607937 on 09/06/2015.
 */
@Singleton
public class UserService {

    @Inject
    protected RestClient restClient;

    @Inject
    protected Context context;

    @Inject
    UserServiceApi userServiceApi;

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
    public void loadUserProfile(UserLoadedCallBack callback, VeloTokenCredentials credentials)  {

        LoadUserAsyncTask asyncLoader = new LoadUserAsyncTask(restClient, context, callback);

        String[] params = {credentials.getId()};
        int currentapiVersion = Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        } else {
            asyncLoader.execute(params);
        }

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void logout(String sessionToken, UserLoggedOutCallBack callback)
    {
        LogoutUserAsyncTask asyncLoader = new LogoutUserAsyncTask(restClient, context, callback);

        String[] params = {sessionToken};
        int currentapiVersion = Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        } else {
            asyncLoader.execute(params);
        }
    }

}
