package com.example.one.feezhomeful.TestLazyTopMenu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by one on 9/09/2017.
 */

public class NewShelterListFragment extends BaseFragment {

    private TextView mTextView;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        mTextView = new TextView(getActivity());
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setTextSize(20);
        mTextView.setTextColor(Color.BLACK);

        return mTextView;
    }

    @Override
    public void initData() {
        Toast.makeText(getActivity(), "加载了最新数据", Toast.LENGTH_SHORT).show();
        mTextView.setText("最新视图");
    }


}
