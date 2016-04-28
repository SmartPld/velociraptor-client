package com.pld.velociraptor.model;

import java.util.Date;

/**
 * Created by thomas on 28/04/2016.
 */
public class Trip {

    private Station station_start;
    private Station station_end;
    private  Date validiteStart;
    private  Date validiteEnd;
    private  int max_number;
    private  int distance;
    private  int delta_elevation;
    private  int points;
    private  int ide;

    public Trip(Station station_start, Station station_end, Date validiteStart, Date validiteEnd, int max_number, int distance, int delta_elevation, int points, int ide) {
        this.station_start = station_start;
        this.station_end = station_end;
        this.validiteStart = validiteStart;
        this.validiteEnd = validiteEnd;
        this.max_number = max_number;
        this.distance = distance;
        this.delta_elevation = delta_elevation;
        this.points = points;
        this.ide = ide;
    }

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

    public int getIde() {
        return ide;
    }

    public void setIde(int ide) {
        this.ide = ide;
    }
}
