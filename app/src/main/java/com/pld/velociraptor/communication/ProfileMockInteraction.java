package com.pld.velociraptor.communication;

import com.pld.velociraptor.model.UserProfile;

import java.util.LinkedHashMap;
import java.util.Map;

import java.security.SecureRandom;
import java.math.BigInteger;

/**
 * Created by schieder on 4/26/16.
 */
public class ProfileMockInteraction implements ProfileInteraction {

    //mock is implemented as singleton (will become obsulete using DI)
    //TODO: Use DI
    private static ProfileMockInteraction singletonReference = null;

    //maps usermails (=ids) to passwords
    private Map<String, String> mockUserCredentials = new LinkedHashMap<>();

    //maps tokens to usermails ( = ids)
    private Map<String, String> mockTokens = new LinkedHashMap<>();

    private ProfileMockInteraction() {
        mockUserCredentials.put("maxou@velociraptor.fr", "42");
        mockUserCredentials.put("thomas@velociraptor.fr", "42");
        mockUserCredentials.put("kilian@velociraptor.fr", "42");
        mockUserCredentials.put("guillaume@velociraptor.fr", "42");
        mockUserCredentials.put("mathieu@velociraptor.fr", "42");
        mockUserCredentials.put("thibault@velociraptor.fr", "42");
    }

    //TODO: remove once DI is set up
    public static ProfileMockInteraction getInstance()
    {
        if(singletonReference == null)
            singletonReference = new ProfileMockInteraction();
        return singletonReference;
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

        //creating a mock profile...
        UserProfile mockProfile = new UserProfile(mockTokens.get(sessionToken), mockTokens.get(sessionToken).split("@")[0], 100, 420, 42);
        return mockProfile;
    }

    @Override
    public String logout(String sessionToken) {
        return null;
    }
}
