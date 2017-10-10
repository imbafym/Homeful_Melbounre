package com.example.one.feezhomeful;

/**
 * Created by one on 30/08/2017.
 */

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by one on 25/08/2017.
 */

public class MapBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private BottomSheetBehavior mBehavior;
    private TextView tv_Title;
    private TextView tv_Details;
    private TextView tv_phone;
    private TextView tv_address;
    private TextView tv_website;
    private TextView tv_who;
    //    private TextView tv_spinnerDefaultValue;
    private Spinner spinner;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };
    Boolean isTextViewClicked = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = View.inflate(getContext(), R.layout.new_bottom_sheet_layout, null);
        tv_Title = (TextView) view.findViewById(R.id.tv_bottomSheet_Title);
        tv_Details = (TextView) view.findViewById(R.id.tv_bottomSheet_Details);
        tv_phone = (TextView) view.findViewById(R.id.tv_bottomSheet_Phone);
        tv_website = (TextView) view.findViewById(R.id.tv_bottomSheet_Website);
        tv_who = (TextView) view.findViewById(R.id.tv_bottomSheet_Who);
        tv_address = (TextView) view.findViewById(R.id.tv_bottomSheet_Address);

        spinner = (Spinner) view.findViewById(R.id.spinner1);
//        tv_spinnerDefaultValue = (TextView) spinner.findViewById(R.id.tv_spinner_defualt);

        String todayWeekDay = getCurrentWeekDay();
        Bundle bundle = getArguments();
        //initial spinner items
        List<String> cityList = new ArrayList<String>();
        String monday = bundle.getString("monday") == null ? "" : bundle.getString("monday");
        String tuesday = bundle.getString("tuesday") == null ? "" : bundle.getString("tuesday");
        String wednesday = bundle.getString("wednesday") == null ? "" : bundle.getString("wednesday");
        String thursday = bundle.getString("thursday") == null ? "" : bundle.getString("thursday");
        String friday = bundle.getString("friday") == null ? "" : bundle.getString("friday");
        String saturday = bundle.getString("saturday") == null ? "" : bundle.getString("saturday");
        String sunday = bundle.getString("sunday") == null ? "" : bundle.getString("sunday");
        cityList.add("Monday: " + monday);
        cityList.add("tuesday: " + tuesday);
        cityList.add("wednesday: " + wednesday);
        cityList.add("thursday: " + thursday);
        cityList.add("friday: " + friday);
        cityList.add("saturday: " + saturday);
        cityList.add("sunday: " + sunday);
//        String hints = bundle.getString(todayWeekDay)==null?"No timetable": bundle.getString(todayWeekDay);
//        tv_spinnerDefaultValue.setText(todayWeekDay + ": " + hints);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_dropdown_style, 0, cityList);
        spinner.setPrompt("See More");
//        spinner.setAdapter(adapter);
        spinner.setAdapter(new NothingSelectedSpinnerAdapter(adapter,
                R.layout.spinner_row_nothing_selected,
                // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                getActivity()));


        dialog.setContentView(view);
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setPeekHeight(1000);
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        tv_Title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isTextViewClicked){
//                    tv_Title.setMaxLines(2);
//                    isTextViewClicked = false;
//                }else{
//                    tv_Title.setMaxLines(Integer.MAX_VALUE);
//                    isTextViewClicked = true;
//                }
//            }
//        });
        tv_Title.setMovementMethod(new ScrollingMovementMethod());


        String name = bundle.getString("name");
        String what = bundle.getString("what");
        String who = bundle.getString("who");
        String phone = bundle.getString("phone");
        String addressPrefix = "";
        String website = "";
        if (bundle.getString("address1") != null && !bundle.getString("address1").isEmpty()) {
            addressPrefix = bundle.getString("address1");
        }
        String address = addressPrefix + " " + bundle.getString("address2");
        if (bundle.getString("website") != null && !bundle.getString("website").isEmpty()) {
            website = bundle.getString("website");
        }


        tv_Title.setText(name);
        tv_Details.setText(what);
        tv_phone.setText(phone);
        tv_who.setText(who);
        tv_address.setText(address);
        tv_website.setText(website);
        return dialog;
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

    @Override
    public void onStart()
    {
        super.onStart();
        //默认全屏展开
        mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

//    public void doclick(View v)
//    {
//        //点击任意布局关闭
//        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//    }

}