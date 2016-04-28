package com.pld.velociraptor.config;

import com.pld.velociraptor.service.TripServiceApi;
import com.pld.velociraptor.service.TripServiceMockImpl;
import com.pld.velociraptor.service.UserServiceApi;
import com.pld.velociraptor.service.UserServiceMockImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by a607937 on 16/06/2015.
 */
@Module
public class VelociraptorEnvModule {

    @Provides
    @Singleton
    UserServiceApi provideUserServiceApi(UserServiceMockImpl impl) {
        return impl;
    }

    @Provides
    @Singleton
    TripServiceApi provideTripServiceApi(TripServiceMockImpl impl) {
        return impl;
    }

}
