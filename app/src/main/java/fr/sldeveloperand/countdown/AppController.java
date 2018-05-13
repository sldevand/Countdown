package fr.sldeveloperand.countdown;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static fr.sldeveloperand.countdown.SharedPrefsTools.getDeadlineFromPrefs;
import static fr.sldeveloperand.countdown.SharedPrefsTools.getEventNameFromPrefs;
import static fr.sldeveloperand.countdown.SharedPrefsTools.prefs;

public class AppController extends AppCompatActivity {

    private static final int ONE = 1;
    private static final int TWELVE=12;

    TextView tvDeadline,tvCountdown,tvEventName;
    SimpleDateFormat df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_controller);
        Toolbar toolbar = findViewById(R.id.toolbar);
        SharedPrefsTools.init(this);
        setSupportActionBar(toolbar);
        df=new SimpleDateFormat(getResources().getString(R.string.frenchDateFormat), Locale.FRANCE);

        tvDeadline = findViewById(R.id.tvDeadline);
        tvCountdown = findViewById(R.id.tvCountDown);
        tvEventName = findViewById(R.id.tvEventName);
        refreshDisplay();

        SharedPreferences.OnSharedPreferenceChangeListener listener =
            new SharedPreferences.OnSharedPreferenceChangeListener() {
                public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                    Log.e("sharedPrefsChanged",key);
                    refreshDisplay();
                }
            };
        prefs.registerOnSharedPreferenceChangeListener(listener);

    }

    public void refreshDisplay(){
        String eventName = getEventNameFromPrefs(this);
        Date deadline = getDeadlineFromPrefs(this);

        Date now = new Date();
        MyDate diff = calculateDiffDates(now,deadline);

        StringBuilder builder = new StringBuilder();

        if(diff.getMonths()>0){
            builder.append(diff.getMonths()).append(" mois");
        }

        if(diff.getDays()>0){
            builder.append(" ").append(diff.getDays()).append(" jour");
            if(diff.getDays()>1) builder.append("s");
        }

        tvEventName.setText(eventName);
        tvDeadline.setText(df.format(deadline));
        tvCountdown.setText(builder.toString());

    }

    public MyDate calculateDiffDates(Date date1, Date date2){
        Calendar calStr1 = Calendar.getInstance();
        Calendar calStr2 = Calendar.getInstance();
        Calendar calStr0;

        int nbMonths;
        int nbYears;
        long nbDays;

        if (date1.equals(date2)) {
            return null;
        }

        calStr1.setTime(date1);
        calStr2.setTime(date2);

        nbMonths = 0;
        while (calStr1.before(calStr2)) {
            calStr1.add(GregorianCalendar.MONTH, ONE);
            if (calStr1.before(calStr2) || calStr1.equals(calStr2)) {
                nbMonths++;
            }
        }
        nbYears = (nbMonths / TWELVE);
        nbMonths = (nbMonths - (nbYears * TWELVE));

        calStr0 = Calendar.getInstance();
        calStr0.setTime(date1);
        calStr0.add(GregorianCalendar.YEAR, nbYears);
        calStr0.add(GregorianCalendar.MONTH, nbMonths);
        nbDays = (calStr2.getTimeInMillis() - calStr0.getTimeInMillis()) / 86400000;

        return new MyDate(nbMonths,nbDays);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app_controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent i = new Intent(this,SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
