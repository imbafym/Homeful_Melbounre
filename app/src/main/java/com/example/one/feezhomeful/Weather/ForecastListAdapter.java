package com.example.one.feezhomeful.Weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.one.feezhomeful.R;

import java.util.List;

/**
 * Created by one on 19/09/2017.
 */

public class ForecastListAdapter extends ArrayAdapter<ForecastDay> {
    private LayoutInflater mInflater;
    private  int resourceId;
    private Context context;
    public ForecastListAdapter(Context context, int resource, List<ForecastDay> f) {
        super(context, resource,f);
        // TODO Auto-generated constructor stub
        resourceId = resource;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            //创建新的view视图.
            convertView = mInflater.inflate(resourceId, null); //see above, you can use the passed resource id.
        }

        ViewHolder holder = null;
        if(holder==null){
            holder = new ViewHolder();
            //查找每个ViewItem中,各个子View,放进holder中
            holder.temperature = (TextView) convertView.findViewById(R.id.tv_temperature);
            holder.weatherIconView = (ImageView) convertView.findViewById(R.id.tv_icon);
            holder.date = (TextView) convertView.findViewById(R.id.tv_date);
            //保存对每个显示的ViewItem中, 各个子View的引用对象
            convertView.setTag(holder);
        }
        else// I think this a bug, program can not run here!!!--linc2014.11.12
        {
            holder = (ViewHolder)convertView.getTag();
        }

        //获取当前要显示的数据
        ForecastDay forecastDay = getItem(position);
        setIconResources(holder,forecastDay.getIcon());
        holder.temperature.setText(forecastDay.getMinTemperature().toString()+" / " + forecastDay.getMaxTemperature().toString());

        holder.date.setText(forecastDay.getDate());

        return convertView;
    }

    private void setIconResources(ViewHolder viewHolder, String icon){
        int iconRes = R.drawable.sun_24;
        switch(icon) {
            case "clear-day" : iconRes = R.drawable.sun_24;
                break;
            case "clear-night" : iconRes =R.drawable.night_24;
                break;
            case "rain" : iconRes = R.drawable.rain_24;
                break;
            case "snow" : iconRes = R.drawable.snow_24;
                break;
            case "sleet" : iconRes = R.drawable.sleet_24;
                break;
            case "wind" : iconRes = R.drawable.wind_24;
                break;
            case "fog" : iconRes = R.drawable.fog_24;
                break;
            case "cloudy" : iconRes = R.drawable.clouds_24;
                break;
            case "partly-cloudy-day" : iconRes = R.drawable.partly_cloudy_day_24;
                break;
            case "partly-cloudy-night" : iconRes = R.drawable.partly_cloudy_day_24;
                break;
            case "hail" : iconRes = R.drawable.hail_24;
                break;
            case "thunderstorm" : iconRes = R.drawable.storm;
                break;
            case "tornado" : iconRes = R.drawable.storm;
                break;
        }

        viewHolder.weatherIconView.setImageResource(iconRes);
    }


    private static class ViewHolder
    {
        TextView temperature;
        TextView date;
        ImageView weatherIconView;
    }


}