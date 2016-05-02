package com.pld.velociraptor.tools;

import com.pld.velociraptor.model.UserProfile;

/**
 * Created by Thibault on 02/05/2016.
 */
public class UserWrapper {

    private UserProfile utilisateur;

    public UserWrapper(UserProfile utilisateur) {
        this.utilisateur = utilisateur;
    }

    public UserProfile getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(UserProfile utilisateur) {
        this.utilisateur = utilisateur;
    }
}
