package com.example.one.feezhomeful;

/**
 * Created by one on 30/08/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by one on 27/08/2017.
 */

public class LocationRepo {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private static  final  String CREATE_TABLE_SHELTER_FEMALE="CREATE TABLE "+ ShelterDB.TABLE_FEMALE+"("
            +ShelterDB.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +ShelterDB.KEY_address1+" TEXT, "
            +ShelterDB.KEY_address2+" TEXT, "
            +ShelterDB.KEY_latitude+" TEXT,"
            +ShelterDB.KEY_longitude+" TEXT, "
            +ShelterDB.KEY_what+" TEXT, "
            +ShelterDB.KEY_phone+" TEXT,"
            +ShelterDB.KEY_name+" TEXT,"
            +ShelterDB.KEY_who+" TEXT, "
            +ShelterDB.KEY_website+" TEXT, "
            +ShelterDB.KEY_suburb+" TEXT, "
            +ShelterDB.KEY_monday+" TEXT,"
            +ShelterDB.KEY_tuesday+" TEXT,"
            +ShelterDB.KEY_wednesday+" TEXT, "
            +ShelterDB.KEY_thursday+" TEXT,"
            +ShelterDB.KEY_friday+" TEXT,"
            +ShelterDB.KEY_saturday+" TEXT, "
            +ShelterDB.KEY_sunday+" TEXT)" +
            "";

    private static  final  String CREATE_TABLE_SHELTER_MALE="CREATE TABLE "+ ShelterDB.TABLE_MALE+"("
            +ShelterDB.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +ShelterDB.KEY_address1+" TEXT, "
            +ShelterDB.KEY_address2+" TEXT, "
            +ShelterDB.KEY_latitude+" TEXT,"
            +ShelterDB.KEY_longitude+" TEXT, "
            +ShelterDB.KEY_what+" TEXT, "
            +ShelterDB.KEY_phone+" TEXT,"
            +ShelterDB.KEY_name+" TEXT,"
            +ShelterDB.KEY_who+" TEXT, "
            +ShelterDB.KEY_website+" TEXT, "
            +ShelterDB.KEY_suburb+" TEXT, "
            +ShelterDB.KEY_monday+" TEXT,"
            +ShelterDB.KEY_tuesday+" TEXT,"
            +ShelterDB.KEY_wednesday+" TEXT, "
            +ShelterDB.KEY_thursday+" TEXT,"
            +ShelterDB.KEY_friday+" TEXT,"
            +ShelterDB.KEY_saturday+" TEXT, "
            +ShelterDB.KEY_sunday+" TEXT)" +
            "";


    private static  final String CREATE_TABLE_FOOD="CREATE TABLE "+ FoodDB.TABLE+"("
            +FoodDB.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +FoodDB.KEY_address1+" TEXT, "
            +FoodDB.KEY_address2+" TEXT, "
            +FoodDB.KEY_latitude+" TEXT,"
            +FoodDB.KEY_longitude+" TEXT, "
            +FoodDB.KEY_what+" TEXT, "
            +FoodDB.KEY_phone+" TEXT,"
            +FoodDB.KEY_name+" TEXT,"
            +FoodDB.KEY_who+" TEXT, "
            +FoodDB.KEY_website+" TEXT, "
            +FoodDB.KEY_suburb+" TEXT, "
            +FoodDB.KEY_monday+" TEXT,"
            +FoodDB.KEY_tuesday+" TEXT,"
            +FoodDB.KEY_wednesday+" TEXT, "
            +FoodDB.KEY_thursday+" TEXT,"
            +FoodDB.KEY_friday+" TEXT,"
            +FoodDB.KEY_saturday+" TEXT, "
            +FoodDB.KEY_sunday+" TEXT)" +
            "";

