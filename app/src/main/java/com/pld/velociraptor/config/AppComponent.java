package com.pld.velociraptor.config;



import com.pld.velociraptor.view.activity.DetailsTripActivity;
import com.pld.velociraptor.view.activity.LoginActivity;
import com.pld.velociraptor.view.activity.VelociraptorActivity;
import com.pld.velociraptor.view.fragment.DisplayStationFragment;
import com.pld.velociraptor.view.fragment.DisplayTripFragment;
import com.pld.velociraptor.view.fragment.FilterFragment;
import com.pld.velociraptor.view.fragment.ProfileFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by a607937 on 15/06/2015.
 */
@Singleton
@Component(modules = {VelociraptorModule.class, AppModule.class})
public interface AppComponent {

    void inject(VelociraptorActivity velociraptorActivity); //just for example

    void inject(LoginActivity loginActivity);

    void inject(DetailsTripActivity detailsTripActivity);

    void inject(DisplayTripFragment displayTripFragment);

    void inject(FilterFragment filterFragment);

    void inject(DisplayStationFragment displayStationFragment);

    void inject(ProfileFragment profileFragment);
}