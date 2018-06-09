package fr.sldeveloperand.countdown;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import fr.sldeveloperand.countdown.Utils.ColorUtils;
import fr.sldeveloperand.countdown.Utils.DateUtils;
import fr.sldeveloperand.countdown.Utils.SharedPrefsUtils;

import static fr.sldeveloperand.countdown.Utils.SharedPrefsUtils.getDeadlineFromPrefs;
import static fr.sldeveloperand.countdown.Utils.SharedPrefsUtils.getEventNameFromPrefs;
import static fr.sldeveloperand.countdown.Utils.SharedPrefsUtils.prefs;

public class AppController extends AppCompatActivity {

    private Toolbar toolbar;

    TextView tvDeadline,tvCountdown,tvEventName;
    SimpleDateFormat df;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_controller);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvDeadline = findViewById(R.id.tvDeadline);
        tvCountdown = findViewById(R.id.tvCountDown);
        tvEventName = findViewById(R.id.tvEventName);

        SharedPrefsUtils.init(this);
        SharedPreferences.OnSharedPreferenceChangeListener listener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                        refreshDisplay();
                    }
                };
        prefs.registerOnSharedPreferenceChangeListener(listener);
        df = new SimpleDateFormat(getResources().getString(R.string.frenchDateFormat), Locale.FRANCE);

        refreshDisplay();
    }

    public void refreshDisplay(){
        String eventName = getEventNameFromPrefs(this);
        Date deadline = getDeadlineFromPrefs(this);

        Date now = new Date();
        Optional<MyDate> diffOpt = Optional.ofNullable(DateUtils.calculateDiffDates(now,deadline));

        StringBuilder builder = new StringBuilder();

        if(diffOpt.isPresent()) {
            MyDate diff = diffOpt.get();
            if ( diff.getMonths() > 0) {
                builder.append(diff.getMonths()).append(" ").append(getString(R.string.month));
            }

            if (diff.getDays() > 0) {
                builder.append(" ").append(diff.getDays()).append(" ").append(getString(R.string.day));
                if (diff.getDays() > 1) builder.append("s");
            }
        }

        tvEventName.setText(eventName);
        tvDeadline.setText(df.format(deadline));
        tvCountdown.setText(builder.toString());

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.pink_bokeh_circles);

        ColorUtils.setToolbarColorWithBitmap(this, toolbar, bmp);
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