    private static  final String CREATE_TABLE_FOOD_ONLINEDB="CREATE TABLE "+ FoodDB.TABLE_OnlineDB+"("
            +FoodDB.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +FoodDB.KEY_address1+" TEXT, "
            +FoodDB.KEY_address2+" TEXT, "
            +FoodDB.KEY_latitude+" TEXT,"
            +FoodDB.KEY_longitude+" TEXT, "
            +FoodDB.KEY_what+" TEXT, "
            +FoodDB.KEY_phone+" TEXT,"
            +FoodDB.KEY_name+" TEXT,"
            +FoodDB.KEY_who+" TEXT, "
            +FoodDB.KEY_website+" TEXT, "
            +FoodDB.KEY_suburb+" TEXT, "
            +FoodDB.KEY_monday+" TEXT,"
            +FoodDB.KEY_tuesday+" TEXT,"
            +FoodDB.KEY_wednesday+" TEXT, "
            +FoodDB.KEY_thursday+" TEXT,"
            +FoodDB.KEY_friday+" TEXT,"
            +FoodDB.KEY_saturday+" TEXT, "
            +FoodDB.KEY_sunday+" TEXT)" +
            "";
    private static  final String CREATE_TABLE_TOILET_FEMALE="CREATE TABLE "+ ToiletDB.TABLE_FEMALE+"("
            +ToiletDB.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +ToiletDB.KEY_name+" TEXT, "
            +ToiletDB.KEY_baby+" TEXT, "
            +ToiletDB.KEY_latitude+" TEXT,"
            +ToiletDB.KEY_longitude+" TEXT)" +
            "";

    private static  final String CREATE_TABLE_TOILET_MALE="CREATE TABLE "+ ToiletDB.TABLE_MALE+"("
            +ToiletDB.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +ToiletDB.KEY_name+" TEXT, "
            +ToiletDB.KEY_baby+" TEXT, "
            +ToiletDB.KEY_latitude+" TEXT,"
            +ToiletDB.KEY_longitude+" TEXT)" +
            "";

    private static  final String CREATE_TABLE_WATER="CREATE TABLE "+ WaterDB.TABLE+"("
            +WaterDB.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
            +WaterDB.KEY_name+" TEXT, "
            +WaterDB.KEY_latitude+" TEXT,"
            +WaterDB.KEY_longitude+" TEXT)" +
            "";
    public LocationRepo(Context context) {
        dbHelper = new DBHelper(context);
    }

    public int insertFemaleShelter(ShelterDB shelterDb, Shelter shelter) {

        //打开连接，写入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ShelterDB.KEY_address1, shelter.getAddress_1());
        values.put(ShelterDB.KEY_address2, shelter.getAddress_2());
        values.put(ShelterDB.KEY_latitude, shelter.getLatitude());
        values.put(ShelterDB.KEY_longitude, shelter.getLongitude());
        values.put(ShelterDB.KEY_what, shelter.getWhat());
        values.put(ShelterDB.KEY_phone, shelter.getPhone());
        values.put(ShelterDB.KEY_name, shelter.getName());
        values.put(ShelterDB.KEY_who, shelter.getWho());
        values.put(ShelterDB.KEY_website, shelter.getWebsite());
        values.put(ShelterDB.KEY_suburb, shelter.getSuburb());
        values.put(ShelterDB.KEY_monday, "N/A");
        values.put(ShelterDB.KEY_tuesday, "N/A");
        values.put(ShelterDB.KEY_wednesday, "N/A");
        values.put(ShelterDB.KEY_thursday, "N/A");
        values.put(ShelterDB.KEY_friday, "N/A");
        values.put(ShelterDB.KEY_saturday, "N/A");
        values.put(ShelterDB.KEY_sunday, "N/A");


//        values.put(ShelterDB.KEY_monday, shelter.getTimetable().get("monday"));
//        values.put(ShelterDB.KEY_tuesday, shelter.getTimetable().get("tuesday"));
//        values.put(ShelterDB.KEY_wednesday, shelter.getTimetable().get("wednesday"));
//        values.put(ShelterDB.KEY_thursday, shelter.getTimetable().get("thursday"));
//        values.put(ShelterDB.KEY_friday, shelter.getTimetable().get("friday"));
//        values.put(ShelterDB.KEY_saturday, shelter.getTimetable().get("saturday"));
//        values.put(ShelterDB.KEY_sunday, shelter.getTimetable().get("sunday"));
        //
        long shelter_id = db.insert(ShelterDB.TABLE_FEMALE, null, values);
        db.close();
        return (int) shelter_id;
    }

