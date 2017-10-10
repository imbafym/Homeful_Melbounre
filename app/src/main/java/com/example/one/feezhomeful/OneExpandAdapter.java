package com.example.one.feezhomeful;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by one on 31/08/2017.
 */



    /**
     * 点击item展开隐藏部分,再次点击收起
     * 只可展开一条记录
     */
    public class OneExpandAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> list;
        private int currentItem = -1; //用于记录点击的 Item 的 position，是控制 item 展开的核心
        private FragmentManager fragmentManager;
        //Searched Location as myItem
        private List<MyItem> myItemsList;
        public OneExpandAdapter(Context context
                                , FragmentManager fragmentManager, List<MyItem> myItemsList) {
            super();
            this.context = context;

            this.fragmentManager = fragmentManager;
            this.myItemsList = myItemsList;
        }

        @Override
        public int getCount() {
            return myItemsList.size();
        }

        @Override
        public Object getItem(int position) {
            return myItemsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.list_item_new_version, parent, false);
                holder = new ViewHolder();
                holder.showArea = (LinearLayout) convertView.findViewById(R.id.layout_showArea);
                holder.tvName = (TextView) convertView
                        .findViewById(R.id.tv_shelter_name);
                holder.tvDistance = (TextView) convertView
                        .findViewById(R.id.tv_distance);
                holder.textViewOpenStatus = (TextView)convertView.findViewById(R.id.textview_open_status);
                holder.tvDescription = (TextView) convertView.findViewById(R.id.tv_what_content);
                holder.imgDownButton = (ImageView)convertView.findViewById(R.id.img_down_button) ;
                holder.tvSuburb = (TextView)convertView.findViewById(R.id.tv_suburb);
                holder.tvDescription.setMovementMethod(ScrollingMovementMethod.getInstance());
                holder.btnShowMap = (ImageButton) convertView
                        .findViewById(R.id.btn_view_item_map);
                holder.btnShowDetail= (ImageButton) convertView
                        .findViewById(R.id.btn_show_detail);
                holder.hideArea = (LinearLayout) convertView.findViewById(R.id.layout_hideArea);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


//            HashMap<String, String> item = list.get(position);
            MyItem myItem = myItemsList.get(position);
            // 注意：我们在此给响应点击事件的区域（我的例子里是 showArea 的线性布局）添加Tag，为了记录点击的 position，我们正好用 position 设置 Tag
            holder.showArea.setTag(position);
            Double itemLatitude = myItem.getPosition().latitude;
            Double itemLongitude = myItem.getPosition().longitude;
            Double distance = 0.0;
            SharedPreferences sharedPreferences = context.getSharedPreferences("currentLocation",0);
            if(!sharedPreferences.getString("currentLatitude","").isEmpty()){
                Double currentLatitude = Double.parseDouble(sharedPreferences.getString("currentLatitude",""));
                Double currentLongitude = Double.parseDouble(sharedPreferences.getString("currentLongitude",""));
                distance = Distance(currentLongitude,currentLatitude,itemLongitude,itemLatitude);
            }else{
                distance = -1.0;
            }


            if(distance != -1.0) {
                String disInMeter = "";
                if (distance > 1000) {
                    Double d = Math.round(distance / 100d) / 100d;
                    disInMeter = Double.toString(d);
                } else {
                    Double d = Math.ceil(distance);
                    disInMeter = Double.toString(d);
                }
                holder.tvDistance.setText(disInMeter + " km");
            }
            holder.tvName.setText(myItem.getName());
            holder.tvDescription.setText(myItem.getWhat());
            holder.tvSuburb.setText(myItem.getSuburb());
            holder.tvDescription.scrollTo(0,0);
            if(myItem.isOpenStatus()) {
                holder.textViewOpenStatus.setText("Now Open");
            }else {
                holder.textViewOpenStatus.setText("Now Close");
            }



            final AnimationSet animSet = new AnimationSet(true);
            animSet.setInterpolator(new DecelerateInterpolator());
            animSet.setFillAfter(true);
            animSet.setFillEnabled(true);

            final AnimationSet antiAnimSet = new AnimationSet(true);
            antiAnimSet.setInterpolator(new DecelerateInterpolator());
            antiAnimSet.setFillAfter(true);
            antiAnimSet.setFillEnabled(true);

            AnimationSet startAnimSet = new AnimationSet(true);
            startAnimSet.setInterpolator(new DecelerateInterpolator());
            startAnimSet.setFillAfter(true);
            startAnimSet.setFillEnabled(true);

            final RotateAnimation animRotate = new RotateAnimation(0.0f, -180.0f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            final RotateAnimation antiAnimRotate = new RotateAnimation(-180.0f, 0.0f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            final RotateAnimation startAnimRotate = new RotateAnimation(0.0f, 0.0f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
            animRotate.setDuration(1000);
            animRotate.setFillAfter(true);
            antiAnimRotate.setDuration(1000);
            antiAnimRotate.setFillAfter(true);
            animSet.addAnimation(animRotate);
            antiAnimSet.addAnimation(antiAnimRotate);
             startAnimSet.addAnimation(startAnimRotate);
            //根据 currentItem 记录的点击位置来设置"对应Item"的可见性（在list依次加载列表数据时，每加载一个时都看一下是不是需改变可见性的那一条）
            if (currentItem == position) {
                holder.hideArea.setVisibility(View.VISIBLE);

            } else {
                holder.hideArea.setVisibility(View.GONE);
//                holder.imgDownButton.startAnimation(startAnimSet);

            }
//            }else{
//                holder.hideArea.setVisibility(View.GONE);
//
//            }

//            holder.imgDownButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });

            final ViewHolder finalHolder = holder;
            final ViewHolder finalHolder1 = holder;
            holder.showArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //用 currentItem 记录点击位置

                    int tag = (Integer) view.getTag();
                    if (tag == currentItem) { //再次点击
                        currentItem = -1;
                        finalHolder1.imgDownButton.startAnimation(antiAnimSet);//给 currentItem 一个无效值
                    } else {
                        currentItem = tag;
                        finalHolder.imgDownButton.startAnimation(animSet);
                    }
                    //通知adapter数据改变需要重新加载
                    notifyDataSetChanged(); //必须有的一步


                }
            });
            holder.btnShowMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyItem myItem = myItemsList.get(position);
                    SingleItemMapFragment mapFragment = new SingleItemMapFragment();
                    mapFragment.setSelectedItem(myItem);
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.replace(R.id.content,mapFragment);
                    ft.commit();


