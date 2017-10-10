package com.example.one.feezhomeful;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import java.util.HashMap;

/**
 * Created by one on 25/08/2017.
 */

public class MyItem implements ClusterItem {
    private String name;
    private final LatLng mPosition;
    private String what;
    private int iconId;
    private String address_1;
    private String address_2;
    private String phone;
    private String who;
    private String website;
    private boolean openStatus;

    public boolean isOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(boolean openStatus) {
        this.openStatus = openStatus;
    }

    private String suburb;

    private HashMap<String,String> timetable;


    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }



    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }


    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public HashMap<String, String> getTimetable() {
        return timetable;
    }

    public void setTimetable(HashMap<String, String> timetable) {
        this.timetable = timetable;
    }

    public MyItem(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}

