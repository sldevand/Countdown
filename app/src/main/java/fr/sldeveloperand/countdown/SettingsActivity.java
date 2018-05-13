package fr.sldeveloperand.countdown;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.prefs.Preferences;

import static fr.sldeveloperand.countdown.Tools.*;


public class SettingsActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().add(android.R.id.content,new PrefsFragment()).commit();
        ActionBar bar=getSupportActionBar();
        if(bar!=null) {
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public static class PrefsFragment extends PreferenceFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_screen);

            Preference btnDateFilter = (Preference) findPreference(getResources().getString(R.string.date_filter_key));

            btnDateFilter.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

                @Override
                public boolean onPreferenceClick(Preference preference) {
                    // Use the current date as the default date in the picker
                    final Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        new DatePickerDialog(getContext(),PrefsFragment.this, year, month, day).show();
                    }
                    return false;
                }
            });
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {

            Date date = new Date(year-1900,month,day);
            long time = date.getTime();

            setDeadlineToPrefs(getActivity(),time);
            getActivity().finish();

        }

    }


}