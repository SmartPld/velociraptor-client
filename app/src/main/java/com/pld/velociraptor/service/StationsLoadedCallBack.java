package com.pld.velociraptor.service;

import com.pld.velociraptor.model.Station;

import java.util.List;

/**
 * Created by Thibault on 01/05/2016.
 */
public interface StationsLoadedCallBack {

    void stationsLoaded(List<Station> stations);
}
