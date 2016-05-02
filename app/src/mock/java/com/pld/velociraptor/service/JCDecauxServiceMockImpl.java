package com.pld.velociraptor.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pld.velociraptor.model.JCDecauxResponse;
import com.pld.velociraptor.model.Trip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Thibault on 02/05/2016.
 */
public class JCDecauxServiceMockImpl implements JCDecauxServiceApi{

    @Inject
    Context context;

    @Inject
    Gson gson;


    @Inject
    public JCDecauxServiceMockImpl(){

    }




    @Override
    public JCDecauxResponse getStations() {
        JCDecauxResponse  result = null;
        try {
            InputStream is = context.getAssets().open("mock_stations.json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            String json = total.toString();



            result = gson.fromJson(json, JCDecauxResponse.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
