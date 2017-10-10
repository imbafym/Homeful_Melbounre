package com.example.one.feezhomeful;

import android.content.Context;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by one on 30/08/2017.
 */

public class MainFragment extends Fragment implements
        LocationListener {
    //Define a request code to send to Google Play services
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;


    private Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;
    View vMain;
    private ImageView welcomeImage;
    private TextView welcomeText;
    private LatLng latLng;
    private ArrayList<Shelter> allShelters;
    private ArrayList<Shelter> allFemaleShelters;
    private ArrayList<Shelter> allMaleShelters;
    private ArrayList<Food> allFood;
    private ArrayList<Food> allFoodOnlineDB;
    private ArrayList<Toilet> allToilet;
    private ArrayList<Toilet> allFemaleToilet;
    private ArrayList<Toilet> allMaleToilet;
    private ArrayList<Water> allWater;
    private Double minDistance;
    private Shelter currentShelter;
    private Food currentFood;
    private Toilet currentToilet;
    private  Water currentWater;
    private MyItem currentMyItem;
    private String provider;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(LocationServices.API)
//                    .build();
//        }
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);
        ImageButton shelterButton = (ImageButton) vMain.findViewById(R.id.imageButton_shelter);
        ImageButton foodButton = (ImageButton) vMain.findViewById(R.id.imageButton_food);
        ImageButton toiletButton = (ImageButton) vMain.findViewById(R.id.imageButton_toilet);
        ImageButton waterButton = (ImageButton) vMain.findViewById(R.id.imageButton_drink);
        addFoodAsy();

        ImageButton settingsButton = (ImageButton) vMain.findViewById(R.id.button_setting);
        ImageButton helpButton = (ImageButton) vMain.findViewById(R.id.button_help);
        LocationRepo locationRepo = new LocationRepo(getContext());
        allFemaleShelters = locationRepo.getFemaleShelterList();
        allMaleShelters = locationRepo.getMaleShelterList();
        allFood = locationRepo.getFoodList();
        allFemaleToilet = locationRepo.getFemaleToiletList();
        allMaleToilet = locationRepo.getMaleToiletList();
        allWater = locationRepo.getWaterList();
        allFoodOnlineDB = locationRepo.getFoodOnlineDBList();


        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingBasePopUpWindow popupWindow = new SettingBasePopUpWindow(getContext());
                popupWindow.setWidth(1000);
                popupWindow.setHeight(1400);
//                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//                popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setContentView(LayoutInflater.from(getContext()).inflate(R.layout.popup_setting, null));
                popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                popupWindow.setOutsideTouchable(false);
                popupWindow.setFocusable(true);
                popupWindow.showAtLocation(vMain, Gravity.CENTER|Gravity.CENTER_HORIZONTAL,0,0);
            }
        });


