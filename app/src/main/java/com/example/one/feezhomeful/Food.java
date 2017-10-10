package com.example.one.feezhomeful;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class Food implements Parcelable {


    //    private Map<String, String> time;
    private String address_1;
    private String address_2;
    private String latitude;
    private String longitude;
    private String what;
    private String phone;
    private String name;
    private String who;
    private String website;




    private String suburb;

    private HashMap<String,String> timetable;

    public Food(){

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

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public HashMap<String, String> getTimetable() {
        return timetable;
    }

    public void setTimetable(HashMap<String, String> timetable) {
        this.timetable = timetable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public static Creator<Food> getCREATOR() {
        return CREATOR;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }



    protected Food(Parcel in) {
        address_1 = in.readString();
        address_2 = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        what = in.readString();
        phone = in.readString();
        name = in.readString();
        who = in.readString();
        website = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address_1);
        dest.writeString(address_2);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(what);
        dest.writeString(phone);
        dest.writeString(name);
        dest.writeString(who);
        dest.writeString(website);
    }


    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }
}
