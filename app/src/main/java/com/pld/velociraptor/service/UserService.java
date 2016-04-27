package com.pld.velociraptor.service;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;


import com.pld.velociraptor.tools.RestClient;

import java.util.concurrent.ExecutionException;

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
    public UserService() {

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void loadDayForecast(UserLoadedCallBack callback, Long cityId) throws ExecutionException, InterruptedException {


        LoadUserAsyncTask asyncLoader = new LoadUserAsyncTask(restClient, context, callback);

        Long[] params = {cityId};
        int currentapiVersion = Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
        } else {
            asyncLoader.execute(params);
        }


    }

}
