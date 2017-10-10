package com.example.one.feezhomeful;

/**
 * Created by one on 30/08/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by one on 26/08/2017.
 */

public class SearchToListFragment extends Fragment {

    private ListView listView;
    private LocationAdapter locationAdapter;
    private TextView searchText;
    private Button searchButton;
    //this is selected items from map
    private List<MyItem> itemList;
    private ArrayList<Shelter> allShelters;
    private ArrayList<Food> allFoods;
    private List<MyItem> allItemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_search_to_list,null);
        listView = (ListView) view.findViewById(R.id.listView_search_to_list);
        searchButton = (Button) view.findViewById(R.id.show_map_button);
        searchText = (TextView) view.findViewById(R.id.search_Title);


        //get all foods and shelters
        LocationRepo locationRepo = new LocationRepo(getContext());
//        allShelters = locationRepo.getShelterList();
        allFoods = locationRepo.getFoodList();
        allItemList = new ArrayList<MyItem>();
        for(Shelter s : allShelters){
            changeShelterToMyItem(s);
        }
        for(Food f : allFoods){
            changeFoodToMyItem(f);
        }


        if(itemList != null) {
            locationAdapter = new LocationAdapter(getContext(), R.layout.list_item_search_to_list, itemList);
            listView.setAdapter(locationAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MyItem myItem = itemList.get(position);
                    MapFragment mapFragment = new MapFragment();
                    mapFragment.setSeletedItem(myItem);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                    ft.replace(R.id.content,mapFragment);
                    ft.commit();
                }
            });
        }else if (allItemList.size()!=0){
            locationAdapter = new LocationAdapter(getContext(), R.layout.list_item_search_to_list, allItemList);
            listView.setAdapter(locationAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MyItem myItem = allItemList.get(position);
                    MapFragment mapFragment = new MapFragment();
                    mapFragment.setSeletedItem(myItem);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.replace(R.id.content,mapFragment);
                    ft.commit();
                }
            });
        }else{
            Toast.makeText(getContext(),"No Content",Toast.LENGTH_SHORT).show();
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = searchText.getText().toString();
                search(result);
            }
        });




        return view;
    }
    private void changeFoodToMyItem(Food f){
        MyItem currentMyItem = new MyItem(Double.parseDouble(f.getLatitude()),Double.parseDouble(f.getLongitude()));
        currentMyItem.setWhat(f.getWhat());
        currentMyItem.setName(f.getName());
        currentMyItem.setAddress_1(f.getAddress_1());
        currentMyItem.setAddress_2(f.getAddress_2());
        currentMyItem.setWho(f.getWho());
        currentMyItem.setPhone(f.getPhone());
        currentMyItem.setWebsite(f.getWebsite());
        currentMyItem.setIconId(R.drawable.ic_map_food);
        currentMyItem.setTimetable(f.getTimetable());
        allItemList.add(currentMyItem);
    }
    private void changeShelterToMyItem(Shelter f){
        MyItem currentMyItem = new MyItem(Double.parseDouble(f.getLatitude()),Double.parseDouble(f.getLongitude()));
        currentMyItem.setWhat(f.getWhat());
        currentMyItem.setName(f.getName());
        currentMyItem.setAddress_1(f.getAddress_1());
        currentMyItem.setAddress_2(f.getAddress_2());
        currentMyItem.setWho(f.getWho());
        currentMyItem.setPhone(f.getPhone());
        currentMyItem.setWebsite(f.getWebsite());
        currentMyItem.setIconId(R.drawable.ic_map_shelter);
        currentMyItem.setTimetable(f.getTimetable());
        allItemList.add(currentMyItem);
    }



    private void search(String input){
        String lowerSearch = input.toLowerCase();
        List<MyItem> resultList = new ArrayList<>();
        for(MyItem m : allItemList){
            String name = m.getName();
            if(name.toLowerCase().contains(lowerSearch)){
                resultList.add(m);
            }
        }
        locationAdapter = new LocationAdapter(getContext(), R.layout.list_item_search_to_list, resultList);
        listView.setAdapter(locationAdapter);
    }

    public void setItemList(List<MyItem> itemList){
        this.itemList = itemList;
    }


}

