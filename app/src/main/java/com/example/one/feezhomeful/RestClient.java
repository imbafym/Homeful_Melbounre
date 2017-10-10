package com.example.one.feezhomeful;

/**
 * Created by one on 30/08/2017.
 */

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;



/**
 * Created by one on 25/08/2017.
 */

public class RestClient {

    public static List<Shelter> getShelter() {
        URL url = null;
        int number = 0;
        HttpURLConnection conn = null;
        JSONArray jsonArray = null;
        String result = "";
        String no = "";
        String textResult = "";
        List<Shelter> shelterList = new ArrayList<Shelter>();
        //Making HTTP request
        try {
            url = new URL("https://data.melbourne.vic.gov.au/resource/jduy-94rf.json");
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            jsonArray = new JSONArray(textResult);
            for(int i = 0; i < jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Shelter shelter = new Shelter();
                HashMap<String,String> timeTable = new HashMap<>();
                if(jsonObject.get("category_1").equals("Accommodation") || jsonObject.get("category_2").equals("Accommodation") || jsonObject.get("category_3").equals("Accommodation")) {
                   if(jsonObject.get("what").toString().contains("women")){
                    if (jsonObject.has("geocoded_location")) {
                        if (jsonObject.has("address_1")) {
                            shelter.setAddress_1(jsonObject.get("address_1").toString());
                        }
                        if (jsonObject.has("address_2")) {
                            shelter.setAddress_2(jsonObject.get("address_2").toString());
                        }
                        if (jsonObject.has("latitude")) {
                            shelter.setLatitude(jsonObject.get("latitude").toString());
                        }
                        if (jsonObject.has("longitude")) {
                            shelter.setLongitude(jsonObject.get("longitude").toString());
                        }
                        if (jsonObject.has("what")) {
                            shelter.setWhat(jsonObject.get("what").toString());
                        }
                        if (jsonObject.has("phone")) {
                            shelter.setPhone(jsonObject.get("phone").toString());
                        }
                        if (jsonObject.has("name")) {
                            shelter.setName(jsonObject.get("name").toString());
                        }
                        if (jsonObject.has("who")) {
                            shelter.setWho(jsonObject.get("who").toString());
                        }
                        if (jsonObject.has("website")) {
                            shelter.setWebsite(jsonObject.get("website").toString());
                        }
                        if (jsonObject.has("monday")) {
                            timeTable.put("monday", jsonObject.get("monday").toString());
                        }
                        if (jsonObject.has("tuesday")) {
                            timeTable.put("tuesday", jsonObject.get("tuesday").toString());
                        }
                        if (jsonObject.has("wednesday")) {
                            timeTable.put("wednesday", jsonObject.get("wednesday").toString());
                        }
                        if (jsonObject.has("thursday")) {
                            timeTable.put("thursday", jsonObject.get("thursday").toString());
                        }
                        if (jsonObject.has("friday")) {
                            timeTable.put("friday", jsonObject.get("friday").toString());
                        }
                        if (jsonObject.has("saturday")) {
                            timeTable.put("saturday", jsonObject.get("saturday").toString());
                        }
                        if (jsonObject.has("sunday")) {
                            timeTable.put("sunday", jsonObject.get("sunday").toString());
                        }

                        shelter.setTimetable(timeTable);

                        shelterList.add(shelter);
                    }
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return shelterList;
    }

    public static List<Shelter> getFemaleShelter() {
        URL url = null;
        int number = 0;
        HttpURLConnection conn = null;
        JSONArray jsonArray = null;
        String result = "";
        String no = "";
        String textResult = "";
        List<Shelter> shelterList = new ArrayList<Shelter>();
        //Making HTTP request
        try {
            url = new URL("http://54.153.147.64/getshelter.php");
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            jsonArray = new JSONArray(textResult);
            for(int i = 0; i < jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Shelter shelter = new Shelter();
                HashMap<String,String> timeTable = new HashMap<>();

                    if(jsonObject.get("Sex").toString().contains("F")){
                            if (jsonObject.has("Address")) {
                                shelter.setAddress_1(jsonObject.get("Address").toString());
                            }
                            if (jsonObject.has("address_2")) {
                                shelter.setAddress_2(jsonObject.get("address_2").toString());
                            }
                            if (jsonObject.has("Latitude")) {
                                shelter.setLatitude(jsonObject.get("Latitude").toString());
                            }
                            if (jsonObject.has("Longitude")) {
                                shelter.setLongitude(jsonObject.get("Longitude").toString());
                            }
                            if (jsonObject.has("Description")) {
                                shelter.setWhat(jsonObject.get("Description").toString());
                            }
                            if (jsonObject.has("Phone")) {
                                shelter.setPhone(jsonObject.get("Phone").toString());
                            }
                            if (jsonObject.has("Name")) {
                                shelter.setName(jsonObject.get("Name").toString());
                            }
                            if (jsonObject.has("who")) {
                                shelter.setWho(jsonObject.get("who").toString());
                            }
                            if (jsonObject.has("Website")) {
                                shelter.setWebsite(jsonObject.get("Website").toString());
                            }

                        if (jsonObject.has("Suburb")) {
                            shelter.setSuburb(jsonObject.get("Suburb").toString());
                        }
                            if (jsonObject.has("monday")) {
                                timeTable.put("monday", jsonObject.get("monday").toString());
                            }
                            if (jsonObject.has("tuesday")) {
                                timeTable.put("tuesday", jsonObject.get("tuesday").toString());
                            }
                            if (jsonObject.has("wednesday")) {
                                timeTable.put("wednesday", jsonObject.get("wednesday").toString());
                            }
                            if (jsonObject.has("thursday")) {
                                timeTable.put("thursday", jsonObject.get("thursday").toString());
                            }
                            if (jsonObject.has("friday")) {
                                timeTable.put("friday", jsonObject.get("friday").toString());
                            }
                            if (jsonObject.has("saturday")) {
                                timeTable.put("saturday", jsonObject.get("saturday").toString());
                            }
                            if (jsonObject.has("sunday")) {
                                timeTable.put("sunday", jsonObject.get("sunday").toString());
                            }

                            shelter.setTimetable(timeTable);

                            shelterList.add(shelter);


                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return shelterList;
    }

    public static List<Shelter> getMaleShelter() {
        URL url = null;
        int number = 0;
        HttpURLConnection conn = null;
        JSONArray jsonArray = null;
        String result = "";
        String no = "";
        String textResult = "";
        List<Shelter> shelterList = new ArrayList<Shelter>();
        //Making HTTP request
        try {
            url = new URL("http://54.153.147.64/getshelter.php");
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            jsonArray = new JSONArray(textResult);
            for(int i = 0; i < jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Shelter shelter = new Shelter();
                HashMap<String,String> timeTable = new HashMap<>();

                if(jsonObject.get("Sex").toString().contains("M")){
                    if (jsonObject.has("Address")) {
                        shelter.setAddress_1(jsonObject.get("Address").toString());
                    }
                    if (jsonObject.has("address_2")) {
                        shelter.setAddress_2(jsonObject.get("address_2").toString());
                    }
                    if (jsonObject.has("Latitude")) {
                        shelter.setLatitude(jsonObject.get("Latitude").toString());
                    }
                    if (jsonObject.has("Longitude")) {
                        shelter.setLongitude(jsonObject.get("Longitude").toString());
                    }
                    if (jsonObject.has("Description")) {
                        shelter.setWhat(jsonObject.get("Description").toString());
                    }
                    if (jsonObject.has("Phone")) {
                        shelter.setPhone(jsonObject.get("Phone").toString());
                    }
                    if (jsonObject.has("Name")) {
                        shelter.setName(jsonObject.get("Name").toString());
                    }
                    if (jsonObject.has("who")) {
                        shelter.setWho(jsonObject.get("who").toString());
                    }
                    if (jsonObject.has("Website")) {
                        shelter.setWebsite(jsonObject.get("Website").toString());
                    }

                    if (jsonObject.has("Suburb")) {
                        shelter.setSuburb(jsonObject.get("Suburb").toString());
                    }
                    if (jsonObject.has("monday")) {
                        timeTable.put("monday", jsonObject.get("monday").toString());
                    }
                    if (jsonObject.has("tuesday")) {
                        timeTable.put("tuesday", jsonObject.get("tuesday").toString());
                    }
                    if (jsonObject.has("wednesday")) {
                        timeTable.put("wednesday", jsonObject.get("wednesday").toString());
                    }
                    if (jsonObject.has("thursday")) {
                        timeTable.put("thursday", jsonObject.get("thursday").toString());
                    }
                    if (jsonObject.has("friday")) {
                        timeTable.put("friday", jsonObject.get("friday").toString());
                    }
                    if (jsonObject.has("saturday")) {
                        timeTable.put("saturday", jsonObject.get("saturday").toString());
                    }
                    if (jsonObject.has("sunday")) {
                        timeTable.put("sunday", jsonObject.get("sunday").toString());
                    }

                    shelter.setTimetable(timeTable);

                    shelterList.add(shelter);


                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return shelterList;
    }


    public static List<Food> getFood() {
        URL url = null;
        int number = 0;
        HttpURLConnection conn = null;
        JSONArray jsonArray = null;
        String result = "";
        String no = "";
        String textResult = "";
        List<Food> foodList = new ArrayList<Food>();
        //Making HTTP request
        try {
            url = new URL("https://data.melbourne.vic.gov.au/resource/uwyu-5y9e.json");
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            jsonArray = new JSONArray(textResult);
            for(int i = 0; i < jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Food food = new Food();
                String category= jsonObject.get("category").toString();
                HashMap<String,String> timeTable = new HashMap<>();
                if(category.toLowerCase().contains("meals") || category.toLowerCase().contains("vouchers") ) {
                    if (jsonObject.has("geocoded_location")) {
                        if(jsonObject.has("address_1")){
                            food.setAddress_1(jsonObject.get("address_1").toString());}
                        if(jsonObject.has("address_2")){
                            food.setAddress_2(jsonObject.get("address_2").toString());}
                        if(jsonObject.has("latitude")){
                            food.setLatitude(jsonObject.get("latitude").toString());}
                        if(jsonObject.has("longitude")){
                            food.setLongitude(jsonObject.get("longitude").toString());}
                        if(jsonObject.has("what")){
                            food.setWhat(jsonObject.get("what").toString());}
                        if(jsonObject.has("phone")){
                            food.setPhone(jsonObject.get("phone").toString());}
                        if(jsonObject.has("name")){
                            food.setName(jsonObject.get("name").toString());}
                        if(jsonObject.has("who")){
                            food.setWho(jsonObject.get("who").toString());}
                        if(jsonObject.has("website")){
                            food.setWebsite(jsonObject.getJSONObject("website").get("url").toString());}
                        if(jsonObject.has("suburb")){
                            food.setSuburb(jsonObject.get("suburb").toString());}
                        if(jsonObject.has("monday")){
                            timeTable.put("monday",jsonObject.get("monday").toString());
                        }
                        if(jsonObject.has("tuesday")){
                            timeTable.put("tuesday",jsonObject.get("tuesday").toString());
                        }
                        if(jsonObject.has("wednesday")){
                            timeTable.put("wednesday",jsonObject.get("wednesday").toString());
                        }
                        if(jsonObject.has("thursday")){
                            timeTable.put("thursday",jsonObject.get("thursday").toString());
                        }
                        if(jsonObject.has("friday")){
                            timeTable.put("friday",jsonObject.get("friday").toString());
                        }
                        if(jsonObject.has("saturday")){
                            timeTable.put("saturday",jsonObject.get("saturday").toString());
                        }
                        if(jsonObject.has("sunday")){
                            timeTable.put("sunday",jsonObject.get("sunday").toString());
                        }
                        food.setTimetable(timeTable);
                        foodList.add(food);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return foodList;
    }


    public static List<Food> getFoodFromDatabase() {
        URL url = null;
        int number = 0;
        HttpURLConnection conn = null;
        JSONArray jsonArray = null;
        String result = "";
        String no = "";
        String textResult = "";
        List<Food> foodList = new ArrayList<Food>();
        //Making HTTP request
        try {
            url = new URL("http://54.153.147.64/getfood.php");
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            jsonArray = new JSONArray(textResult);
            for(int i = 0; i < jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Food food = new Food();
                JSONObject secondJsonObj = jsonObject.getJSONObject("");
                HashMap<String,String> timeTable = new HashMap<>();


                        if(secondJsonObj.has("Address")){
                            food.setAddress_1(secondJsonObj.get("Address").toString());}
                        if(secondJsonObj.has("address_2")){
                            food.setAddress_2(secondJsonObj.get("address_2").toString());}
                        if(secondJsonObj.has("Latitude")){
                            food.setLatitude(secondJsonObj.get("Latitude").toString());}
                        if(secondJsonObj.has("Longitude")){
                            food.setLongitude(secondJsonObj.get("Longitude").toString());}
                        if(secondJsonObj.has("Description")){
                            food.setWhat(secondJsonObj.get("Description").toString());}
                        if(secondJsonObj.has("Phone")){
                            food.setPhone(secondJsonObj.get("Phone").toString());}
                        if(secondJsonObj.has("Name")){
                            food.setName(secondJsonObj.get("Name").toString());}
                        if(secondJsonObj.has("who")){
                            food.setWho(secondJsonObj.get("who").toString());}
                        if(secondJsonObj.has("Website")){
                            food.setWebsite(secondJsonObj.get("Website").toString());}
                    if(secondJsonObj.has("Suburb")){
                            food.setSuburb(secondJsonObj.get("Suburb").toString());}
                        if(secondJsonObj.has("Monday")){
                            timeTable.put("Monday",secondJsonObj.get("Monday").toString());
                        }
                        if(secondJsonObj.has("Tuesday")){
                            timeTable.put("Tuesday",secondJsonObj.get("Tuesday").toString());
                        }
                        if(secondJsonObj.has("Wednesday")){
                            timeTable.put("Wednesday",secondJsonObj.get("Wednesday").toString());
                        }
                        if(secondJsonObj.has("Thursday")){
                            timeTable.put("Thursday",secondJsonObj.get("Thursday").toString());
                        }
                        if(secondJsonObj.has("Friday")){
                            timeTable.put("Friday",secondJsonObj.get("Friday").toString());
                        }
                        if(secondJsonObj.has("Saturday")){
                            timeTable.put("Saturday",secondJsonObj.get("Saturday").toString());
                        }
                        if(secondJsonObj.has("Sunday")){
                            timeTable.put("Sunday",secondJsonObj.get("Sunday").toString());
                        }
                        food.setTimetable(timeTable);
                        foodList.add(food);
            }




        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return foodList;
    }


    public static List<Toilet> getMaleToilet() {
        URL url = null;
        int number = 0;
        HttpURLConnection conn = null;
        JSONArray jsonArray = null;
        String result = "";
        String no = "";
        String textResult = "";
        List<Toilet> toiletsList = new ArrayList<Toilet>();
        //Making HTTP request
        try {
            url = new URL("https://data.melbourne.vic.gov.au/resource/327g-9akn.json");
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            jsonArray = new JSONArray(textResult);
            for(int i = 0; i < jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Toilet toilet = new Toilet();

                if(jsonObject.get("male").equals("yes") ) {

                    if(jsonObject.has("lon")){
                        toilet.setLongitude(jsonObject.get("lon").toString());}
                    if(jsonObject.has("lat")){
                        toilet.setLatitude(jsonObject.get("lat").toString());}
                    if(jsonObject.has("name")){
                        toilet.setName(jsonObject.get("name").toString());}
                    if(jsonObject.has("baby_facil")){
                        toilet.setBaby(jsonObject.get("baby_facil").toString());}
                    toiletsList.add(toilet);

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return toiletsList;
    }



    public static List<Toilet> getFemaleToilet() {
        URL url = null;
        int number = 0;
        HttpURLConnection conn = null;
        JSONArray jsonArray = null;
        String result = "";
        String no = "";
        String textResult = "";
        List<Toilet> toiletsList = new ArrayList<Toilet>();
        //Making HTTP request
        try {
            url = new URL("https://data.melbourne.vic.gov.au/resource/327g-9akn.json");
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            jsonArray = new JSONArray(textResult);
            for(int i = 0; i < jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Toilet toilet = new Toilet();

                if(jsonObject.get("female").equals("yes") ) {

                        if(jsonObject.has("lon")){
                            toilet.setLongitude(jsonObject.get("lon").toString());}
                        if(jsonObject.has("lat")){
                            toilet.setLatitude(jsonObject.get("lat").toString());}
                        if(jsonObject.has("name")){
                            toilet.setName(jsonObject.get("name").toString());}
                        if(jsonObject.has("baby_facil")){
                            toilet.setBaby(jsonObject.get("baby_facil").toString());}
                    toiletsList.add(toilet);

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return toiletsList;
    }


    public static List<Water> getWater() {
        URL url = null;
        int number = 0;
        HttpURLConnection conn = null;
        JSONArray jsonArray = null;
        String result = "";
        String no = "";
        String textResult = "";
        List<Water> wartersList = new ArrayList<Water>();
        //Making HTTP request
        try {
            url = new URL("https://data.melbourne.vic.gov.au/resource/4vrs-i8xz.json");
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            jsonArray = new JSONArray(textResult);
            for(int i = 0; i < jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Water water = new Water();
                JSONObject jsonGeo= jsonObject.getJSONObject("geom");
                    if(jsonGeo.has("coordinates")){
                        water.setName(jsonObject.get("descriptio").toString());
                        water.setLatitude(jsonGeo.getJSONArray("coordinates").getString(1));
                        water.setLongitude(jsonGeo.getJSONArray("coordinates").getString(0));
                    }

                    wartersList.add(water);


            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return wartersList;
    }

    public static List<Shelter> getHealth() {
        URL url = null;
        int number = 0;
        HttpURLConnection conn = null;
        JSONArray jsonArray = null;
        String result = "";
        String no = "";
        String textResult = "";
        List<Shelter> shelterList = new ArrayList<Shelter>();
        //Making HTTP request
        try {
            url = new URL("https://data.melbourne.vic.gov.au/resource/jduy-94rf.json");
            //open the connection
            conn = (HttpURLConnection) url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            //Read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input steream and store it as string
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
            jsonArray = new JSONArray(textResult);
            for(int i = 0; i < jsonArray.length();i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Shelter shelter = new Shelter();
                HashMap<String,String> timeTable = new HashMap<>();
                if(jsonObject.get("category_1").equals("Health Services") || jsonObject.get("category_2").equals("Health Services") || jsonObject.get("category_3").equals("Health Services")) {
                    if(jsonObject.get("what").toString().contains("women")){
                        if (jsonObject.has("geocoded_location")) {
                            if (jsonObject.has("address_1")) {
                                shelter.setAddress_1(jsonObject.get("address_1").toString());
                            }
                            if (jsonObject.has("address_2")) {
                                shelter.setAddress_2(jsonObject.get("address_2").toString());
                            }
                            if (jsonObject.has("latitude")) {
                                shelter.setLatitude(jsonObject.get("latitude").toString());
                            }
                            if (jsonObject.has("longitude")) {
                                shelter.setLongitude(jsonObject.get("longitude").toString());
                            }
                            if (jsonObject.has("what")) {
                                shelter.setWhat(jsonObject.get("what").toString());
                            }
                            if (jsonObject.has("phone")) {
                                shelter.setPhone(jsonObject.get("phone").toString());
                            }
                            if (jsonObject.has("name")) {
                                shelter.setName(jsonObject.get("name").toString());
                            }
                            if (jsonObject.has("who")) {
                                shelter.setWho(jsonObject.get("who").toString());
                            }
                            if (jsonObject.has("website")) {
                                shelter.setWebsite(jsonObject.get("website").toString());
                            }
                            if (jsonObject.has("monday")) {
                                timeTable.put("monday", jsonObject.get("monday").toString());
                            }
                            if (jsonObject.has("tuesday")) {
                                timeTable.put("tuesday", jsonObject.get("tuesday").toString());
                            }
                            if (jsonObject.has("wednesday")) {
                                timeTable.put("wednesday", jsonObject.get("wednesday").toString());
                            }
                            if (jsonObject.has("thursday")) {
                                timeTable.put("thursday", jsonObject.get("thursday").toString());
                            }
                            if (jsonObject.has("friday")) {
                                timeTable.put("friday", jsonObject.get("friday").toString());
                            }
                            if (jsonObject.has("saturday")) {
                                timeTable.put("saturday", jsonObject.get("saturday").toString());
                            }
                            if (jsonObject.has("sunday")) {
                                timeTable.put("sunday", jsonObject.get("sunday").toString());
                            }

                            shelter.setTimetable(timeTable);

                            shelterList.add(shelter);
                        }
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return shelterList;
    }
}
