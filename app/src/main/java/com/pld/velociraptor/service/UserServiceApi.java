package com.pld.velociraptor.service;

import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.tools.UserWrapper;
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
 */
public interface UserServiceApi {


    /**
     *
     */
    @POST("/users/login")
    public VeloTokenCredentials login(@Body VeloCredentials credentials);


    @POST("/users/{user}/accepttrajet/{trajet}")
    UserWrapper acceptTrip(@Path("user") int idUser,
                    @Path("trajet") int idTrip,
                    @Query("access_token") String sessionToken,
                    @Body String dummy);

    @POST("/users/{user}/validetrajet")
    UserWrapper terminateTrip(@Path("user") int idUser,
                              @Query("access_token") String sessionToken,
                              @Body String dummy);

    @GET("/users/{user}")
    UserWrapper getUserProfile(@Path("user") int userId,
                               @Query("access_token") String accessToken);


    /**
     * The second parameter is an empty string. We only need it due to an API bug which occurs if we do not pass a second argument (According to thibault)
     * @param sessionToken
     * @param dummy
     * @return
     */
    @POST("/users/logout")
    String logout(@Query("access_token") String accessToken,  @Body String dummy);

}