    public int insertMaleShelter(ShelterDB shelterDb, Shelter shelter) {

        //打开连接，写入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ShelterDB.KEY_address1, shelter.getAddress_1());
        values.put(ShelterDB.KEY_address2, shelter.getAddress_2());
        values.put(ShelterDB.KEY_latitude, shelter.getLatitude());
        values.put(ShelterDB.KEY_longitude, shelter.getLongitude());
        values.put(ShelterDB.KEY_what, shelter.getWhat());
        values.put(ShelterDB.KEY_phone, shelter.getPhone());
        values.put(ShelterDB.KEY_name, shelter.getName());
        values.put(ShelterDB.KEY_who, shelter.getWho());
        values.put(ShelterDB.KEY_website, shelter.getWebsite());
        values.put(ShelterDB.KEY_suburb, shelter.getSuburb());
        values.put(ShelterDB.KEY_monday, "N/A");
        values.put(ShelterDB.KEY_tuesday, "N/A");
        values.put(ShelterDB.KEY_wednesday, "N/A");
        values.put(ShelterDB.KEY_thursday, "N/A");
        values.put(ShelterDB.KEY_friday, "N/A");
        values.put(ShelterDB.KEY_saturday, "N/A");
        values.put(ShelterDB.KEY_sunday, "N/A");



        long shelter_id = db.insert(ShelterDB.TABLE_MALE, null, values);
        db.close();
        return (int) shelter_id;
    }

    public int insertFemaleToilet( Toilet toilet) {
        //打开连接，写入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ToiletDB.KEY_name, toilet.getName());
        values.put(ToiletDB.KEY_latitude, toilet.getLatitude());
        values.put(ToiletDB.KEY_longitude, toilet.getLongitude());
        values.put(ToiletDB.KEY_baby, toilet.getBaby());
//        values.put(ShelterDB.KEY_monday, shelter.getTimetable().get("monday"));
//        values.put(ShelterDB.KEY_tuesday, shelter.getTimetable().get("tuesday"));
//        values.put(ShelterDB.KEY_wednesday, shelter.getTimetable().get("wednesday"));
//        values.put(ShelterDB.KEY_thursday, shelter.getTimetable().get("thursday"));
//        values.put(ShelterDB.KEY_friday, shelter.getTimetable().get("friday"));
//        values.put(ShelterDB.KEY_saturday, shelter.getTimetable().get("saturday"));
//        values.put(ShelterDB.KEY_sunday, shelter.getTimetable().get("sunday"));
        //
        long toilet_id = db.insert(ToiletDB.TABLE_FEMALE, null, values);
        db.close();
        return (int) toilet_id;
    }

    public int insertMaleToilet( Toilet toilet) {
        //打开连接，写入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ToiletDB.KEY_name, toilet.getName());
        values.put(ToiletDB.KEY_latitude, toilet.getLatitude());
        values.put(ToiletDB.KEY_longitude, toilet.getLongitude());
        values.put(ToiletDB.KEY_baby, toilet.getBaby());

        long toilet_id = db.insert(ToiletDB.TABLE_MALE, null, values);
        db.close();
        return (int) toilet_id;
    }

    public int insertWater( Water water) {
        //打开连接，写入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(WaterDB.KEY_name, water.getName());
        values.put(WaterDB.KEY_latitude, water.getLatitude());
        values.put(WaterDB.KEY_longitude, water.getLongitude());

//        values.put(ShelterDB.KEY_monday, shelter.getTimetable().get("monday"));
//        values.put(ShelterDB.KEY_tuesday, shelter.getTimetable().get("tuesday"));
//        values.put(ShelterDB.KEY_wednesday, shelter.getTimetable().get("wednesday"));
//        values.put(ShelterDB.KEY_thursday, shelter.getTimetable().get("thursday"));
//        values.put(ShelterDB.KEY_friday, shelter.getTimetable().get("friday"));
//        values.put(ShelterDB.KEY_saturday, shelter.getTimetable().get("saturday"));
//        values.put(ShelterDB.KEY_sunday, shelter.getTimetable().get("sunday"));
        //
        long water_id = db.insert(WaterDB.TABLE, null, values);
        db.close();
        return (int) water_id;
    }

