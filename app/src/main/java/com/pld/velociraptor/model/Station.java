package com.pld.velociraptor.model;

/**
 * Created by Thibault on 28/04/2016.
 */
public class Station {
    private int number;
    private String name;
    private boolean open;
    private String address;
    private String address2;
    private String commune;
    private int nmarrond;
    private boolean bonus;
    private String pole;
    private Pos pos;
    private int grid;
    private int altitude;
    private int id;

    public Station(int number, String name, boolean open, String address, String address2, String commune, int nmarrond, boolean bonus, String pole, Pos pos, int grid, int altitude, int id) {
        this.number = number;
        this.name = name;
        this.open = open;
        this.address = address;
        this.address2 = address2;
        this.commune = commune;
        this.nmarrond = nmarrond;
        this.bonus = bonus;
        this.pole = pole;
        this.pos = pos;
        this.grid = grid;
        this.altitude = altitude;
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
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

    public int getNmarrond() {
        return nmarrond;
    }

    public void setNmarrond(int nmarrond) {
        this.nmarrond = nmarrond;
    }

    public boolean isBonus() {
        return bonus;
    }

    public void setBonus(boolean bonus) {
        this.bonus = bonus;
    }

    public String getPole() {
        return pole;
    }

    public void setPole(String pole) {
        this.pole = pole;
    }

    public Pos getPos() {
        return pos;
    }

    public void setPos(Pos pos) {
        this.pos = pos;
    }

    public int getGrid() {
        return grid;
    }

    public void setGrid(int grid) {
        this.grid = grid;
    }

    public int getAltitude() {
        return altitude;
    }

    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public class Pos {
        private double lat;
        private double lng;

        public Pos(double lat, double lng) {
            this.lat = lat;
            this.lng = lng;
        }

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
    }





}
