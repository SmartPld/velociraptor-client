package com.pld.velociraptor.model;

/**
 * Created by thomas on 28/04/2016.
 */
public class Trip {

    private final int idStationStart;
    private final int idStationEnd;
    private final int validiteStart;
    private final int validiteEnd;
    private final int maxNumber;
    private final int distance;
    private final int deltaElevation;
    private final int points;

    public Trip(int idStationStart, int idStationEnd, int validiteStart, int validiteEnd, int maxNumber, int distance, int deltaElevation, int points) {

        this.idStationStart = idStationStart;
        this.idStationEnd = idStationEnd;
        this.validiteStart = validiteStart;
        this.validiteEnd = validiteEnd;
        this.maxNumber = maxNumber;
        this.distance = distance;
        this.deltaElevation = deltaElevation;
        this.points = points;
    }

    public int getIdStationStart() {
        return idStationStart;
    }

    public int getIdStationEnd() {
        return idStationEnd;
    }

    public int getValiditeStart() {
        return validiteStart;
    }

    public int getValiditeEnd() {
        return validiteEnd;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int getDistance() {
        return distance;
    }

    public int getDeltaElevation() {
        return deltaElevation;
    }

    public int getPoints() {
        return points;
    }


}