    public int insertFood(FoodDB foodDB, Food food) {
        //打开连接，写入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodDB.KEY_address1, food.getAddress_1());
        values.put(FoodDB.KEY_address2, food.getAddress_2());
        values.put(FoodDB.KEY_latitude, food.getLatitude());
        values.put(FoodDB.KEY_longitude, food.getLongitude());
        values.put(FoodDB.KEY_what, food.getWhat());
        values.put(FoodDB.KEY_phone, food.getPhone());
        values.put(FoodDB.KEY_name, food.getName());
        values.put(FoodDB.KEY_who, food.getWho());
        values.put(FoodDB.KEY_website, food.getWebsite());
        values.put(FoodDB.KEY_suburb, food.getSuburb());
        values.put(FoodDB.KEY_monday, food.getTimetable().get("monday"));
        values.put(FoodDB.KEY_tuesday, food.getTimetable().get("tuesday"));
        values.put(FoodDB.KEY_wednesday, food.getTimetable().get("wednesday"));
        values.put(FoodDB.KEY_thursday, food.getTimetable().get("thursday"));
        values.put(FoodDB.KEY_friday, food.getTimetable().get("friday"));
        values.put(FoodDB.KEY_saturday, food.getTimetable().get("saturday"));
        values.put(FoodDB.KEY_sunday,food.getTimetable().get("sunday"));

        long food_id = db.insert(FoodDB.TABLE, null, values);
        db.close();
        return (int) food_id;
    }

    public int insertFoodToOnlineDB(FoodDB foodDB, Food food) {
        //打开连接，写入数据
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FoodDB.KEY_address1, food.getAddress_1());
        values.put(FoodDB.KEY_address2, food.getAddress_2());
        values.put(FoodDB.KEY_latitude, food.getLatitude());
        values.put(FoodDB.KEY_longitude, food.getLongitude());
        values.put(FoodDB.KEY_what, food.getWhat());
        values.put(FoodDB.KEY_phone, food.getPhone());
        values.put(FoodDB.KEY_name, food.getName());
        values.put(FoodDB.KEY_who, food.getWho());
        values.put(FoodDB.KEY_website, food.getWebsite());
        values.put(FoodDB.KEY_suburb, food.getSuburb());
        values.put(FoodDB.KEY_monday, food.getTimetable().get("Monday"));
        values.put(FoodDB.KEY_tuesday, food.getTimetable().get("Tuesday"));
        values.put(FoodDB.KEY_wednesday, food.getTimetable().get("Wednesday"));
        values.put(FoodDB.KEY_thursday, food.getTimetable().get("Thursday"));
        values.put(FoodDB.KEY_friday, food.getTimetable().get("Friday"));
        values.put(FoodDB.KEY_saturday, food.getTimetable().get("Saturday"));
        values.put(FoodDB.KEY_sunday,food.getTimetable().get("Sunday"));

        long food_id = db.insert(FoodDB.TABLE_OnlineDB, null, values);
        db.close();
        return (int) food_id;
    }

    public LocationRepo open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }
    public void close() {
        dbHelper.close();
    }
    public void initialFemaleShelterTable() {
        db.execSQL("DROP TABLE IF EXISTS "+ ShelterDB.TABLE_FEMALE);
        db.execSQL(CREATE_TABLE_SHELTER_FEMALE);
    }
    public void initialMaleShelterTable() {
        db.execSQL("DROP TABLE IF EXISTS "+ ShelterDB.TABLE_MALE);
        db.execSQL(CREATE_TABLE_SHELTER_MALE);
    }
    public void initialFoodTable() {
        db.execSQL("DROP TABLE IF EXISTS "+ FoodDB.TABLE);
        db.execSQL(CREATE_TABLE_FOOD);
    }
    public void initialFoodOnlineDBTable() {
        db.execSQL("DROP TABLE IF EXISTS "+ FoodDB.TABLE_OnlineDB);
        db.execSQL(CREATE_TABLE_FOOD_ONLINEDB);
    }

    public void initialWaterTable() {
        db.execSQL("DROP TABLE IF EXISTS "+ WaterDB.TABLE);
        db.execSQL(CREATE_TABLE_WATER);
    }
    public void initialFemaleToiletTable() {
        db.execSQL("DROP TABLE IF EXISTS "+ ToiletDB.TABLE_FEMALE);
        db.execSQL(CREATE_TABLE_TOILET_FEMALE);
    }
    public void initialMaleToiletTable() {
        db.execSQL("DROP TABLE IF EXISTS "+ ToiletDB.TABLE_MALE);
        db.execSQL(CREATE_TABLE_TOILET_MALE);
    }
