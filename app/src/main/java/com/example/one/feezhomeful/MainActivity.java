package com.example.one.feezhomeful;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Shelter> allShelters = new ArrayList<>();
    private List<Food> allFoods = new ArrayList<>();
    private List<Food> allFoodsOnlineDB = new ArrayList<>();
    private List<Toilet> allToilet = new ArrayList<>();
    private List<Water> allWater = new ArrayList<>();
    private long lastBackTime = 0;
    private long currentBackTime = 0;
    private ProgressBar mainProgressBar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.content, new MainFragment()).commit();
                    return true;
                case R.id.navigation_shelter:
                    fragmentTransaction.replace(R.id.content, new ShelterListFragment()).commit();
                    return true;
                case R.id.navigation_food:
                    fragmentTransaction.replace(R.id.content, new FoodListFragment()).commit();
                    return true;
                case R.id.navigation_map:
                    fragmentTransaction.replace(R.id.content, new CommentFragment()).commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainProgressBar = (ProgressBar) findViewById(R.id.progress_bar_main);

        addMaleShelterAsy();
        addFemaleShelterAsy();
        addFoodAsy();
        addMaleToiletAsy();
        addFemaleToiletAsy();
        addWaterAsy();
        addFoodOnlineAsy();


            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, new MainFragment()).commit();

    }


    public void addFemaleShelterAsy() {
        new AsyncTask<Void, Void, List<Shelter>>() {

            @Override
            protected List<Shelter> doInBackground(Void... params) {
                List<Shelter> s = RestClient.getFemaleShelter();
                return s;
            }

            @Override
            protected void onPostExecute(List<Shelter> input) {
                allShelters = input;
                LocationRepo shelterRepo = new LocationRepo(getApplicationContext());
                try{
//                    dbManager.initialTable(dbManager.getWritableDatabase());
                    shelterRepo.open();
                    shelterRepo.initialFemaleShelterTable();
                    for(Shelter s : allShelters){
                        shelterRepo.insertFemaleShelter(new ShelterDB(), s);
                    }}catch(Exception e){
                }
                shelterRepo.close();
            }
        }.execute();
    }


    public void addMaleShelterAsy() {
        new AsyncTask<Void, Integer, List<Shelter>>() {

            @Override
            protected List<Shelter> doInBackground(Void... params) {
                List<Shelter> s = RestClient.getMaleShelter();
                for(int i = 0; i < s.size(); i++){
                    publishProgress(i);
                }

                return s;
            }


            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate();
                //通过publishProgress方法传过来的值进行进度条的更新.
                mainProgressBar.setProgress(values[0]);
            }


            @Override
            protected void onPostExecute(List<Shelter> input) {
                mainProgressBar.setVisibility(View.GONE);
                allShelters = input;
                LocationRepo shelterRepo = new LocationRepo(getApplicationContext());
                try{
//                    dbManager.initialTable(dbManager.getWritableDatabase());
                    shelterRepo.open();
                    shelterRepo.initialMaleShelterTable();
                    for(Shelter s : allShelters){
                        shelterRepo.insertMaleShelter(new ShelterDB(), s);
                    }}catch(Exception e){
                }
                shelterRepo.close();
            }
        }.execute();
    }




    public void addFemaleToiletAsy() {
        new AsyncTask<Void, Void, List<Toilet>>() {

            @Override
            protected List<Toilet> doInBackground(Void... params) {
                List<Toilet> s = RestClient.getFemaleToilet();
                return s;
            }

            @Override
            protected void onPostExecute(List<Toilet> input) {
                allToilet = input;
                LocationRepo shelterRepo = new LocationRepo(getApplicationContext());
                try{
                    shelterRepo.open();
                    shelterRepo.initialFemaleToiletTable();
                    for(Toilet s : allToilet){
                        shelterRepo.insertFemaleToilet( s);
                    }}catch(Exception e){
                }
                shelterRepo.close();
            }
        }.execute();
    }

    public void addMaleToiletAsy() {
        new AsyncTask<Void, Void, List<Toilet>>() {

            @Override
            protected List<Toilet> doInBackground(Void... params) {
                List<Toilet> s = RestClient.getMaleToilet();
                return s;
            }

            @Override
            protected void onPostExecute(List<Toilet> input) {
                allToilet = input;
                LocationRepo shelterRepo = new LocationRepo(getApplicationContext());
                try{
                    shelterRepo.open();
                    shelterRepo.initialMaleToiletTable();
                    for(Toilet s : allToilet){
                        shelterRepo.insertMaleToilet(s);
                    }}catch(Exception e){
                }
                shelterRepo.close();
            }
        }.execute();
    }

    public void addWaterAsy() {
        new AsyncTask<Void, Void, List<Water>>() {

            @Override
            protected List<Water> doInBackground(Void... params) {
                List<Water> s = RestClient.getWater();


                return s;
            }

            @Override
            protected void onPostExecute(List<Water> input) {
                allWater = input;
                LocationRepo shelterRepo = new LocationRepo(getApplicationContext());
                try{
                    shelterRepo.open();
                    shelterRepo.initialWaterTable();
                    for(Water s : allWater){
                        shelterRepo.insertWater( s);
                    }}catch(Exception e){

                }
                shelterRepo.close();
            }
        }.execute();
    }

    public void addFoodAsy() {
        new AsyncTask<Void, Void, List<Food>>() {
            @Override
            protected List<Food> doInBackground(Void... params) {
                List<Food> s = RestClient.getFood();
                return s;
            }

            @Override
            protected void onPostExecute(List<Food> input) {
                allFoods = input;
                LocationRepo foodRepo = new LocationRepo(getApplicationContext());
                try{
                    foodRepo.open();
                    foodRepo.initialFoodTable();
                    for(Food f : allFoods){
                        foodRepo.insertFood(new FoodDB(), f);
                    }}catch(Exception e){

                }
                foodRepo.close();
            }
        }.execute();
    }


    public void addFoodOnlineAsy() {
        new AsyncTask<Void, Void, List<Food>>() {
            @Override
            protected List<Food> doInBackground(Void... params) {
                List<Food> s = RestClient.getFoodFromDatabase();
                return s;
            }

            @Override
            protected void onPostExecute(List<Food> input) {
                allFoodsOnlineDB = input;
                LocationRepo foodRepo = new LocationRepo(getApplicationContext());
                try{
                    foodRepo.open();
                    foodRepo.initialFoodOnlineDBTable();
                    for(Food f : allFoodsOnlineDB){
                        foodRepo.insertFoodToOnlineDB(new FoodDB(), f);
                    }}catch(Exception e){

                }
                foodRepo.close();
            }
        }.execute();
    }


    //remind user when he presses the back button on devices
    //double check whether to exit app
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            currentBackTime = System.currentTimeMillis();
            //do nothing when time for two clicks is larger than 2 seconds
            if(currentBackTime - lastBackTime > 2 * 1000){
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
                lastBackTime = currentBackTime;
            }else{ //if less than 2 seconds, app will close
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
