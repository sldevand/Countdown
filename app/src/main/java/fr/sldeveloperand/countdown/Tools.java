package fr.sldeveloperand.countdown;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.Date;

public class Tools {

    public static SharedPreferences prefs;


    public static void init(Context ctx){
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

    }


    public static Date getDeadlineFromPrefs(Context ctx){
        long time = prefs.getLong(ctx.getResources().getString(R.string.date_filter_key),0);

        return new Date(time);
    }

    public static void setDeadlineToPrefs(Context ctx,long time){

        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong(ctx.getResources().getString(R.string.date_filter_key),time);
        editor.apply();
    }

    public static String getEventNameFromPrefs(Context ctx){

        return prefs.getString(ctx.getResources().getString(R.string.event_name_key),"");
    }
}
