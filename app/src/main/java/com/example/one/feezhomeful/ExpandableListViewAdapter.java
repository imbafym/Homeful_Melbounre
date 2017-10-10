package com.example.one.feezhomeful;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * Created by one on 31/08/2017.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    String[] groupsNames = {"Shelter1", "Shelter2", "Shelter3", "Shelter4"};
    String[][] childNames = {{"Boxing","Kick Boxing","Judo"},{"Desk","Laptop","phone"},{"shelter3 Name"},{"Shelter4 Name"}};

    Context context;

    public ExpandableListViewAdapter(Context context){
        this.context = context;
    }
    @Override
    public int getGroupCount() {
        return groupsNames.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childNames[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupsNames[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childNames[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView txtView = new TextView(context);
        txtView.setText(groupsNames[groupPosition]);
        txtView.setPadding(100,0,0,0);
        txtView.setTextSize(40);
        txtView.setTextColor(Color.BLUE);
        return txtView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView txtView = new TextView(context);
        txtView.setText(childNames[groupPosition][childPosition]);
        txtView.setPadding(100,0,0,0);
        txtView.setTextSize(30);
        txtView.setTextColor(Color.RED);
        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return  txtView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
