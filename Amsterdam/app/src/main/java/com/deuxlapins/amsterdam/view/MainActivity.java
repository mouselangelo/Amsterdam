package com.deuxlapins.amsterdam.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.deuxlapins.amsterdam.R;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "MainActivity";
    private static final String FRAGMENT_TAG = "WeatherFragment";
    private WeatherFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragment =  (WeatherFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        // fragment not retained so create new one
        if(fragment == null) {
             fragment = new WeatherFragment();
            fragmentManager.beginTransaction().replace(android.R.id.content, fragment, FRAGMENT_TAG).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                Log.d(DEBUG_TAG, "Do Refresh...");
                fragment.refresh();
                return true;
            case R.id.action_settings:
                Log.d(DEBUG_TAG, "Show Settings...");
                showSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
  }
}