//    public void deleteShelter(int shelter_id) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        db.delete(ShelterDB.TABLE_FEMALE, ShelterDB.KEY_ID + "=?", new String[]{String.valueOf(shelter_id)});
//        db.close();
//    }

    public ArrayList<Food> getFoodList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Food> foods = new ArrayList<>();
        Cursor cur = db.query(FoodDB.TABLE, null, null, null, null, null, null);
        if (cur.moveToFirst()) {
            do {
                Food food = new Food();
                HashMap<String, String> timeTable = new HashMap<>();
                food.setAddress_1(cur.getString(cur.getColumnIndex(FoodDB.KEY_address1)));
                food.setAddress_2(cur.getString(cur.getColumnIndex(FoodDB.KEY_address2)));
                food.setLatitude(cur.getString(cur.getColumnIndex(FoodDB.KEY_latitude)));
                food.setLongitude(cur.getString(cur.getColumnIndex(FoodDB.KEY_longitude)));
                food.setWhat(cur.getString(cur.getColumnIndex(FoodDB.KEY_what)));
                food.setPhone(cur.getString(cur.getColumnIndex(FoodDB.KEY_phone)));
                food.setName(cur.getString(cur.getColumnIndex(FoodDB.KEY_name)));
                food.setWho(cur.getString(cur.getColumnIndex(FoodDB.KEY_who)));
                food.setWebsite(cur.getString(cur.getColumnIndex(FoodDB.KEY_website)));
                food.setSuburb(cur.getString(cur.getColumnIndex(FoodDB.KEY_suburb)));
                timeTable.put("monday",cur.getString(cur.getColumnIndex(FoodDB.KEY_monday)));
                timeTable.put("tuesday",cur.getString(cur.getColumnIndex(FoodDB.KEY_tuesday)));
                timeTable.put("wednesday",cur.getString(cur.getColumnIndex(FoodDB.KEY_wednesday)));
                timeTable.put("thursday",cur.getString(cur.getColumnIndex(FoodDB.KEY_thursday)));
                timeTable.put("friday",cur.getString(cur.getColumnIndex(FoodDB.KEY_friday)));
                timeTable.put("saturday",cur.getString(cur.getColumnIndex(FoodDB.KEY_saturday)));
                timeTable.put("sunday",cur.getString(cur.getColumnIndex(FoodDB.KEY_sunday)));
                food.setTimetable(timeTable);
                foods.add(food);
            } while (cur.moveToNext());
        }
        cur.close();
        db.close();
        return foods;
    }

    public ArrayList<Food> getFoodOnlineDBList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Food> foods = new ArrayList<>();
        Cursor cur = db.query(FoodDB.TABLE_OnlineDB, null, null, null, null, null, null);
        if (cur.moveToFirst()) {
            do {
                Food food = new Food();
                HashMap<String, String> timeTable = new HashMap<>();
                food.setAddress_1(cur.getString(cur.getColumnIndex(FoodDB.KEY_address1)));
                food.setAddress_2(cur.getString(cur.getColumnIndex(FoodDB.KEY_address2)));
                food.setLatitude(cur.getString(cur.getColumnIndex(FoodDB.KEY_latitude)));
                food.setLongitude(cur.getString(cur.getColumnIndex(FoodDB.KEY_longitude)));
                food.setWhat(cur.getString(cur.getColumnIndex(FoodDB.KEY_what)));
                food.setPhone(cur.getString(cur.getColumnIndex(FoodDB.KEY_phone)));
                food.setName(cur.getString(cur.getColumnIndex(FoodDB.KEY_name)));
                food.setWho(cur.getString(cur.getColumnIndex(FoodDB.KEY_who)));
                food.setWebsite(cur.getString(cur.getColumnIndex(FoodDB.KEY_website)));
                food.setSuburb(cur.getString(cur.getColumnIndex(FoodDB.KEY_suburb)));
                timeTable.put("Monday",cur.getString(cur.getColumnIndex(FoodDB.KEY_monday)));
                timeTable.put("Tuesday",cur.getString(cur.getColumnIndex(FoodDB.KEY_tuesday)));
                timeTable.put("Wednesday",cur.getString(cur.getColumnIndex(FoodDB.KEY_wednesday)));
                timeTable.put("Thursday",cur.getString(cur.getColumnIndex(FoodDB.KEY_thursday)));
                timeTable.put("Friday",cur.getString(cur.getColumnIndex(FoodDB.KEY_friday)));
                timeTable.put("Saturday",cur.getString(cur.getColumnIndex(FoodDB.KEY_saturday)));
                timeTable.put("Sunday",cur.getString(cur.getColumnIndex(FoodDB.KEY_sunday)));
                food.setTimetable(timeTable);
                foods.add(food);
            } while (cur.moveToNext());
        }
        cur.close();
        db.close();
        return foods;
    }


    public ArrayList<Toilet> getMaleToiletList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Toilet> toilets = new ArrayList<>();
        Cursor cur = db.query(ToiletDB.TABLE_MALE, null, null, null, null, null, null);
        if (cur.moveToFirst()) {
            do {
                Toilet toilet = new Toilet();
                HashMap<String, String> timeTable = new HashMap<>();
                toilet.setName(cur.getString(cur.getColumnIndex(ToiletDB.KEY_name)));
                toilet.setLatitude(cur.getString(cur.getColumnIndex(ToiletDB.KEY_latitude)));
                toilet.setLongitude(cur.getString(cur.getColumnIndex(ToiletDB.KEY_longitude)));
                toilet.setBaby(cur.getString(cur.getColumnIndex(ToiletDB.KEY_baby)));
                toilets.add(toilet);
            } while (cur.moveToNext());
        }
        cur.close();
        db.close();
        return toilets;
    }

    public ArrayList<Toilet> getFemaleToiletList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Toilet> toilets = new ArrayList<>();
        Cursor cur = db.query(ToiletDB.TABLE_FEMALE, null, null, null, null, null, null);
        if (cur.moveToFirst()) {
            do {
                Toilet toilet = new Toilet();
                HashMap<String, String> timeTable = new HashMap<>();
                toilet.setName(cur.getString(cur.getColumnIndex(ToiletDB.KEY_name)));
                toilet.setLatitude(cur.getString(cur.getColumnIndex(ToiletDB.KEY_latitude)));
                toilet.setLongitude(cur.getString(cur.getColumnIndex(ToiletDB.KEY_longitude)));
                toilet.setBaby(cur.getString(cur.getColumnIndex(ToiletDB.KEY_baby)));
                toilets.add(toilet);
            } while (cur.moveToNext());
        }
        cur.close();
        db.close();
        return toilets;
    }

    public ArrayList<Water> getWaterList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Water> waters = new ArrayList<>();
        Cursor cur = db.query(WaterDB.TABLE, null, null, null, null, null, null);
        if (cur.moveToFirst()) {
            do {
                Water water = new Water();
                HashMap<String, String> timeTable = new HashMap<>();
                water.setName(cur.getString(cur.getColumnIndex(WaterDB.KEY_name)));
                water.setLatitude(cur.getString(cur.getColumnIndex(WaterDB.KEY_latitude)));
                water.setLongitude(cur.getString(cur.getColumnIndex(WaterDB.KEY_longitude)));

                waters.add(water);
            } while (cur.moveToNext());
        }
        cur.close();
        db.close();
        return waters;
    }


    public ArrayList<Shelter> getMaleShelterList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Shelter> shelters = new ArrayList<>();
        Cursor cur = db.query(ShelterDB.TABLE_MALE, null, null, null, null, null, null);
        if (cur.moveToFirst()) {
            do {
                Shelter shelter = new Shelter();
                HashMap<String, String> timeTable = new HashMap<>();
                shelter.setAddress_1(cur.getString(cur.getColumnIndex(ShelterDB.KEY_address1)));
                shelter.setAddress_2(cur.getString(cur.getColumnIndex(ShelterDB.KEY_address2)));
                shelter.setLatitude(cur.getString(cur.getColumnIndex(ShelterDB.KEY_latitude)));
                shelter.setLongitude(cur.getString(cur.getColumnIndex(ShelterDB.KEY_longitude)));
                shelter.setWhat(cur.getString(cur.getColumnIndex(ShelterDB.KEY_what)));
                shelter.setPhone(cur.getString(cur.getColumnIndex(ShelterDB.KEY_phone)));
                shelter.setName(cur.getString(cur.getColumnIndex(ShelterDB.KEY_name)));
                shelter.setWho(cur.getString(cur.getColumnIndex(ShelterDB.KEY_who)));
                shelter.setWebsite(cur.getString(cur.getColumnIndex(ShelterDB.KEY_website)));
                shelter.setSuburb(cur.getString(cur.getColumnIndex(ShelterDB.KEY_suburb)));
                timeTable.put("monday",cur.getString(cur.getColumnIndex(ShelterDB.KEY_monday)));
                timeTable.put("tuesday",cur.getString(cur.getColumnIndex(ShelterDB.KEY_tuesday)));
                timeTable.put("wednesday",cur.getString(cur.getColumnIndex(ShelterDB.KEY_wednesday)));
                timeTable.put("thursday",cur.getString(cur.getColumnIndex(ShelterDB.KEY_thursday)));
                timeTable.put("friday",cur.getString(cur.getColumnIndex(ShelterDB.KEY_friday)));
                timeTable.put("saturday",cur.getString(cur.getColumnIndex(ShelterDB.KEY_saturday)));
                timeTable.put("sunday",cur.getString(cur.getColumnIndex(ShelterDB.KEY_sunday)));

                shelter.setTimetable(timeTable);
                shelters.add(shelter);
            } while (cur.moveToNext());
        }
        cur.close();
        db.close();
        return shelters;
    }

    public ArrayList<Shelter> getFemaleShelterList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Shelter> shelters = new ArrayList<>();
        Cursor cur = db.query(ShelterDB.TABLE_FEMALE, null, null, null, null, null, null);
        if (cur.moveToFirst()) {
            do {
                Shelter shelter = new Shelter();
                HashMap<String, String> timeTable = new HashMap<>();
                shelter.setAddress_1(cur.getString(cur.getColumnIndex(ShelterDB.KEY_address1)));
                shelter.setAddress_2(cur.getString(cur.getColumnIndex(ShelterDB.KEY_address2)));
                shelter.setLatitude(cur.getString(cur.getColumnIndex(ShelterDB.KEY_latitude)));
                shelter.setLongitude(cur.getString(cur.getColumnIndex(ShelterDB.KEY_longitude)));
                shelter.setWhat(cur.getString(cur.getColumnIndex(ShelterDB.KEY_what)));
                shelter.setPhone(cur.getString(cur.getColumnIndex(ShelterDB.KEY_phone)));
                shelter.setName(cur.getString(cur.getColumnIndex(ShelterDB.KEY_name)));
                shelter.setWho(cur.getString(cur.getColumnIndex(ShelterDB.KEY_who)));
                shelter.setWebsite(cur.getString(cur.getColumnIndex(ShelterDB.KEY_website)));
                shelter.setSuburb(cur.getString(cur.getColumnIndex(ShelterDB.KEY_suburb)));
                timeTable.put("monday",cur.getString(cur.getColumnIndex(ShelterDB.KEY_monday)));
                timeTable.put("tuesday",cur.getString(cur.getColumnIndex(ShelterDB.KEY_tuesday)));
                timeTable.put("wednesday",cur.getString(cur.getColumnIndex(ShelterDB.KEY_wednesday)));
                timeTable.put("thursday",cur.getString(cur.getColumnIndex(ShelterDB.KEY_thursday)));
                timeTable.put("friday",cur.getString(cur.getColumnIndex(ShelterDB.KEY_friday)));
                timeTable.put("saturday",cur.getString(cur.getColumnIndex(ShelterDB.KEY_saturday)));
                timeTable.put("sunday",cur.getString(cur.getColumnIndex(ShelterDB.KEY_sunday)));

                shelter.setTimetable(timeTable);
                shelters.add(shelter);
            } while (cur.moveToNext());
        }
        cur.close();
        db.close();
        return shelters;
    }
}








