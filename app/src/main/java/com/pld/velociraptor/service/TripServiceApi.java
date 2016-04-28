package com.pld.velociraptor.service;

import com.pld.velociraptor.model.Trip;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;

/**
 *
 * This is the interface for MOCK and FULL implementation of the trip service
 *
 * Created by maxou on 4/26/16.
 */
public interface TripServiceApi {
    @GET("/trajets")
    List<Trip> loadTrips(@Query("filter[limit]")int limit);

}
