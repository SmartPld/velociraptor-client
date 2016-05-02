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
     * Retrieves a user profile (all profile data unified in an object) from the server using the session key
     * @param sessionToken
     * @return
     */
    @GET("/user")
    public UserProfile getUserProfile(@Query("token") String sessionToken);

    /**
     * Tells the server to invalidate the session key
     */

    @POST("/users/logout")
    public void logout(@Query("access_token") String sessionToken);



    /**
     * @return a String either representing a session token OR an error mesage telling why the received data is not valid;
     * (Error messages begin by the keyword "ERROR")
     */
    @POST("/users/login")
    public VeloTokenCredentials login(@Body VeloCredentials credentials);


    @POST("/users/{user}/accepttrajet/{trajet}")
    Trip acceptTrip(@Path("user") int idUser,
                    @Path("trajet") int idTrip,
                    @Body String dummy);

    @POST("/users/{user}/validetrajet")
    UserProfile terminateTrip(@Path("user") int idUser, @Body String dummy);

}
