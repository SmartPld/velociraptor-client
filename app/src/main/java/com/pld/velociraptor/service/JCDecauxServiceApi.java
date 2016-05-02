package com.pld.velociraptor.service;

import com.pld.velociraptor.model.JCDecauxResponse;

import retrofit.http.GET;

/**
 * Created by Thibault on 01/05/2016.
 */
public interface JCDecauxServiceApi {

    @GET("/ws/rdata/jcd_jcdecaux.jcdvelov/all.json")
    JCDecauxResponse getStations();

}
