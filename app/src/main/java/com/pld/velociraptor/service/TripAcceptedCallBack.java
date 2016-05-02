package com.pld.velociraptor.service;

import com.pld.velociraptor.model.UserProfile;

/**
 * Created by Thibault on 01/05/2016.
 */
public interface TripAcceptedCallBack {
    void tripAcceptedError(Exception pendingException);
    void tripAccepted(UserProfile result);
}
