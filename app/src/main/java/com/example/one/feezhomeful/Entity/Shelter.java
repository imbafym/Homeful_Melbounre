package com.example.one.feezhomeful;

/**
 * Created by one on 30/08/2017.
 */

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;



/**
 * Created by one on 12/08/2017.
 */

public class Shelter implements Parcelable {
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
    private HashMap<String, String> timeTable;
    private String sex;
    private String child;


//
//    public Map<String, String> getTime() {
//        return time;
//    }
//
//    public void setTime(Map<String, String> time) {
//        this.time = time;
//    }

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

    public  String getLatitude() {
        return latitude;
    }

    public  void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public  String getLongitude() {
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

    public static Creator<Shelter> getCREATOR() {
        return CREATOR;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public HashMap<String, String> getTimetable() {
        return timeTable;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getChild() {
        return child;
    }

    public void setChild(String child) {
        this.child = child;
    }

    public void setTimetable(HashMap<String, String> timeTable) {
        this.timeTable = timeTable;
    }
    public Shelter() {

    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    protected Shelter(Parcel in) {

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

    public static final Creator<Shelter> CREATOR = new Creator<Shelter>() {
        @Override
        public Shelter createFromParcel(Parcel in) {
            return new Shelter(in);
        }

        @Override
        public Shelter[] newArray(int size) {
            return new Shelter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}