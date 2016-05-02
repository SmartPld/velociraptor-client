package com.pld.velociraptor.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by thomas on 28/04/2016.
 */
public class Trip implements Parcelable{

    private Station station_start;
    private Station station_end;
    @SerializedName("validite_start")
    private  Date validiteStart;
    @SerializedName("validite_end")
    private  Date validiteEnd;
    private  int max_number;
    private  int distance;
    private  int delta_elevation;
    private  int points;
    private  int id;

    public Trip(Station station_start, Station station_end, Date validiteStart, Date validiteEnd, int max_number, int distance, int delta_elevation, int points, int ide) {
        this.station_start = station_start;
        this.station_end = station_end;
        this.validiteStart = validiteStart;
        this.validiteEnd = validiteEnd;
        this.max_number = max_number;
        this.distance = distance;
        this.delta_elevation = delta_elevation;
        this.points = points;
        this.id = ide;
    }

    protected Trip(Parcel in) {
        station_start = in.readParcelable(Station.class.getClassLoader());
        station_end = in.readParcelable(Station.class.getClassLoader());
        max_number = in.readInt();
        distance = in.readInt();
        delta_elevation = in.readInt();
        points = in.readInt();
        id = in.readInt();
        validiteStart = new Date(in.readLong());
        validiteEnd = new Date(in.readLong());
    }

    public boolean equals(Trip trip) {
        return this.id == trip.id;
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public Station getStation_start() {
        return station_start;
    }

    public void setStation_start(Station station_start) {
        this.station_start = station_start;
    }

    public Station getStation_end() {
        return station_end;
    }

    public void setStation_end(Station station_end) {
        this.station_end = station_end;
    }

    public Date getValiditeStart() {
        return validiteStart;
    }

    public void setValiditeStart(Date validiteStart) {
        this.validiteStart = validiteStart;
    }

    public Date getValiditeEnd() {
        return validiteEnd;
    }

    public void setValiditeEnd(Date validiteEnd) {
        this.validiteEnd = validiteEnd;
    }

    public int getMax_number() {
        return max_number;
    }

    public void setMax_number(int max_number) {
        this.max_number = max_number;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDelta_elevation() {
        return delta_elevation;
    }

    public void setDelta_elevation(int delta_elevation) {
        this.delta_elevation = delta_elevation;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int ide) {
        this.id = ide;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(station_start, flags);
        dest.writeParcelable(station_end, flags);
        dest.writeInt(max_number);
        dest.writeInt(distance);
        dest.writeInt(delta_elevation);
        dest.writeInt(points);
        dest.writeInt(id);
        dest.writeLong(validiteStart.getTime());
        dest.writeLong(validiteEnd.getTime());
    }
}
