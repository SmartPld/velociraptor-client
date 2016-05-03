package com.pld.velociraptor.service;

import com.pld.velociraptor.model.UserProfile;
import com.pld.velociraptor.tools.UserWrapper;
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
    public UserWrapper getUserProfile(@Path("user") int userId,
                                      @Query("access_token") String accessToken) {
        if (mockVeloTokens.get(accessToken).getId() == userId) {
            UserProfile profile = mockVeloTokens.get(accessToken);
            UserWrapper mockWrapper = new UserWrapper(profile);
            return mockWrapper;
        } else
            return null; //the received session token does not correspond to the received user id.
    }

    /**
     * The second paramter is an empty string. We only need it due to an API bug which occurs if we do not pass a second argument (According to thibault)
     * @param sessionToken
     * @param dummyString
     * @return
     */
    @Override
    public String logout(String sessionToken, String dummyString) {
        for(VeloTokenCredentials cred : mockVeloTokens.keySet()) {
            if(cred.getId() == sessionToken) {
                mockVeloTokens.remove(cred);
                return "";
            }
        }
        //if the received token does not match any credentials, we have an uninscribel user -> this should never happen
        throw new RuntimeException("Unable to logout unknown user.");
    }

    @Override
    public VeloTokenCredentials login(@Body VeloCredentials credentials) {

        //generate an id for the mock Token Credentials
        String randomId = Integer.toString((int) Math.random() * 100);
        //generate random user id
        int randomUserId = (int) Math.random() * 100;
        VeloTokenCredentials mockTokenCredentials = new VeloTokenCredentials(randomId, 99999999, "foobar", randomUserId);
        UserProfile mockProfile = new UserProfile("mockEmail", "mockUserName", 42, 42, 42, 42);
        mockVeloTokens.put(mockTokenCredentials, mockProfile);

        return mockTokenCredentials;
    }

    @Override
    public UserWrapper acceptTrip(@Path("user") int idUser, @Path("trajet") int idTrip, @Query("access_token") String sessionToken, @Body String dummy) {
        return null;
    }

    @Override
    public UserProfile terminateTrip(@Path("user") int idUser, @Query("access_token") String sessionToken, @Body String dummy) {
        return null;
    }
}
