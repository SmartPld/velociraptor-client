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

    private Map<String, String> mockUserCredentials = new LinkedHashMap<>();
    private Map<String, String> mockTokens = new LinkedHashMap<>();


    @Inject
    public UserServiceMockImpl() {

        mockUserCredentials.put("Maxou", "42");
        mockUserCredentials.put("Thomas", "41");
        mockUserCredentials.put("Kilian", "40");
        mockUserCredentials.put("Guillaume", "39");
        mockUserCredentials.put("Mathieu", "38");
        mockUserCredentials.put("Thibault", "37");

    }

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

        UserProfile mockProfile = new UserProfile("mock@email.fr", mockTokens.get(sessionToken), 42, 42, 42);
        return mockProfile;
    }

    @Override
    public String logout(String sessionToken) {
        return null;
    }
}
