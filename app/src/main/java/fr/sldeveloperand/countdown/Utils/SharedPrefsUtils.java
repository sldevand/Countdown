package fr.sldeveloperand.countdown.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

import fr.sldeveloperand.countdown.R;

public class SharedPrefsUtils {

    public static SharedPreferences prefs;

    public static void init(Context ctx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static Date getDeadlineFromPrefs(Context ctx) {
        long time = prefs.getLong(ctx.getResources().getString(R.string.date_filter_key),0);
        return new Date(time);
    }

    public static void setDeadlineToPrefs(Context ctx, long time) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(ctx.getResources().getString(R.string.date_filter_key),time);
        editor.apply();
    }

    public static String getEventNameFromPrefs(Context ctx) {
        return prefs.getString(ctx.getResources().getString(R.string.event_name_key),"");
    }
}
