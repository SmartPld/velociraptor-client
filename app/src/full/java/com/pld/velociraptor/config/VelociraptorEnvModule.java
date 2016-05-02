package com.pld.velociraptor.config;


import com.google.gson.Gson;
import com.pld.velociraptor.BuildConfig;
import com.pld.velociraptor.service.JCDecauxService;
import com.pld.velociraptor.service.JCDecauxServiceApi;
import com.pld.velociraptor.service.TripServiceApi;
import com.pld.velociraptor.service.UserServiceApi;
import com.squareup.okhttp.OkHttpClient;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by a607937 on 16/06/2015.
 */
@Module
public class VelociraptorEnvModule {

    @Provides
    @Singleton
    UserServiceApi provideUserServiceApi(RestAdapter restAdapter) {

        return restAdapter.create(UserServiceApi.class);
    }

    @Provides
    @Singleton
    TripServiceApi provideTripServiceApi(RestAdapter restAdapter) {

        return restAdapter.create(TripServiceApi.class);
    }

    @Provides
    @Singleton
    JCDecauxServiceApi provideJCDecauxServiceApi(OkHttpClient client, Gson gson, Properties properties) {


        Executor executor = Executors.newCachedThreadPool();

        String url = "https://" + properties.getProperty("app.jcdecaux.baseurl");


        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
                .setEndpoint(url)
                .setExecutors(executor, executor)
                .setClient(new OkClient(client))
                .setConverter(new GsonConverter(gson))
                .build();

        return restAdapter.create(JCDecauxServiceApi.class);
    }

}
