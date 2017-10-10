package com.example.one.feezhomeful;

import android.app.Activity;
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
 * Created by one on 30/08/2017.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    EditText searchText;
    Button searchButton;
    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    private CameraPosition mCameraPosition;
    private final LatLng mDefaultLocation = new LatLng(-37.815152, 144.955895);
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private static final int DEFAULT_ZOOM = 50;
    // The entry point to Google Play services, used by the Places API and Fused Location Provider.
    private GoogleApiClient mGoogleApiClient;
    //shelterList local storage
    private List<Shelter> shelterList;
    private List<Food> foodList;
    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;
    //cluster on the map
    private ClusterManager<MyItem> _clusterManager;
    //Container for Shelter Item & Food
    Map<MyItem, Shelter> itemShelter = new HashMap<>();
    Map <MyItem, Food> itemFood = new HashMap<>();
    List<Shelter> currentShelters = new ArrayList<>();
    List<Food> currentFoods = new ArrayList<>();
    //current MyItem in the Map
    private MyItem currentMyItem;
    //cameraPosition
    final CameraPosition[] mPreviousCameraPosition = {null};
    //Searched Location as myItem
    private List<MyItem> myItemsList;
    //the location which is selected by SearchToList
    private MyItem selectedItem;
    //store all of shelters and foods from online
    private List<Shelter> allFemaleShelters = new ArrayList<>();
    private List<Food> allFoods = new ArrayList<>();
    private FragmentActivity myContext;


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mView = inflater.inflate(R.layout.map_fragment, container, false);
        LocationRepo locationRepo = new LocationRepo(getContext());
        allFemaleShelters = locationRepo.getFemaleShelterList();
        allFoods = locationRepo.getFoodList();
//    allFoods = bundle.getParcelableArrayList("Foods");

        try {
            MapsInitializer.initialize(getActivity());
        } catch (Exception e) {
            // TODO handle this situation
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, final Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) mView.findViewById(R.id.map_view);
        if(mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
        searchButton = (Button)mView.findViewById(R.id.search_button);
        searchText = (EditText) mView.findViewById(R.id.search_Text);
        searchText.setBackgroundColor(Color.WHITE);
        searchText.bringToFront();
        searchButton.bringToFront();
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String result = searchText.getText().toString();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                search(result);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
//                if( myItemsList!=null) {
//                    SearchToListFragment searchToListFragment = new SearchToListFragment();
//                    searchToListFragment.setItemList(myItemsList);
//                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                    ft.replace(R.id.content,searchToListFragment);
//                    ft.addToBackStack(null);
//                    ft.commit();
//
//                }else if (searchText.getText().toString().isEmpty()){
//                    SearchToListFragment searchToListFragment = new SearchToListFragment();
//                    FragmentTransaction ft = getFragmentManager().beginTransaction();
//                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                    ft.replace(R.id.content,searchToListFragment);
//                    //   ft.addToBackStack(null);
//                    ft.commit();
//                }else{
//                    Toast.makeText(getContext(),"No searched Restult",Toast.LENGTH_SHORT).show();
//                }
            }
        });
