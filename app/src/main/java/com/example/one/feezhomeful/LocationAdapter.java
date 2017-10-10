package com.example.one.feezhomeful;

/**
 * Created by one on 30/08/2017.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by one on 26/08/2017.
 */

public class LocationAdapter extends ArrayAdapter<MyItem> {
    private int resourceId;

    public LocationAdapter(Context context, int resource, List<MyItem> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        MyItem myItem = getItem(position);
        View myItemView = LayoutInflater.from(getContext()).inflate(resourceId,null);

        ImageView icon = (ImageView)myItemView.findViewById(R.id.imageView_icon_list);
        TextView name = (TextView)myItemView.findViewById(R.id.textView_name_list);

        icon.setImageResource(myItem.getIconId());
        name.setText(myItem.getName());

        return myItemView;
    }













}
