package com.pld.velociraptor;

import android.app.Application;


import com.pld.velociraptor.config.AppComponent;
import com.pld.velociraptor.config.AppModule;
import com.pld.velociraptor.config.DaggerAppComponent;
import com.pld.velociraptor.config.VelociraptorModule;

import java.io.IOException;

/**
 * Created by a607937 on 11/06/2015.
 */
public class VelociraptorApplication extends Application {

    private AppComponent dependencyGraph;

    public AppComponent getAppComponent()
    {
        return dependencyGraph;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            dependencyGraph = DaggerAppComponent
                    .builder()
                    .appModule(new AppModule(this, "app.properties"))
                    .velociraptorModule(new VelociraptorModule())
                    .build();
        } catch (IOException e) {
            // Should not happen
            throw new RuntimeException(e);
        }

    }
}
