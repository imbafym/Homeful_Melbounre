package com.example.one.feezhomeful;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by one on 31/08/2017.
 */

public class SingleItemMapFragment extends Fragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    // The entry point to Google Play services, used by the Places API and Fused Location Provider.
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private View view;
    private static final LatLng MELBOURNE_CITY_CENTER = new LatLng(-37.81303878836988, 144.96597290039062);
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private static final int DEFAULT_ZOOM = 15;
    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;
    //cluster on the map
    private ClusterManager<MyItem> _clusterManager;
    //current MyItem in the Map
    private MyItem currentMyItem;
    //cameraPosition
    final CameraPosition[] mPreviousCameraPosition = {null};
    private final LatLng mDefaultLocation = new LatLng(-37.815152, 144.955895);
    private CameraPosition mCameraPosition;
    //the location which is selected by SearchToList
    private MyItem selectedItem;
    private FragmentActivity myContext;
    //if the current Toilet exist
    private Toilet currentToilet;
    //if the current Water exist
    private Water currentWater;
    //check the gender
    private boolean gender;
    //check the baby
    private boolean baby;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frament_single_item_on_map, container, false);
        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            // TODO handle this situation
        }

        SharedPreferences preferences = getActivity().getSharedPreferences(
                "currentGender", Context.MODE_PRIVATE);
         gender = preferences.getBoolean("Gender",true);
        SharedPreferences babyPre = getActivity().getSharedPreferences(
                "baby", Context.MODE_PRIVATE);
         baby = babyPre.getBoolean("baby",false);

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) view.findViewById(R.id.map_view);
        if (mMapView != null) {
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
    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mGoogleMap.setMyLocationEnabled(true);
        } else {
            getActivity().requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 12345);
            // Show rationale and request permission.
        }
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        /*
         *change the compass icon on the map
         *
         */
        if (mMapView != null &&
                mMapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("5"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }
//        CameraPosition itemPlace = CameraPosition.builder().target(MELBOURNE_CITY_CENTER).zoom(12).bearing(0).build();
//        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(itemPlace));
        initialMap();
//        updateLocationUI();

//        getDeviceLocation();

    }

    private void initialMap(){
        setUpCluster();

        if(selectedItem !=null){
            _clusterManager.addItem(selectedItem);
            CameraPosition selectedPlace = CameraPosition.builder().target(selectedItem.getPosition()).zoom(14).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(selectedPlace));
        }else if(currentToilet!=null){
            LatLng latLng = new LatLng(Double.parseDouble(currentToilet.getLatitude()),Double.parseDouble(currentToilet.getLongitude()));
            mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(currentToilet.getName()).snippet("Baby: " + currentToilet.getBaby())).showInfoWindow();
            CameraPosition selectedPlace = CameraPosition.builder().target(latLng).zoom(14).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(selectedPlace));
        }else if(currentWater!=null){
            LatLng latLng = new LatLng(Double.parseDouble(currentWater.getLatitude()),Double.parseDouble(currentWater.getLongitude()));
            mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(currentWater.getName())).showInfoWindow();
            CameraPosition selectedPlace = CameraPosition.builder().target(latLng).zoom(14).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(selectedPlace));
        }
        else{
            Toast.makeText(getContext(),"No place found",Toast.LENGTH_SHORT ).show();
        }

        showItemInfoWindow();
        zoomInCluster();
        _clusterManager.cluster();
    }

    private void zoomInCluster(){
        _clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<MyItem>() {
            @Override
            public boolean onClusterClick(final Cluster<MyItem> cluster) {
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        cluster.getPosition(), (float) Math.floor(mGoogleMap
                                .getCameraPosition().zoom + 1)), 300,
                        null);
                return true;
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 12345: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    try {
                        mGoogleMap.setMyLocationEnabled(true);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "No internet", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void setUpCluster(){
        _clusterManager = new ClusterManager<MyItem>(this.getContext(), mGoogleMap);
        mGoogleMap.setOnCameraIdleListener(_clusterManager);
        mGoogleMap.setOnInfoWindowClickListener(_clusterManager);
        mGoogleMap.setOnMarkerClickListener(_clusterManager);
        _clusterManager.setRenderer(new OwnIconRendered(getContext(),mGoogleMap,_clusterManager));
    }

    public void addItemInCluster(Object s, int iconId){
        if(s instanceof Shelter ) {
            Shelter shelter = (Shelter)s;
            this.currentMyItem  = new MyItem(Double.parseDouble(shelter.getLatitude()), Double.parseDouble(shelter.getLongitude()));
            currentMyItem.setWhat(shelter.getWhat());
            currentMyItem.setName(shelter.getName());
            currentMyItem.setAddress_1(shelter.getAddress_1());
            currentMyItem.setAddress_2(shelter.getAddress_2());
            currentMyItem.setWho(shelter.getWho());
            currentMyItem.setPhone(shelter.getPhone());
            currentMyItem.setWebsite(shelter.getWebsite());
            currentMyItem.setIconId(iconId);
            currentMyItem.setTimetable(shelter.getTimetable());
            _clusterManager.addItem(currentMyItem);

        }else if(s instanceof Food ){
            Food food = (Food)s;
            currentMyItem = new MyItem(Double.parseDouble(food.getLatitude()), Double.parseDouble(food.getLongitude()));
            currentMyItem.setWhat(food.getWhat());
            currentMyItem.setName(food.getName());
            currentMyItem.setAddress_1(food.getAddress_1());
            currentMyItem.setAddress_2(food.getAddress_2());
            currentMyItem.setWho(food.getWho());
            currentMyItem.setPhone(food.getPhone());
            currentMyItem.setWebsite(food.getWebsite());
            currentMyItem.setTimetable(food.getTimetable());
            currentMyItem.setIconId(iconId);
            _clusterManager.addItem(currentMyItem);


        }
    }

    private void showItemInfoWindow(){
        _clusterManager.setOnClusterItemInfoWindowClickListener(new ClusterManager.OnClusterItemInfoWindowClickListener<MyItem>() {
            @Override
            public void onClusterItemInfoWindowClick(MyItem myItem) {
                MapBottomSheetDialogFragment mbsdFragment = new MapBottomSheetDialogFragment();
                Bundle bundle = new Bundle();
                HashMap<String, String> timtable = myItem.getTimetable();
                bundle.putString("name", myItem.getName());
                bundle.putString("what", myItem.getWhat());
                bundle.putString("address1", myItem.getAddress_1());
                bundle.putString("address2", myItem.getAddress_2());
                bundle.putString("phone", myItem.getPhone());
                bundle.putString("who", myItem.getWho());
                bundle.putString("website", myItem.getWebsite());
                bundle.putString("monday", timtable.get("monday"));
                bundle.putString("tuesday", timtable.get("tuesday"));
                bundle.putString("wednesday", timtable.get("wednesday"));
                bundle.putString("thursday", timtable.get("thursday"));
                bundle.putString("friday", timtable.get("friday"));
                bundle.putString("saturday", timtable.get("saturday"));
                bundle.putString("sunday", timtable.get("sunday"));
                mbsdFragment.setArguments(bundle);
                mbsdFragment.show(myContext.getSupportFragmentManager(), "dialog");
            }
        });
    }



//    @Override
//    public void onStart() {
//        mGoogleApiClient.connect();
//        Log.d("ConnectonStart", "Connected ");
//        Log.d("ONstart", Boolean.toString(mGoogleApiClient.isConnected()));
//        super.onStart();
//    }
    public class OwnIconRendered extends DefaultClusterRenderer<MyItem> {
        private final IconGenerator mClusterIconGenerator = new IconGenerator(getContext());
        private BitmapDescriptor mBitmapMarker;

        public OwnIconRendered(Context context, GoogleMap map, ClusterManager<MyItem> clusterManager) {
            super(context, map, clusterManager);
        }

        @Override
        protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {

            mBitmapMarker = BitmapDescriptorFactory.fromResource(item.getIconId());
            markerOptions.icon(mBitmapMarker);
            markerOptions.title(item.getName());
            markerOptions.snippet(item.getWhat());
            super.onBeforeClusterItemRendered(item, markerOptions);
        }
        @Override
        protected void onClusterItemRendered(MyItem clusterItem, Marker marker) {
            super.onClusterItemRendered(clusterItem, marker);
        }


    }




    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        if (mLocationPermissionGranted) {
            mLastKnownLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
        }

        // Set the map's camera position to the current location of the device.
        if (mCameraPosition != null) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
        } else if (mLastKnownLocation != null) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
        } else {
            Log.d(TAG, "Current location is null. Using defaults.");
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }


    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mGoogleMap == null) {
            return;
        }

        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mGoogleMap.setMyLocationEnabled(false);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
    }


    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        Log.d("ConnectonStart", "Connected ");
        Log.d("ONstart", Boolean.toString(mGoogleApiClient.isConnected()));
        super.onStart();
    }

    @Override
    public void onAttach(Activity activity){
        myContext = (FragmentActivity)activity;
        super.onAttach(activity);
    }



    @Override
    public void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onResume() {
        mGoogleApiClient.connect();
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d("Connect", "Connected ");
        Log.d("onConnected", Boolean.toString(mGoogleApiClient.isConnected()));
    }


    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void setSelectedItem(MyItem myItem){
        this.selectedItem = myItem;
    }

    public void setCurrentToilet(Toilet toilet){
        this.currentToilet = toilet;
    }
    public void setCurrentWater(Water water){
        this.currentWater = water;
    }
}