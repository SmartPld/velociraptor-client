package com.pld.velociraptor.service;

import com.pld.velociraptor.model.UserProfile;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by schieder on 4/26/16.
 */
@Singleton
public class UserServiceMockImpl implements UserServiceApi {

    //mock is implemented as singleton (will become obsulete using DI)

    //maps usermails (=ids) to passwords
    private Map<String, String> mockUserCredentials = new LinkedHashMap<>();

    @Inject
    public UserServiceMockImpl() {
        mockUserCredentials.put("maxou@velociraptor.fr", "42");
        mockUserCredentials.put("thomas@velociraptor.fr", "42");
        mockUserCredentials.put("kilian@velociraptor.fr", "42");
        mockUserCredentials.put("guillaume@velociraptor.fr", "42");
        mockUserCredentials.put("mathieu@velociraptor.fr", "42");
        mockUserCredentials.put("thibault@velociraptor.fr", "42");

    }

    //maps tokens to usermails ( = ids)
    private Map<String, String> mockTokens = new LinkedHashMap<>();


    @Override
    public String getUserToken(String usermail, String password) {
        if (usermail.isEmpty()) {
            return ("ERROR: User must be specified!");
        } else if (password.isEmpty()) {
            return ("ERROR: Password must not be empty!");
        } else if (mockUserCredentials.get(usermail) == null) {
            return ("ERROR: Invalid user");
        } else if (!mockUserCredentials.get(usermail).equals(password)) {
            return ("ERROR: Password incorrect");
        } else if (mockTokens.containsValue(usermail)) {
            //TODO: unsure if needed... to discuss with hexanome
            return ("ERROR: User already connected");
        }

        // login seems to be ok, generate random token and return it:
        SecureRandom random = new SecureRandom();
        String token = new BigInteger(130, random).toString(32);
        mockTokens.put(token, usermail);
        return token;
    }

    @Override
    public UserProfile getUserProfile(String sessionToken) {

        //return null if the session token is invalid
        if(!mockTokens.containsKey(sessionToken))
        {
            return null;
        }

        //creating a mock profile...
        UserProfile mockProfile = new UserProfile(mockTokens.get(sessionToken), mockTokens.get(sessionToken).split("@")[0], (int)(Math.random()*1000) , (int)(Math.random()*1000), (int)(Math.random()*1000));
        return mockProfile;
    }

    @Override
    public void logout(String sessionToken) {
        mockTokens.remove(sessionToken);
    }
}
