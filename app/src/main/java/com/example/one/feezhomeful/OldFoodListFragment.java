package com.example.one.feezhomeful;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.one.feezhomeful.TestLazyTopMenu.DesignListItemAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by one on 31/08/2017.
 */

public class OldFoodListFragment extends Fragment{

    ExpandableListView expandableListView;
    View vMain;
    private ImageView welcomeImage;
    private ListView lvProduct;
    private TextView listTitle;
    private EditText searchText;
    private Button showOnMap;
    private ArrayList<Food> allFoodList,allOnlineFoodList;
    //current MyItem in the Map
    private MyItem currentMyItem;
    //Searched Location as myItem
    private List<MyItem> myItemsList;
    private ImageButton searchButton;
    private OneExpandAdapter myAdapter;
    private List<MyItem> resultList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_search_to_list, container, false);
         lvProduct = (ListView) vMain.findViewById(R.id.listView_search_to_list);
        listTitle = (TextView)vMain.findViewById(R.id.search_Title);
        showOnMap = (Button)vMain.findViewById(R.id.show_map_button);
        searchText= (EditText)vMain.findViewById(R.id.search_text_list);
        searchButton = (ImageButton)vMain.findViewById(R.id.search_button_list);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = searchText.getText().toString();
                search(result);
            }
        });

        requestData(inflater);

        SharedPreferences preferences = getActivity().getSharedPreferences(
                "currentGender", Context.MODE_PRIVATE);
        Boolean flag = preferences.getBoolean("Gender",true);

        if(flag) {
            listTitle.setText("Male Food List");
        }else{
            listTitle.setText("Female Food List");
        }




        showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(resultList.size() == 0){
                    Toast.makeText(getContext(),"No Food",Toast.LENGTH_LONG).show();
                }else{
                    MapFragment mapFragment = new MapFragment();
                    mapFragment.setSelectedItemList(resultList);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.replace(R.id.content,mapFragment);
                    ft.commit();
                }
            }
        });
        return vMain;
    }

    private void search(String input){
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
        String lowerSearch = input.toLowerCase();
        resultList = new ArrayList<>();
        for(MyItem myItem : myItemsList){
            String name = myItem.getName();
            String suburb = myItem.getSuburb();
            if(name.toLowerCase().contains(lowerSearch)||suburb.toLowerCase().contains(lowerSearch)){
                resultList.add(myItem);
            }
        }
        myAdapter = new OneExpandAdapter(getContext(),getFragmentManager(),resultList);
        lvProduct.setAdapter(myAdapter);
    }


    private void requestData(LayoutInflater inflater) {
        LocationRepo locationRepo = new LocationRepo(getContext());
//        allFoodList = locationRepo.getFoodList();
        allOnlineFoodList = locationRepo.getFoodOnlineDBList();
        myItemsList = new ArrayList<>();
        resultList = new ArrayList<>();
        String currentWeek = getCurrentWeekDay();
        String currentTime = getCurrentTime();

        ArrayList<Food> openFoodList = new ArrayList<>();
        ArrayList<Food> closeFoodList = new ArrayList<>();
        int now = Integer.parseInt(currentTime);

        ArrayList<HashMap<String, String>> datas = new ArrayList<HashMap<String, String>>();
        for (Food nowFood : allOnlineFoodList) {
            String openStatus = nowFood.getTimetable().get(currentWeek);

            if (!openStatus.equals("Closed") && !openStatus.equals("N/A")) {
                String[] time = openStatus.trim().split(",");
                String[] newStTime = time[0].trim().split(":");

                int end = 24;
                int start = Integer.parseInt(newStTime[0]);
                if (time.length == 2) {
                    String[] newEndTime = time[1].trim().split(":");
                    end = Integer.parseInt(newEndTime[0].trim());
                }
                if (now >= start && now <= end) {
                    openFoodList.add(nowFood);
                } else {
                    closeFoodList.add(nowFood);
                }

            } else {
                closeFoodList.add(nowFood);
            }
        }
            for (Food s : allFoodList) {
                for(Food closeFood : closeFoodList){

                if (s.getSuburb().equals(closeFood.getSuburb()) && s.getName().equals(closeFood.getName())) {
                    this.currentMyItem = new MyItem(Double.parseDouble(s.getLatitude()), Double.parseDouble(s.getLongitude()));
                    currentMyItem.setWhat(s.getWhat());
                    currentMyItem.setName(s.getName());
                    currentMyItem.setAddress_1(s.getAddress_1());
                    currentMyItem.setAddress_2(s.getAddress_2());
                    currentMyItem.setWho(s.getWho());
                    currentMyItem.setPhone(s.getPhone());
                    currentMyItem.setWebsite(s.getWebsite());
                    currentMyItem.setSuburb(s.getSuburb());
                    currentMyItem.setIconId(R.drawable.ic_food_red);
                    currentMyItem.setTimetable(s.getTimetable());
                    currentMyItem.setOpenStatus(false);
                    myItemsList.add(currentMyItem);
                }
            }
        }

        for (Food s : allFoodList) {

            for(Food opFood : openFoodList){
                if (s.getSuburb().equals(opFood.getSuburb()) && s.getName().equals(opFood.getName())) {
                    this.currentMyItem = new MyItem(Double.parseDouble(s.getLatitude()), Double.parseDouble(s.getLongitude()));
                    currentMyItem.setWhat(s.getWhat());
                    currentMyItem.setName(s.getName());
                    currentMyItem.setAddress_1(s.getAddress_1());
                    currentMyItem.setAddress_2(s.getAddress_2());
                    currentMyItem.setWho(s.getWho());
                    currentMyItem.setPhone(s.getPhone());
                    currentMyItem.setWebsite(s.getWebsite());
                    currentMyItem.setSuburb(s.getSuburb());
                    currentMyItem.setIconId(R.drawable.ic_food_red);
                    currentMyItem.setTimetable(s.getTimetable());
                    currentMyItem.setOpenStatus(true);
                    myItemsList.add(currentMyItem);
                }
            }
        }

        resultList = myItemsList;
        DesignListItemAdapter adapter = new DesignListItemAdapter(inflater,myItemsList,getContext());
        lvProduct.setAdapter(adapter);

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


}
