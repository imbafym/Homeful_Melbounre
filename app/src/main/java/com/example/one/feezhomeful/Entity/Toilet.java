package com.example.one.feezhomeful;

/**
 * Created by one on 1/09/2017.
 */

public class Toilet {
    private String Name;
    private String latitude;
    private String longitude;
    private String baby;

    public Toilet(){}

    public Toilet(String name, String latitude, String longitude, String baby) {
        Name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.baby = baby;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBaby() {
        return baby;
    }

    public void setBaby(String baby) {
        this.baby = baby;
    }
}