//                    Toast.makeText(context, "got to Map", Toast.LENGTH_SHORT).show();
                }
            });

            holder.btnShowDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MyItem myItem = myItemsList.get(position);
                    DetailFragment detailFragment = new DetailFragment();
                    detailFragment.setDetailFragment(myItem);
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    ft.addToBackStack(null);
                    ft.replace(R.id.content,detailFragment);
                    ft.commit();
//                    MyItem myItem = myItemsList.get(position);
//                    MapBottomSheetDialogFragment mbsdFragment = new MapBottomSheetDialogFragment();
//                    Bundle bundle = new Bundle();
//                    HashMap<String, String> timtable = myItem.getTimetable();
//                    bundle.putString("name", myItem.getName());
//                    bundle.putString("what", myItem.getWhat());
//                    bundle.putString("address1", myItem.getAddress_1());
//                    bundle.putString("address2", myItem.getAddress_2());
//                    bundle.putString("phone", myItem.getPhone());
//                    bundle.putString("who", myItem.getWho());
//                    bundle.putString("website", myItem.getWebsite());
//                    bundle.putString("monday", timtable.get("monday"));
//                    bundle.putString("tuesday", timtable.get("tuesday"));
//                    bundle.putString("wednesday", timtable.get("wednesday"));
//                    bundle.putString("thursday", timtable.get("thursday"));
//                    bundle.putString("friday", timtable.get("friday"));
//                    bundle.putString("saturday", timtable.get("saturday"));
//                    bundle.putString("sunday", timtable.get("sunday"));
//                    mbsdFragment.setArguments(bundle);
//                    mbsdFragment.show(fragmentManager, "dialog");
                }
            });

//            holder.tvName.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(context, "hehe", Toast.LENGTH_SHORT).show();
//                }
//            });
            return convertView;
        }

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

        private static final class ViewHolder {
            private LinearLayout showArea;
            private TextView tvName,tvDistance,tvSuburb;
            private TextView tvDescription;
            private ImageButton btnShowMap;
            private ImageButton btnShowDetail;
            private LinearLayout hideArea;
            private TextView textViewOpenStatus;
            private ImageView imgDownButton;
        }
    }


