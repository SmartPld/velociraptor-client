package com.pld.velociraptor.model;

/**
 * Created by schieder on 4/26/16.
 */
public class UserProfile {

    private final String email;
    private final String username;
    private final int tripsTotal;
    private final int distanceTotal;
    private final int points;

    //optional bonus fields
    //private final double averageSpeed;
    //private final int placement;
    //...maybe even a photo


    public UserProfile(String email, String username, int tripsTotal, int distanceTotal, int points) {
        this.email = email;
        this.username = username;
        this.tripsTotal = tripsTotal;
        this.distanceTotal = distanceTotal;
        this.points = points;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public int getTripsTotal() {
        return tripsTotal;
    }

    public int getDistanceTotal() {
        return distanceTotal;
    }

    public int getPoints() {
        return points;
    }
}
