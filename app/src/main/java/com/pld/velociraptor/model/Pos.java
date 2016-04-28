package com.pld.velociraptor.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pos implements Parcelable {
    private double lat;
    private double lng;

    public Pos(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    protected Pos(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<Pos> CREATOR = new Creator<Pos>() {
        @Override
        public Pos createFromParcel(Parcel in) {
            return new Pos(in);
        }

        @Override
        public Pos[] newArray(int size) {
            return new Pos[size];
        }
    };

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }
}