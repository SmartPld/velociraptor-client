package com.pld.velociraptor.service;

import com.pld.velociraptor.model.Trip;
import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.tools.VeloCredentials;
import com.pld.velociraptor.tools.VeloTokenCredentials;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by schieder on 4/26/16.
 */
@Singleton
public class UserServiceMockImpl implements UserServiceApi {

    //maps usermails (=ids) to passwords
    private Map<String, String> mockUserCredentials = new LinkedHashMap<>();

    @Inject
    public UserServiceMockImpl() {
        mockUserCredentials.put("admin", "password");
        mockUserCredentials.put("maxou@velociraptor.fr", "42");
        mockUserCredentials.put("thomas@velociraptor.fr", "42");
        mockUserCredentials.put("kilian@velociraptor.fr", "42");
        mockUserCredentials.put("guillaume@velociraptor.fr", "42");
        mockUserCredentials.put("mathieu@velociraptor.fr", "42");
        mockUserCredentials.put("thibault@velociraptor.fr", "42");

    }

    //maps tokens to usermails ( = ids)
    private Map<VeloTokenCredentials, UserProfile> mockVeloTokens = new LinkedHashMap<>();


    @GET("/users/{user}")
    public UserProfile getUserProfile(@Path("user") int userId,
                               @Query("access_token") String accessToken) {
        if (mockVeloTokens.get(accessToken).getId() == userId)
            return mockVeloTokens.get(accessToken);
        else
            return null; //the received session token does not correspond to the received user id.
    }

    @Override
    public void logout(VeloCredentials veloCredentials) {
        mockVeloTokens.remove(veloCredentials);
    }

    @Override
    public VeloTokenCredentials login(@Body VeloCredentials credentials) {

        //generate an id for the mock Token Credentials
        String randomId = Integer.toString((int)Math.random()*100);
        //generate random user id
        int randomUserId = (int)Math.random()*100;
        VeloTokenCredentials mockTokenCredentials = new VeloTokenCredentials(randomId, 99999999, "foobar", randomUserId);
        UserProfile mockProfile = new UserProfile("mockEmail", "mockUserName", 42, 42, 42, 42);
        mockVeloTokens.put(mockTokenCredentials, mockProfile);

        return mockTokenCredentials;
    }

    @Override
    public Trip acceptTrip(@Path("user") int idUser, @Path("trajet") int idTrip, @Query("access_token") String sessionToken, @Body String dummy) {
        return null;
    }

    @Override
    public UserProfile terminateTrip(@Path("user") int idUser, @Query("access_token") String sessionToken, @Body String dummy) {
        return null;
    }
}
