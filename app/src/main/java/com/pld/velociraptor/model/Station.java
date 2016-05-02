package com.pld.velociraptor.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Thibault on 28/04/2016.
 */
public class Station implements Parcelable{
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
    private int available_bike_stands;
    private int available_bikes;


    public Station(int number, String name, boolean open, String address, String address2, String commune, int nmarrond, boolean bonus, String pole, Pos pos, int grid, int altitude, int id, int available_bike_stands, int available_bikes) {
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
        this.available_bike_stands = available_bike_stands;
        this.available_bikes = available_bikes;

    }


    protected Station(Parcel in) {
        number = in.readInt();
        name = in.readString();
        open = in.readByte() != 0;
        address = in.readString();
        address2 = in.readString();
        commune = in.readString();
        nmarrond = in.readInt();
        bonus = in.readByte() != 0;
        pole = in.readString();
        pos = in.readParcelable(Pos.class.getClassLoader());
        grid = in.readInt();
        altitude = in.readInt();
        id = in.readInt();
        available_bike_stands = in.readInt();
        available_bikes = in.readInt();

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(name);
        dest.writeByte((byte) (open ? 1 : 0));
        dest.writeString(address);
        dest.writeString(address2);
        dest.writeString(commune);
        dest.writeInt(nmarrond);
        dest.writeByte((byte) (bonus ? 1 : 0));
        dest.writeString(pole);
        dest.writeParcelable(pos, flags);
        dest.writeInt(grid);
        dest.writeInt(altitude);
        dest.writeInt(id);
        dest.writeInt(available_bike_stands);
        dest.writeInt(available_bikes);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Station> CREATOR = new Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel in) {
            return new Station(in);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };

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


    public int getAvailable_bike_stands() {
        return available_bike_stands;
    }

    public void setAvailable_bike_stands(int available_bike_stands) {
        this.available_bike_stands = available_bike_stands;
    }

    public int getAvailable_bikes() {
        return available_bikes;
    }

    public void setAvailable_bikes(int available_bikes) {
        this.available_bikes = available_bikes;
    }


}