//    searchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
//                    actionId == EditorInfo.IME_ACTION_DONE ||
//                    actionId == EditorInfo.IME_ACTION_GO ||
//                    event.getAction() == KeyEvent.ACTION_DOWN &&
//                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//
//                // hide virtual keyboard
//                InputMethodManager imm= (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
////                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
//
//                new SearchClicked(searchText.getText().toString()).execute();
//                searchText.setText("", TextView.BufferType.EDITABLE);
//                return true;
//            }
//            return false;
//        }
//    });
    }
    protected void search(String input) {
        String lowerSearch = input.toLowerCase();
        Boolean hasResult = false;
        myItemsList = new ArrayList<>();
        mGoogleMap.clear();
        setUpCluster();
        for(Shelter s : allFemaleShelters){
            String name = s.getName();
            if(name.toLowerCase().contains(lowerSearch))
            {
                addItemInCluster((Object) s,R.drawable.ic_icon_bed_red);
                this.currentMyItem  = new MyItem(Double.parseDouble(s.getLatitude()), Double.parseDouble(s.getLongitude()));
                currentMyItem.setWhat(s.getWhat());
                currentMyItem.setName(s.getName());
                currentMyItem.setAddress_1(s.getAddress_1());
                currentMyItem.setAddress_2(s.getAddress_2());
                currentMyItem.setWho(s.getWho());
                currentMyItem.setPhone(s.getPhone());
                currentMyItem.setWebsite(s.getWebsite());
                currentMyItem.setIconId(R.drawable.ic_icon_bed_red);
                currentMyItem.setTimetable(s.getTimetable());
                myItemsList.add(currentMyItem);
                hasResult = true;
            }
        }
        for(Food f : allFoods){
            String name = f.getName();
            if(name.toLowerCase().contains(lowerSearch))
            {
                addItemInCluster((Object) f,R.drawable.ic_food_red);
                this.currentMyItem  = new MyItem(Double.parseDouble(f.getLatitude()), Double.parseDouble(f.getLongitude()));
                currentMyItem.setWhat(f.getWhat());
                currentMyItem.setName(f.getName());
                currentMyItem.setAddress_1(f.getAddress_1());
                currentMyItem.setAddress_2(f.getAddress_2());
                currentMyItem.setWho(f.getWho());
                currentMyItem.setPhone(f.getPhone());
                currentMyItem.setWebsite(f.getWebsite());
                currentMyItem.setIconId(R.drawable.ic_food_red);
                currentMyItem.setTimetable(f.getTimetable());
                myItemsList.add(currentMyItem);
                hasResult = true;
            }
        }
        if(!hasResult){
            Toast.makeText(getContext(),"No result",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),"this is result",Toast.LENGTH_SHORT).show();
        }
        showItemInfoWindow();
        zoomInCluster();

        _clusterManager.cluster();
//
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
//        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

    }
