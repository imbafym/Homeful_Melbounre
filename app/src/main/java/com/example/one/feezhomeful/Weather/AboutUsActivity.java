package com.example.one.feezhomeful.Weather;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.one.feezhomeful.R;

/**
 * Created by LHZ on 20/9/17.
 */

public class AboutUsActivity extends AppCompatActivity {
    private TextView title;
    private TextView copyright;
    private TextView para1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
    }
}
