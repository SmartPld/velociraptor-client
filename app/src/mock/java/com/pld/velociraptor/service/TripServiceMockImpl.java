package com.pld.velociraptor.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.tools.VeloFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.converter.GsonConverter;
import retrofit.http.Query;

/**
 * Created by schieder on 4/26/16.
 */
@Singleton
public class TripServiceMockImpl implements TripServiceApi {

    @Inject
    Gson converter;

    @Inject
    Context context;

    @Inject
    public TripServiceMockImpl() {

    }

    @Override
    public List<Trip> loadTrips(Integer limit,  Integer minDist, Integer maxDist) {
        List<Trip> result = new ArrayList<>();
        try {
            InputStream is = context.getAssets().open("mock.json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            String json = total.toString();


            Type listType = new TypeToken<List<Trip>>() {}.getType();
            result = converter.fromJson(json, listType);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d("gson", "test");
        return result;
    }
}
