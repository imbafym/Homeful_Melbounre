package com.example.one.feezhomeful;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

    /**
     *
     */
    public static final String SHOW_GUIDE = "showguide";

    /**
     *
     */
    public static void setBoolean(Context context, String key, boolean value) {
        //
        SharedPreferences preferences = context.getSharedPreferences(
                "preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     *
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(
                "preference", Context.MODE_PRIVATE);
        //
        return preferences.getBoolean(key, false);

    }
}
