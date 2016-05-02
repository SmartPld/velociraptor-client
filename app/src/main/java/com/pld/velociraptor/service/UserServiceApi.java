package com.pld.velociraptor.service;

import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.tools.VeloCredentials;
import com.pld.velociraptor.tools.VeloTokenCredentials;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Cette interface décrit toutes les methodes concernant les données d'utilisateur
 *
 * Created by schieder on 4/26/16.
 */
public interface UserServiceApi {

    /**
     * Tells the server to invalidate the session key
     */

    @POST("/users/logout")
    public void logout(@Query("access_token") VeloCredentials veloCredentials);



    /**
     *
     */
    @POST("/users/login")
    public VeloTokenCredentials login(@Body VeloCredentials credentials);


    @POST("/users/{user}/accepttrajet/{trajet}")
    Trip acceptTrip(@Path("user") int idUser,
                    @Path("trajet") int idTrip,
                    @Query("access_token") String sessionToken,
                    @Body String dummy);

    @POST("/users/{user}/validetrajet")
    UserProfile terminateTrip(@Path("user") int idUser,
                              @Query("access_token") String sessionToken,
                              @Body String dummy);

    @GET("/users/{user}")
    UserProfile getUserProfile(@Path("user") int userId,
                               @Query("access_token") String accessToken);

}
