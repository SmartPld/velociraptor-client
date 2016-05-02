package com.pld.velociraptor.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by schieder on 4/26/16.
 */
public class UserProfile implements Parcelable{

    private final String email;
    private final String username;
    private final int tripsTotal;
    private final int distanceTotal;
    private final int points;
    private  int id;
    private Trip trajet;

    //optional bonus fields
    //private final double averageSpeed;
    //private final int placement;
    //...maybe even a photo

    public UserProfile(String email, String username, int tripsTotal, int distanceTotal, int points, int id) {
        this.email = email;
        this.username = username;
        this.tripsTotal = tripsTotal;
        this.distanceTotal = distanceTotal;
        this.points = points;
        this.id = id;
    }

    protected UserProfile(Parcel in) {
        email = in.readString();
        username = in.readString();
        tripsTotal = in.readInt();
        distanceTotal = in.readInt();
        points = in.readInt();
        id = in.readInt();
        trajet = in.readParcelable(Trip.class.getClassLoader());
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Trip getTrajet() {
        return trajet;
    }

    public void setTrajet(Trip currenTrip) {
        this.trajet = currenTrip;
    }

    public Boolean equals(Trip otherTrip){
        return otherTrip.getId() == this.id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(username);
        dest.writeInt(tripsTotal);
        dest.writeInt(distanceTotal);
        dest.writeInt(points);
        dest.writeInt(id);
        dest.writeParcelable(trajet, flags);
    }
}
