package com.pld.velociraptor.config;



import com.pld.velociraptor.view.activity.LoginActivity;
import com.pld.velociraptor.view.activity.VelociraptorActivity;

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
}