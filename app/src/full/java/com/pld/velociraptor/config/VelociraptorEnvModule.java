package com.pld.velociraptor.config;


import com.pld.velociraptor.service.UserServiceApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

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

}
