
package com.example.one.feezhomeful;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.one.feezhomeful.Entity.Shelter;
import com.example.one.feezhomeful.TestLazyTopMenu.DesignListItemAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ShelterListFragment extends Fragment {
    ExpandableListView expandableListView;
    View vMain;
    private Button showOnMap;
    private ImageButton searchButton;
    private ImageView welcomeImage;
    private TextView welcomeText,listTitle;
    private  EditText searchText;
    private ArrayList<Shelter> allShelters;
    //current MyItem in the Map
    private MyItem currentMyItem;
    //Searched Location as myItem
    private List<MyItem> myItemsList;
    private ListView lvProduct;
    private OneExpandAdapter myAdapter;
    private List<MyItem> resultList;
    private LayoutInflater mInflater;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        //filter
        if(vMain == null) {

            vMain = inflater.inflate(R.layout.fragment_search_to_list, container, false);
            lvProduct = (ListView) vMain.findViewById(R.id.listView_search_to_list);
            showOnMap = (Button) vMain.findViewById(R.id.show_map_button);
            listTitle = (TextView) vMain.findViewById(R.id.search_Title);
            searchText = (EditText) vMain.findViewById(R.id.search_text_list);
            searchButton = (ImageButton) vMain.findViewById(R.id.search_button_list);
            this.mInflater = inflater;
            SharedPreferences preferences = getActivity().getSharedPreferences(
                    "currentGender", Context.MODE_PRIVATE);
            Boolean flag = preferences.getBoolean("Gender", true);

            if (flag) {
                listTitle.setText("Male Shelter List");
            } else {
                listTitle.setText("Female Shelter List");
            }
            requestData();

            searchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String result = searchText.getText().toString();
                    search(result);
                }
            });

            lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MyItem myItem = myItemsList.get(position);
                    DetailFragment detailFragment = new DetailFragment();
                    detailFragment.setDetailFragment(myItem);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();

                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.replace(R.id.new_content,detailFragment);
                    ft.commit();
                }
            });

            showOnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (resultList.size() == 0) {
                        Toast.makeText(getContext(), "No shelter", Toast.LENGTH_LONG).show();
                    } else {
                        MapFragment mapFragment = new MapFragment();
                        mapFragment.setSelectedItemList(resultList);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        ft.addToBackStack(null);
                        ft.replace(R.id.viewpager, mapFragment);
                        ft.commit();
                    }
                }
            });
        }
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
            Boolean isOpen = myItem.isOpenStatus();
            if(name.toLowerCase().contains(lowerSearch)||suburb.toLowerCase().contains(lowerSearch)||isOpen.toString().equals(lowerSearch)){
                resultList.add(myItem);
            }
        }
        DesignListItemAdapter adapter = new DesignListItemAdapter(this.mInflater,resultList,getContext());
        lvProduct.setAdapter(adapter);
    }

    private void requestData() {
        LocationRepo locationRepo = new LocationRepo(getContext());
//        SharedPreferences preferences = getActivity().getSharedPreferences(
//                "currentGender", Context.MODE_PRIVATE);
//        Boolean flag = preferences.getBoolean("Gender",true);
//        if (flag) {
//            allShelters = locationRepo.getMaleShelterList();
//        }else {
//            allShelters = locationRepo.getFemaleShelterList();
//        }

        /*
            using all shelters now, include both male and female
         */

        allShelters = locationRepo.getDatabaseShelterList();

        resultList = new ArrayList<>();
        myItemsList = new ArrayList<>();
        ArrayList<HashMap<String, String>> datas = new ArrayList<HashMap<String, String>>();
        for (Shelter s : allShelters) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("name", s.getName());
            item.put("what", s.getWhat());
            datas.add(item);
            this.currentMyItem  = new MyItem(Double.parseDouble(s.getLatitude()), Double.parseDouble(s.getLongitude()));
            currentMyItem.setWhat(s.getWhat());
            currentMyItem.setName(s.getName());
            currentMyItem.setAddress_1(s.getAddress_1());
            currentMyItem.setAddress_2(s.getAddress_2());
            currentMyItem.setWho(s.getWho());
            currentMyItem.setPhone(s.getPhone());
            currentMyItem.setSex(s.getSex());
            currentMyItem.setBaby(s.getChild());
            currentMyItem.setWebsite(s.getWebsite());
            currentMyItem.setSuburb(s.getSuburb());
            currentMyItem.setIconId(R.drawable.list_shelter);
            currentMyItem.setIconOnMap(R.drawable.ic_map_shelter);
            currentMyItem.setTimetable(s.getTimetable());
            currentMyItem.setOpenStatus(true);
            myItemsList.add(currentMyItem);
        }
        resultList = myItemsList;
//        OneExpandAdapter adapter = new OneExpandAdapter(getContext(),getFragmentManager(),myItemsList);
//        lvProduct.setAdapter(adapter);

        DesignListItemAdapter adapter = new DesignListItemAdapter(this.mInflater,resultList,getContext());
        lvProduct.setAdapter(adapter);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (vMain != null) {
            ((ViewGroup) vMain.getParent()).removeView(vMain);
        }
    }
}