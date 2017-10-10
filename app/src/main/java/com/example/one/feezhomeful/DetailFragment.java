package com.example.one.feezhomeful;

/**
 * Created by LHZ on 5/9/17.
 */

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class is used for display the detail information about the food place that selected in list view or on map
 */
public class DetailFragment extends Fragment implements OnMapReadyCallback
{
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private View mView;
    private MyItem currentMyItem;
    private String latitude,longtitude;
    private TextView tv_Title, tv_Details,tv_phone,tv_website,tv_who,tv_address,tv_suburb;
    private LinearLayout layout_call,layout_website;
    private Spinner spinner;
    private String name ;
    private String what ;
    private String who ;
    private String phone ;
    private String suburb ;
    private String addressPrefix ;
    private String address ;
    private String website ;
    /**
     * To create the view of current fragment.
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        mView = inflater.inflate(R.layout.fragment_detail, container, false);
        latitude = "";
        longtitude = "";

        layout_call = (LinearLayout) mView.findViewById(R.id.layout_call_line);
        layout_website = (LinearLayout) mView.findViewById(R.id.layout_website_line);

        tv_Title = (TextView) mView.findViewById(R.id.tv_bottomSheet_Title);
        tv_Details = (TextView) mView.findViewById(R.id.tv_bottomSheet_Details);
        tv_phone = (TextView) mView.findViewById(R.id.tv_bottomSheet_Phone);
        tv_website = (TextView) mView.findViewById(R.id.tv_bottomSheet_Website);
        tv_who = (TextView) mView.findViewById(R.id.tv_bottomSheet_Who);
        tv_address = (TextView) mView.findViewById(R.id.tv_bottomSheet_Address);
        tv_suburb = (TextView) mView.findViewById(R.id.tv_suburb);
        spinner = (Spinner) mView.findViewById(R.id.spinner1);

        String todayWeekDay = getCurrentWeekDay();
        Bundle bundle = getArguments();
        //initial spinner items
        List<String> cityList = new ArrayList<String>();
        String monday = currentMyItem.getTimetable().get("monday");
        String tuesday = currentMyItem.getTimetable().get("tuesday");
        String wednesday = currentMyItem.getTimetable().get("wednesday");
        String thursday = currentMyItem.getTimetable().get("thursday");
        String friday = currentMyItem.getTimetable().get("friday");
        String saturday = currentMyItem.getTimetable().get("saturday");
        String sunday = currentMyItem.getTimetable().get("sunday");
        cityList.add("Monday: " + monday);
        cityList.add("tuesday: " + tuesday);
        cityList.add("wednesday: " + wednesday);
        cityList.add("thursday: " + thursday);
        cityList.add("friday: " + friday);
        cityList.add("saturday: " + saturday);
        cityList.add("sunday: " + sunday);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_dropdown_style, 0, cityList);
        spinner.setPrompt("See More");
//        spinner.setAdapter(adapter);
        spinner.setAdapter(new NothingSelectedSpinnerAdapter(adapter,
                R.layout.spinner_row_nothing_selected,
                // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                getActivity()));


         name = currentMyItem.getName();
         what = currentMyItem.getWhat();
        who = currentMyItem.getWho();
        phone = currentMyItem.getPhone();
       suburb = currentMyItem.getSuburb();
        addressPrefix = "";
        address = "";
       website = "";
        if (currentMyItem.getAddress_1()== null && currentMyItem.getAddress_2() == null )
        {
           address = "N/A";
        }
        else if(currentMyItem.getAddress_1()!= null && currentMyItem.getAddress_1() != "")
        {
            address = currentMyItem.getAddress_1();
        }
        else if(currentMyItem.getAddress_2() != null && currentMyItem.getAddress_2() != "")
        {
            address += " " + currentMyItem.getAddress_2();
        }
        if (currentMyItem.getWebsite() != null && !currentMyItem.getWebsite().isEmpty()) {
            website = currentMyItem.getWebsite();
        }

        tv_Title.setText(name);
        tv_Details.setText(what);
        tv_phone.setText(phone);
        tv_who.setText(who);
        tv_address.setText(address);
        tv_website.setText(website);
        //tv_suburb.setText(suburb);

        return mView;
    }

    /**
     * Register the mapView, create the mapView and load the map asynchronously
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.map_view);
        if (mMapView != null)
        {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_back_arrow);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
            }

        });

    }

    /**
     * When after loading the map, this method setting the map type, zoomable, permission, marker,
     * camera position etc. According to the location, display the position on map.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            mGoogleMap.setMyLocationEnabled(true);
        }
        else
        {
            getActivity().requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},12345);
            // Show rationale and request permission.
        }
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);

        LatLng latLng = new LatLng((currentMyItem.getPosition().latitude),(currentMyItem.getPosition().longitude));

        googleMap.addMarker(new MarkerOptions().position(latLng).title(currentMyItem.getName()).
                snippet(currentMyItem.getAddress_1()+","+currentMyItem.getAddress_2()));
        CameraPosition selectedPlace = CameraPosition.builder().target(latLng).zoom(14).bearing(0).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(selectedPlace));

    }


    /**
     * Request the permission and grant it.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch(requestCode)
        {
            case 12345:
            {
                if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    try
                    {
                        mGoogleMap.setMyLocationEnabled(true);
                    }
                    catch (SecurityException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getContext(), "Please grant the permission", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getCurrentWeekDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String result = "";
        switch (day){
            case Calendar.SUNDAY:
                // Current day is Sunday
                result =  "sunday";
                break;
            case Calendar.MONDAY:
                // Current day is Monday
                result =  "monday";
                break;
            case Calendar.TUESDAY:
                result =  "tuesday";
                break;
            case Calendar.WEDNESDAY:
                result =  "wednesday";
                break;
            case Calendar.THURSDAY:
                result =  "thursday";
                break;
            case Calendar.FRIDAY:
                result =  "friday";
                break;
            case Calendar.SATURDAY:
                result =  "saturday";
                break;
        }
        return  result;
    }

    /**
     * invoked when view is created.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        if (view != null)
        {
//            mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                @Override
//                public void onMapClick(LatLng latLng)
//                {
//
//                }
//            });

            layout_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    if (currentMyItem.getPhone().length() == 0)
                    {
                        layout_call.setClickable(false);
                    }
                    else
                    {
                        Uri data = Uri.parse("tel:"+currentMyItem.getPhone());
                        intent.setData(data);
                        startActivity(intent);
                    }
                }
            });

            layout_website.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if (currentMyItem.getWebsite().length() == 0)
                    {
                        layout_website.setClickable(false);
                    }
                    else
                    {
                        Uri uri = Uri.parse(currentMyItem.getWebsite());
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    public void setDetailFragment(MyItem myItem)
    {
        this.currentMyItem = myItem;
    }
}

