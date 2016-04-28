package com.pld.velociraptor.service;

import com.pld.velociraptor.model.Trip;

import java.util.List;

/**
 *
 * Created by schieder on 4/26/16.
 */
public interface TripServiceApi {

    List<Trip> loadTrips();

}
