package com.pld.velociraptor.tools;

/**
 * Created by Thibault on 29/04/2016.
 */
public class VeloCredentials {
    private String username;
    private String password;

    public VeloCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