//        babySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                SharedPreferences prefs = getActivity().getSharedPreferences(
//                        "baby", Context.MODE_PRIVATE);
//                if(isChecked) {
//                    prefs.edit().putBoolean("baby", true).apply();
//                }else{
//                    prefs.edit().putBoolean("baby", false).apply();
//                }
//            }
//        });
//
//        maleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences prefs = getActivity().getSharedPreferences(
//                        "currentGender", Context.MODE_PRIVATE);
//                prefs.edit().putBoolean("Gender",true).apply();
//
//            }
//        });
//
//        femaleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences prefs = getActivity().getSharedPreferences(
//                        "currentGender", Context.MODE_PRIVATE);
//                prefs.edit().putBoolean("Gender",false).apply();
//            }
//        });


        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentFood = new Food();
                String currentWeek = getCurrentWeekDay();
                String currentTime = getCurrentTime();
                int now = Integer.parseInt(currentTime);
                Boolean flag = false;
                minDistance = 0.0;

                if (allFoodOnlineDB .size()!=0) {
                    for (Food s : allFoodOnlineDB) {
                        String openStatus = s.getTimetable().get(currentWeek);

                        if (!openStatus.equals("Closed") && !openStatus.equals("N/A")) {
                            String[] time = openStatus.trim().split(",");
                            int end = 24;
                            int start = Integer.parseInt(time[0]);
                            if (time.length == 2) {
                                end = Integer.parseInt(time[1].trim());
                            }
                            if (now >= start && now <= end) {
                                Double currentDistance = Distance(latitude, longitude, Double.parseDouble(s.getLatitude()), Double.parseDouble(s.getLongitude()));
                                if (minDistance != 0.0) {
                                    if (currentDistance != null && currentDistance < minDistance) {
                                        minDistance = currentDistance;
                                        currentFood = s;
                                    }
                                } else {
                                    minDistance = currentDistance;
                                    currentFood = s;
                                }
                            }

                        }
                    }
                    if (!currentFood.getName().trim().equals("")) {
                        for (Food f : allFood) {
                            if (f.getName().equals(currentFood.getName())) {
                                currentFood = f;
                            }
                        }

                        addToMyItem(currentFood);
                        SingleItemMapFragment singleItemMapFragment = new SingleItemMapFragment();
                        singleItemMapFragment.setSelectedItem(currentMyItem);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.addToBackStack(null);
                        ft.replace(R.id.content, singleItemMapFragment);
                        ft.commit();
                    } else {
                        Toast.makeText(getContext(), "No avaliable foodPlace", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getContext(),"Please Check Internet Connection", Toast.LENGTH_LONG).show();
                }
            }
        });

        shelterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentShelter = new Shelter();

                minDistance = 0.0;
                SharedPreferences preferences = getActivity().getSharedPreferences(
                        "currentGender", Context.MODE_PRIVATE);
                Boolean flag = preferences.getBoolean("Gender",true);
                if(flag){
                    allShelters = allMaleShelters;
                }else {
                    allShelters =allFemaleShelters;
                }
        if(allShelters.size()!=0) {
            for (Shelter s : allShelters) {
                Double currentDistance = Distance(latitude, longitude, Double.parseDouble(s.getLatitude()), Double.parseDouble(s.getLongitude()));
                if (minDistance != 0.0) {
                    if (currentDistance != null && currentDistance < minDistance) {
                        minDistance = currentDistance;
                        currentShelter = s;
                    }
                } else {
                    minDistance = currentDistance;
                    currentShelter = s;
                }
            }
            addToMyItem(currentShelter);
            SingleItemMapFragment singleItemMapFragment = new SingleItemMapFragment();
            singleItemMapFragment.setSelectedItem(currentMyItem);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(null);
            ft.replace(R.id.content, singleItemMapFragment);
            ft.commit();
        }else{
            Toast.makeText(getContext(),"Please Check Internet connection",Toast.LENGTH_LONG).show();
        }
            }
        });

        toiletButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentToilet = new Toilet();

                minDistance = 0.0;
                SharedPreferences preferences = getActivity().getSharedPreferences(
                        "currentGender", Context.MODE_PRIVATE);
                Boolean flag = preferences.getBoolean("Gender",true);
                SharedPreferences babyPre = getActivity().getSharedPreferences(
                        "baby", Context.MODE_PRIVATE);
                Boolean isBaby = babyPre.getBoolean("baby",false);
                if(flag){
                    allToilet = allMaleToilet;
                }else {
                    allToilet =allFemaleToilet;
                }

                if (allToilet .size()!=0) {
                    for (Toilet s : allToilet) {
                        if (isBaby) {
                            if (s.getBaby().contains("yes")) {
                                Double currentDistance = Distance(latitude, longitude, Double.parseDouble(s.getLatitude()), Double.parseDouble(s.getLongitude()));
                                if (minDistance != 0.0) {
                                    if (currentDistance != null && currentDistance < minDistance) {
                                        minDistance = currentDistance;
                                        currentToilet = s;
                                    }
                                } else {
                                    minDistance = currentDistance;
                                    currentToilet = s;
                                }
                            }
                        } else {
                            Double currentDistance = Distance(latitude, longitude, Double.parseDouble(s.getLatitude()), Double.parseDouble(s.getLongitude()));
                            if (minDistance != 0.0) {
                                if (currentDistance != null && currentDistance < minDistance) {
                                    minDistance = currentDistance;
                                    currentToilet = s;
                                }
                            } else {
                                minDistance = currentDistance;
                                currentToilet = s;
                            }
                        }
                    }



                SingleItemMapFragment singleItemMapFragment = new SingleItemMapFragment();
                singleItemMapFragment.setCurrentToilet(currentToilet);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.replace(R.id.content, singleItemMapFragment);
                ft.commit();

                }else {
                    Toast.makeText(getContext(),"Please Check Internet connection",Toast.LENGTH_LONG).show();
                }
            }
        });
        waterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentWater = new Water();
                minDistance = 0.0;
                if(allWater.size()!=0) {
                    for (Water s : allWater) {
                        Double currentDistance = Distance(latitude, longitude, Double.parseDouble(s.getLatitude()), Double.parseDouble(s.getLongitude()));
                        if (minDistance != 0.0) {
                            if (currentDistance != null && currentDistance < minDistance) {
                                minDistance = currentDistance;
                                currentWater = s;
                            }
                        } else {
                            minDistance = currentDistance;
                            currentWater = s;
                        }
                    }
                    SingleItemMapFragment singleItemMapFragment = new SingleItemMapFragment();
                    singleItemMapFragment.setCurrentWater(currentWater);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.replace(R.id.content, singleItemMapFragment);
                    ft.commit();
                }else {
                    Toast.makeText(getContext(),"Please Check Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });

        return vMain;

    }

    private String getCurrentWeekDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        return  dayOfTheWeek;
    }

    private String getCurrentTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String str = sdf.format(new Date());
        return str;
    }



    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //获取当前可用的位置控制器
        List<String> list = locationManager.getProviders(true);
        if (list.contains(LocationManager.GPS_PROVIDER)) {
            //是否为GPS位置控制器
            provider = LocationManager.GPS_PROVIDER;
        } else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            //是否为网络位置控制器
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(getContext(), "Please Check Internet or GPS if Opened ",
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            //获取当前位置，这里只用到了经纬度
            latitude= location.getLatitude();
            longitude= location.getLongitude();
            String currentLongitude = Double.toString(longitude);
            String currentLatitude = Double.toString(latitude);
            SharedPreferences prefs = getActivity().getSharedPreferences(
                    "currentLocation", Context.MODE_PRIVATE);
            prefs.edit().putString("currentLongitude",currentLongitude).apply();
            prefs.edit().putString("currentLatitude",currentLatitude).apply();
        }
        //绑定定位事件，监听位置是否改变
