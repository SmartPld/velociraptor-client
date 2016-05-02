package com.pld.velociraptor.service;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.google.gson.Gson;
import com.pld.velociraptor.BuildConfig;
import com.pld.velociraptor.tools.VeloFilter;
import com.squareup.okhttp.OkHttpClient;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by Thibault on 01/05/2016.
 */
@Singleton
public class JCDecauxService {


    @Inject
    public JCDecauxService(){

    }

    @Inject
    Gson gson;

    @Inject
    Properties properties;

    @Inject
    JCDecauxClient client;

    @Inject
    Context context;



    public void getStations(StationsLoadedCallBack callBack){

         LoadStationsAsyncTask asyncLoader = new LoadStationsAsyncTask(client, context, callBack);

        int currentapiVersion = Build.VERSION.SDK_INT;
        //here we check the level api
        if (currentapiVersion >= Build.VERSION_CODES.HONEYCOMB) {
            asyncLoader.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        } else {
            asyncLoader.execute();
        }


    }


}
