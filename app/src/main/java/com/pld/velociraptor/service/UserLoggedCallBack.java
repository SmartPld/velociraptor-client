package com.pld.velociraptor.service;

import com.pld.velociraptor.tools.VeloTokenCredentials;

/**
 * Created by Thibault on 29/04/2016.
 */
public interface UserLoggedCallBack {

    void userLogged(VeloTokenCredentials response);

    void loginError(Exception exception);
}