//第一个参数为控制器类型第二个参数为监听位置变化的时间间隔（单位：毫秒）
//第三个参数为位置变化的间隔（单位：米）第四个参数为位置监听器

//        // getting network status
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);


//        if (!isGPSEnabled && !isNetworkEnabled) {
//            // no network provider is enabled
//        } else {
//            this.canGetLocation = true;
//            if (isNetworkEnabled) {
//                locationManager.requestLocationUpdates(
//                        LocationManager.NETWORK_PROVIDER,
//                        MIN_TIME_BW_UPDATES,
//                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//                Log.d("activity", "LOC Network Enabled");
//                if (locationManager != null) {
//                    location = locationManager
//                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                    if (location != null) {
//                        Log.d("activity", "LOC by Network");
//                        latitude = location.getLatitude();
//                        longitude = location.getLongitude();
//                    }
//                }
//            }
//            // if GPS Enabled get lat/long using GPS Services
//            if (isGPSEnabled) {
//                if (location == null) {
//                    locationManager.requestLocationUpdates(
//                            LocationManager.GPS_PROVIDER,
//                            MIN_TIME_BW_UPDATES,
//                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//                    Log.d("activity", "RLOC: GPS Enabled");
//                    if (locationManager != null) {
//                        location = locationManager
//                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
//                        if (location != null) {
//                            Log.d("activity", "RLOC: loc by GPS");
//                            longitude = location.getLongitude();
//                            latitude = location.getLatitude();
//
//                        }
//                    }
//                }
//            }
//        LocationListener locationListener = new LocationListener() {
//
//            @Override
//            public void onLocationChanged(Location arg0) {
//                // TODO Auto-generated method stub
//                // 更新当前经纬度
//            }
//        };
//        locationManager.requestLocationUpdates(provider, 2000, 2,
//                locationListener);
        }

        private void addToMyItem(Object o){

            if(o instanceof  Shelter) {
                Shelter s = (Shelter)o;
                this.currentMyItem = new MyItem(Double.parseDouble(s.getLatitude()), Double.parseDouble(s.getLongitude()));
                currentMyItem.setWhat(s.getWhat());
                currentMyItem.setName(s.getName());
                currentMyItem.setAddress_1(s.getAddress_1());
                currentMyItem.setAddress_2(s.getAddress_2());
                currentMyItem.setWho(s.getWho());
                currentMyItem.setPhone(s.getPhone());
                currentMyItem.setWebsite(s.getWebsite());
                currentMyItem.setIconId(R.drawable.ic_icon_bed_red);
                currentMyItem.setTimetable(s.getTimetable());
            }else if(o instanceof Food ){
                Food s = (Food) o;
                this.currentMyItem = new MyItem(Double.parseDouble(s.getLatitude()), Double.parseDouble(s.getLongitude()));
                currentMyItem.setWhat(s.getWhat());
                currentMyItem.setName(s.getName());
                currentMyItem.setAddress_1(s.getAddress_1());
                currentMyItem.setAddress_2(s.getAddress_2());
                currentMyItem.setWho(s.getWho());
                currentMyItem.setPhone(s.getPhone());
                currentMyItem.setWebsite(s.getWebsite());
                currentMyItem.setIconId(R.drawable.ic_food_red);
                currentMyItem.setTimetable(s.getTimetable());

            }
        }



  @Override
public void onLocationChanged (Location location){
       if (location != null) {
             longitude = location.getLongitude();
             latitude = location.getLatitude();
           String currentLongitude = Double.toString(longitude);
           String currentLatitude = Double.toString(latitude);
           SharedPreferences prefs = getActivity().getSharedPreferences(
                   "currentLocation", Context.MODE_PRIVATE);
           prefs.edit().putString("currentLongitude",currentLongitude).apply();
           prefs.edit().putString("currentLatitude",currentLatitude).apply();
          }
  }

    /**
     * 计算地球上任意两点(经纬度)距离
     *
     * @param long1
     *            第一点经度
     * @param lat1
     *            第一点纬度
     * @param long2
     *            第二点经度
     * @param lat2
     *            第二点纬度
     * @return 返回距离 单位：米
     */
    public static double Distance(double long1, double lat1, double long2, double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return d;
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
                List<Food> dbFoodList = input;
                LocationRepo foodRepo = new LocationRepo(getActivity());
                try{
                    foodRepo.open();
                    foodRepo.initialFoodTable();
                    for(Food f : dbFoodList){
                        foodRepo.insertFood(new FoodDB(), f);
                    }}catch(Exception e){
                }
                foodRepo.close();
            }
        }.execute();
    }


 }