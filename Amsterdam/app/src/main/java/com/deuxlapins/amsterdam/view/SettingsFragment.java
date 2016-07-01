package com.deuxlapins.amsterdam.view;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.deuxlapins.amsterdam.R;
import com.deuxlapins.amsterdam.util.Utils;
import com.deuxlapins.amsterdam.vo.TempertatureUnits;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {


    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.weather_settings);
    }

    @Override
    public void onResume() {
        super.onResume();
        // register to listen for changes to shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        updateSummary(Utils.PREFERNCE_KEY);
    }

    @Override
    public void onPause() {
        super.onPause();
        // unregister listener
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        updateSummary(s);
    }

    /**
     * Updates the summary of the relevant Preference for the given key
     * @param key
     */
    private void updateSummary(String key) {
        if (Utils.PREFERNCE_KEY.equals(key)) {
            Preference preference = findPreference(key);
            if (preference != null) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String currentValue = sharedPreferences.getString(key, TempertatureUnits.FAHRENHEIT.name());
                TempertatureUnits currentUnits = TempertatureUnits.valueOf(currentValue);
                preference.setSummary(currentUnits.getDisplayName());
            }
        }
    }
}
