package fr.sldeveloperand.countdown;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

public class SharedPrefsTools {

    static SharedPreferences prefs;

    static void init(Context ctx){
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    static Date getDeadlineFromPrefs(Context ctx){
        long time = prefs.getLong(ctx.getResources().getString(R.string.date_filter_key),0);
        return new Date(time);
    }

    static void setDeadlineToPrefs(Context ctx,long time){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(ctx.getResources().getString(R.string.date_filter_key),time);
        editor.apply();
    }

    static String getEventNameFromPrefs(Context ctx){
        return prefs.getString(ctx.getResources().getString(R.string.event_name_key),"");
    }
}