//    private class SearchClicked extends AsyncTask<Void, Void, Boolean> {
//        private String toSearch;
//        private Address address;
//
//        public SearchClicked(String toSearch) {
//            this.toSearch = toSearch;
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//
//            try {
//                Geocoder geocoder = new Geocoder(getContext(), Locale.UK);
//                List<Address> results = geocoder.getFromLocationName(toSearch, 1);
//
//                if (results.size() == 0) {
//                    return false;
//                }
//                address = results.get(0);
//                // Now do something with this GeoPoint:
//                Barcode.GeoPoint p = new Barcode.GeoPoint((int) (address.getLatitude() * 1E6), (int) (address.getLongitude() * 1E6), (int) (address.getLongitude() * 1E6));
//
//            } catch (Exception e) {
//                Log.e("", "Something went wrong: ", e);
//                return false;
//            }
//            return true;
//        }
//    }

    public void showSearchedMap(){
        for(MyItem m : myItemsList){
            _clusterManager.addItem(m);
        }
    }

    @Override
    public void onAttach(Activity activity){
        myContext = (FragmentActivity)activity;
        super.onAttach(activity);
    }

    @Override
    public void onStart() {
        mGoogleApiClient.connect();
        Log.d("ConnectonStart", "Connected ");
        Log.d("ONstart", Boolean.toString(mGoogleApiClient.isConnected()));
        super.onStart();
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
    public void onMapReady(GoogleMap googleMap) {

        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
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



//        CameraPosition Mel = CameraPosition.builder().target(mDefaultLocation).zoom(5).bearing(0).build();
//        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Mel));
//        controlCameraMoving();

        initialMap();

        updateLocationUI();

//        getDeviceLocation();

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

    //private void downloadData(){
//    addShelterAsy();
//    addFoodAsy();
//}
    private void initialMap(){
        setUpCluster();

        if(myItemsList != null) {
            showSearchedMap();
            CameraPosition Mel = CameraPosition.builder().target(mDefaultLocation).zoom(12).bearing(0).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Mel));

        }else if(selectedItem !=null){
            _clusterManager.addItem(selectedItem);
            CameraPosition selectedPlace = CameraPosition.builder().target(selectedItem.getPosition()).zoom(16).bearing(0).tilt(45).build();
            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(selectedPlace));
        }
        else{
            addShelterInCluster();
            addFoodInCluster();
        }
        showItemInfoWindow();
        zoomInCluster();
        _clusterManager.cluster();
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
            itemShelter.put(currentMyItem,shelter);
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
            itemFood.put(currentMyItem,food);

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public class OwnIconRendered extends DefaultClusterRenderer<MyItem> {

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

//    public void addShelterAsy() {
//        new AsyncTask<Void, Void, List<Shelter>>() {
//
//            @Override
//            protected List<Shelter> doInBackground(Void... params) {
//                List<Shelter> s = RestClient.getShelter();
//
//
//                return s;
//            }
//
//            @Override
//            protected void onPostExecute(List<Shelter> input) {
//                allShelters = input;
//            }
//        }.execute();
//    }

    private void addShelterInCluster(){

        for(Shelter s : allFemaleShelters){
            LatLng shelterLocation = new LatLng(Double.parseDouble(s.getLatitude()),Double.parseDouble(s.getLongitude()));
//            MarkerOptions shelterMarkerOption = new MarkerOptions().zIndex(1000).position(shelterLocation).title(s.getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)).snippet(s.getWhat());
            addItemInCluster(s,R.drawable.ic_icon_bed_red);
            currentShelters.add(s);
        }
    }

    private void addFoodInCluster(){

        for(Food s : allFoods){
            LatLng shelterLocation = new LatLng(Double.parseDouble(s.getLatitude()),Double.parseDouble(s.getLongitude()));
            MarkerOptions foodMarker = new MarkerOptions().zIndex(1000).position(shelterLocation).title(s.getName()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).snippet(s.getWhat());
            // mMap.addMarker(foodMarker);
            addItemInCluster(s,R.drawable.ic_food_red);
            currentFoods.add(s);
        }
    }

//    public void addFoodAsy() {
//        new AsyncTask<Void, Void, List<Food>>() {
//
//            @Override
//            protected List<Food> doInBackground(Void... params) {
//                List<Food> s = RestClient.getFood();
//
////                ArrayList<Food> foods = new ArrayList<Food>(s);
////                MapBottomSheetDialogFragment mbsdFragment = new MapBottomSheetDialogFragment();
////                Bundle bundle = new Bundle();
////                bundle.putParcelableArrayList("Foods",foods);
////                mbsdFragment.setArguments(bundle);
//                return s;
//            }
//
//            @Override
//            protected void onPostExecute(List<Food> input) {
//
//                allFoods = input;
//            }
//        }.execute();
//    }

    public void controlCameraMoving(){
        mGoogleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition position = mGoogleMap.getCameraPosition();
                if(mPreviousCameraPosition[0] == null || mPreviousCameraPosition[0].zoom != position.zoom) {
                    mPreviousCameraPosition[0] = mGoogleMap.getCameraPosition();
                    _clusterManager.cluster();
                }
            }
        });
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
//            private void updateLocationUI() {
//                if (mGoogleMap == null) {
//                    return;
//                }
//
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//                if (ContextCompat.checkSelfPermission(getContext(),
//                        android.Manifest.permission.ACCESS_FINE_LOCATION)
//                        == PackageManager.PERMISSION_GRANTED) {
//                    mLocationPermissionGranted = true;
//                } else {
//                    ActivityCompat.requestPermissions(this,
//                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//                }
//
//                if (mLocationPermissionGranted) {
//                    mMap.setMyLocationEnabled(true);
//                    mMap.getUiSettings().setMyLocationButtonEnabled(true);
//                } else {
//                    mMap.setMyLocationEnabled(false);
//                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
//                    mLastKnownLocation = null;
//                }
//            }
    /**
     *Request the permission and grant it.
     */
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
                    Toast.makeText(getContext(), "Please grant the permission", Toast.LENGTH_SHORT).show();
                }
            }
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



    public void setSeletedItem(MyItem selectedItem){
        this.selectedItem = selectedItem;
    }

    public void setSelectedItemList(List<MyItem> myItemsList){
        this.myItemsList = myItemsList;
    }
}