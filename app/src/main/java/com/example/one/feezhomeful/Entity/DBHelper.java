package com.example.one.feezhomeful;

/**
 * Created by one on 30/08/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by one on 27/08/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=4;

    private static final String DATABASE_NAME="location.db";
    public DBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        //create Table

      String CREATE_TABLE_SHELTER_FROM_DB="CREATE TABLE "+ ShelterDB.TABLE_SHELTER_FROM_DB+"("
                +ShelterDB.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +ShelterDB.KEY_address1+" TEXT, "
                +ShelterDB.KEY_address2+" TEXT, "
                +ShelterDB.KEY_latitude+" TEXT,"
                +ShelterDB.KEY_longitude+" TEXT, "
                +ShelterDB.KEY_sex+" TEXT,"
                +ShelterDB.KEY_child+" TEXT, "
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
        String CREATE_TABLE_SHELTER_FEMALE="CREATE TABLE "+ ShelterDB.TABLE_FEMALE+"("
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
        String CREATE_TABLE_SHELTER_MALE="CREATE TABLE "+ ShelterDB.TABLE_MALE+"("
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
        String CREATE_TABLE_FOOD="CREATE TABLE "+ FoodDB.TABLE+"("
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
                +ShelterDB.KEY_monday+" TEXT,"
                +ShelterDB.KEY_tuesday+" TEXT,"
                +ShelterDB.KEY_wednesday+" TEXT, "
                +ShelterDB.KEY_thursday+" TEXT,"
                +ShelterDB.KEY_friday+" TEXT,"
                +ShelterDB.KEY_saturday+" TEXT, "
                +ShelterDB.KEY_sunday+" TEXT)" +
                "";
        String CREATE_TABLE_FOOD_ONLINEDB="CREATE TABLE "+ FoodDB.TABLE_OnlineDB+"("
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
                +ShelterDB.KEY_monday+" TEXT,"
                +ShelterDB.KEY_tuesday+" TEXT,"
                +ShelterDB.KEY_wednesday+" TEXT, "
                +ShelterDB.KEY_thursday+" TEXT,"
                +ShelterDB.KEY_friday+" TEXT,"
                +ShelterDB.KEY_saturday+" TEXT, "
                +ShelterDB.KEY_sunday+" TEXT)" +
                "";
        String CREATE_TABLE_TOILET_FEMALE="CREATE TABLE "+ ToiletDB.TABLE_FEMALE+"("
                +ToiletDB.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +ToiletDB.KEY_name+" TEXT, "
                +ToiletDB.KEY_baby+" TEXT, "
                +ToiletDB.KEY_latitude+" TEXT,"
                +ToiletDB.KEY_longitude+" TEXT)" +
                "";
        String CREATE_TABLE_TOILET_MALE="CREATE TABLE "+ ToiletDB.TABLE_MALE+"("
                +ToiletDB.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +ToiletDB.KEY_name+" TEXT, "
                +ToiletDB.KEY_baby+" TEXT, "
                +ToiletDB.KEY_latitude+" TEXT,"
                +ToiletDB.KEY_longitude+" TEXT)" +
                "";


        String CREATE_TABLE_WATER="CREATE TABLE "+ WaterDB.TABLE+"("
                +WaterDB.KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"
                +WaterDB.KEY_name+" TEXT, "
                +WaterDB.KEY_latitude+" TEXT,"
                +WaterDB.KEY_longitude+" TEXT)" +
                "";

        db.execSQL(CREATE_TABLE_SHELTER_FEMALE);
        db.execSQL(CREATE_TABLE_SHELTER_MALE);
        db.execSQL(CREATE_TABLE_FOOD);
        db.execSQL(CREATE_TABLE_SHELTER_FROM_DB);
        db.execSQL(CREATE_TABLE_TOILET_MALE);
        db.execSQL(CREATE_TABLE_TOILET_FEMALE);
        db.execSQL(CREATE_TABLE_WATER);
        db.execSQL(CREATE_TABLE_FOOD_ONLINEDB);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //If previous table exists, delete it
        db.execSQL("DROP TABLE IF EXISTS "+ ShelterDB.TABLE_FEMALE);
        db.execSQL("DROP TABLE IF EXISTS "+ ShelterDB.TABLE_MALE);
        db.execSQL("DROP TABLE IF EXISTS "+ FoodDB.TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ ToiletDB.TABLE_MALE);
        db.execSQL("DROP TABLE IF EXISTS "+ ToiletDB.TABLE_FEMALE);
        db.execSQL("DROP TABLE IF EXISTS "+ WaterDB.TABLE);
        db.execSQL("DROP TABLE IF EXISTS "+ FoodDB.TABLE_OnlineDB);
        db.execSQL("DROP TABLE IF EXISTS "+ ShelterDB.TABLE_SHELTER_FROM_DB);
        //Create new one
        onCreate(db);
    }

//    public void initialTable(SQLiteDatabase db){
//        db.execSQL("DROP TABLE IF EXISTS "+ ShelterDB.TABLE_FEMALE);
//        db.execSQL("DROP TABLE IF EXISTS "+ FoodDB.TABLE);
//        db.execSQL("DROP TABLE IF EXISTS "+ FoodDB.TABLE_OnlineDB);
//        db.execSQL("DROP TABLE IF EXISTS "+ ToiletDB.TABLE_FEMALE);
//        db.execSQL("DROP TABLE IF EXISTS "+ WaterDB.TABLE);
//        onCreate(db);
//    }
//
//
//
//    public boolean deleteDatabase(Context context){
//        return context.deleteDatabase(DATABASE_NAME);
//    }



}
