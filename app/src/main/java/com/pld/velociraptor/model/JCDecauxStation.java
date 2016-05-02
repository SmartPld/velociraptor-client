package com.pld.velociraptor.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Thibault on 02/05/2016.
 */
public class JCDecauxStation {
    private String number;
    private String name;
    private String open;
    private String address;
    private String address2;
    private String commune;
    private String nmarrond;
    private String bonus;
    private String pole;
    @SerializedName("gid") private String grid;
    private String altitude;
    private String id;
    private String available_bike_stands;
    private String available_bikes;
    private String lat;
    private String lng;


    public JCDecauxStation(String number, String name, String open, String address, String address2, String commune, String nmarrond, String bonus, String pole, String grid, String altitude, String id, String available_bike_stands, String available_bikes, String lat, String lng) {
        this.number = number;
        this.name = name;
        this.open = open;
        this.address = address;
        this.address2 = address2;
        this.commune = commune;
        this.nmarrond = nmarrond;
        this.bonus = bonus;
        this.pole = pole;
        this.grid = grid;
        this.altitude = altitude;
        this.id = id;
        this.available_bike_stands = available_bike_stands;
        this.available_bikes = available_bikes;
        this.lat = lat;
        this.lng = lng;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getNmarrond() {
        return nmarrond;
    }

    public void setNmarrond(String nmarrond) {
        this.nmarrond = nmarrond;
    }

    public String getBonus() {
        return bonus;
    }

    public void setBonus(String bonus) {
        this.bonus = bonus;
    }

    public String getPole() {
        return pole;
    }

    public void setPole(String pole) {
        this.pole = pole;
    }

    public String getGrid() {
        return grid;
    }

    public void setGrid(String grid) {
        this.grid = grid;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvailable_bike_stands() {
        return available_bike_stands;
    }

    public void setAvailable_bike_stands(String available_bike_stands) {
        this.available_bike_stands = available_bike_stands;
    }

    public String getAvailable_bikes() {
        return available_bikes;
    }

    public void setAvailable_bikes(String available_bikes) {
        this.available_bikes = available_bikes;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
