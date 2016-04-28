package com.pld.velociraptor.service;

import com.pld.velociraptor.model.Trip;

import java.util.List;

/**
 * Created by thomas on 28/04/2016.
 */
public interface TripLoadedCallback {

    public void onTripsLoaded(List<Trip> trips);

}
