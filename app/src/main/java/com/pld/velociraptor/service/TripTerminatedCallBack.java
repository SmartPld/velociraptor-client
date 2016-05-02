package com.pld.velociraptor.service;

import com.pld.velociraptor.model.UserProfile;

/**
 * Created by Thibault on 01/05/2016.
 */
public interface TripTerminatedCallBack {
    void tripTerminated(UserProfile user);
}
