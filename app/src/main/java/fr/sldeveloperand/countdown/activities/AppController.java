package fr.sldeveloperand.countdown.activities;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import fr.sldeveloperand.countdown.R;
import fr.sldeveloperand.countdown.helpers.ColorHelper;
import fr.sldeveloperand.countdown.helpers.SharedPrefsHelper;
import fr.sldeveloperand.countdown.models.MyEvent;
import fr.sldeveloperand.countdown.models.MyEvents;

import static fr.sldeveloperand.countdown.helpers.DateHelper.shortStrFromDeadline;
import static fr.sldeveloperand.countdown.helpers.DateHelper.strFromDeadline;
import static fr.sldeveloperand.countdown.helpers.SharedPrefsHelper.getDeadlineFromPrefs;
import static fr.sldeveloperand.countdown.helpers.SharedPrefsHelper.getEventNameFromPrefs;
import static fr.sldeveloperand.countdown.helpers.SharedPrefsHelper.prefs;

public class AppController extends AppCompatActivity implements Observer {

    private Toolbar toolbar;

    private TextView tvDeadline;
    private TextView tvCountdown;
    private TextView tvEventName;
    private SimpleDateFormat df;
    private MyEvent myEvent;
    private MyEvents myEvents;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_controller);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvDeadline = findViewById(R.id.tvDeadline);
        tvCountdown = findViewById(R.id.tvCountDown);
        tvEventName = findViewById(R.id.tvEventName);


        SharedPrefsHelper.init(this);
        SharedPreferences.OnSharedPreferenceChangeListener listener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                        updateEvent();
                    }
                };
        prefs.registerOnSharedPreferenceChangeListener(listener);
        df = new SimpleDateFormat(getResources().getString(R.string.frenchDateFormat), Locale.FRANCE);

        myEvents = new MyEvents();

        myEvent = new MyEvent();
        myEvent.addObserver(this);
        myEvents.addEvent(myEvent);


        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pink_bokeh_circles);
        ColorHelper.setToolbarColorWithBitmap(this, toolbar, bmp);
        updateEvent();

    }

    public void updateEvent(){
        myEvent.setName(getEventNameFromPrefs(this));
        myEvent.setDeadline(getDeadlineFromPrefs(this));

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

    @Override
    public void update(Observable o, Object arg) {
        String dateStr = strFromDeadline(this,myEvent.getDeadline());
        tvEventName.setText(myEvent.getName());
        tvDeadline.setText(df.format(myEvent.getDeadline()));
        tvCountdown.setText(dateStr);

        updateWidgets();

    }

    private void updateWidgets(){
        String dateStr = shortStrFromDeadline(this,myEvent.getDeadline());

        Context context = this;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.countdown_appwidget);
        ComponentName thisWidget = new ComponentName(context, CountdownWidgetProvider.class);
        remoteViews.setTextViewText(R.id.appwidget_name, myEvent.getName());
        remoteViews.setTextViewText(R.id.appwidget_date, dateStr);
        appWidgetManager.updateAppWidget(thisWidget, remoteViews);
    }
}
