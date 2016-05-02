package com.pld.velociraptor.service;



import com.pld.velociraptor.model.UserProfile;

/**
 * Created by a607937 on 09/06/2015.
 */
public interface UserLoadedCallBack {

    public void onUserLoaded(UserProfile userProfile);

    public void onUserLoadError(Exception exception);

}
