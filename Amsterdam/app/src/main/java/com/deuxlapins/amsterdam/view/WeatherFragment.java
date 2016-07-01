package com.deuxlapins.amsterdam.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.deuxlapins.amsterdam.R;
import com.deuxlapins.amsterdam.presenter.WeatherPresenter;
import com.deuxlapins.amsterdam.presenter.WeatherPresenterImpl;
import com.deuxlapins.amsterdam.util.Utils;
import com.deuxlapins.amsterdam.vo.Place;
import com.deuxlapins.amsterdam.vo.TempertatureUnits;

/**
 * A placeholder fragment containing a simple view.
 */
public class WeatherFragment extends Fragment implements WeatherView, SharedPreferences.OnSharedPreferenceChangeListener {

    private WeatherPresenter presenter;

    private View weatherView;
    private ProgressBar progressBar;
    private TextView placeName;
    private TextView currentTemperature;
    private TextView currentUnits;
    private TextView currentConditions;
    private ListView forecastList;

    private TempertatureUnits tempertatureUnits;

    private WeatherListAdapter adapter;

    private Place place;


    public WeatherFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Retains instance of this fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        this.weatherView = view.findViewById(R.id.weatherView);
        this.progressBar = (ProgressBar) view.findViewById(R.id.progress);

        this.placeName = (TextView) view.findViewById(R.id.placeName);
        this.currentTemperature = (TextView) view.findViewById(R.id.currentTemperature);
        this.currentUnits = (TextView) view.findViewById(R.id.currentUnits);

        this.currentConditions = (TextView) view.findViewById(R.id.currentConditions);
        this.forecastList = (ListView) view.findViewById(R.id.forecastList);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Read the user preferences and update in view
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String prefValue = sharedPreferences.getString(Utils.PREFERNCE_KEY, TempertatureUnits.FAHRENHEIT.name());
        tempertatureUnits = TempertatureUnits.valueOf(prefValue);
        // start listening for changes in preferences
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        // start loading data if not already available
        if (place == null) {
            loadData();
        }
        // update view
        refreshWeatherView();
    }

    @Override
    public void onPause() {
        super.onPause();
        // stop listening for shared preferences changes
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onDestroy() {
        // pass lifecycle methods to presenter
        if (presenter != null) {
            presenter.onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // update the view if relevant preference has changed
        if (Utils.PREFERNCE_KEY.equals(key)) {
            String prefValue = sharedPreferences.getString(key, TempertatureUnits.FAHRENHEIT.name());
            tempertatureUnits = TempertatureUnits.valueOf(prefValue);
            refreshWeatherView();
        }
    }

    public void refresh() {
        loadData();
    }


    private void loadData() {
        // initialize the presenter if needed
        if (presenter == null) {
            presenter = new WeatherPresenterImpl();
        }
        // ask for data
        presenter.getWeather(this);
    }


    /**
     * updates the view to display the weather data
     */
    private void refreshWeatherView() {
        if (place != null) {
            this.placeName.setText(place.getName());
            if (tempertatureUnits == TempertatureUnits.FAHRENHEIT) {
                this.currentTemperature.setText(Utils.formatTemperature(place.getCurrentTempInF()));
            } else {
                this.currentTemperature.setText(Utils.formatTemperature(place.getCurrentTempInC()));
            }
            this.currentUnits.setText(tempertatureUnits.getDisplaySymbol());
            this.currentConditions.setText(place.getCurrentCondition());
            if (this.adapter == null) {
                this.adapter = new WeatherListAdapter(getContext(), tempertatureUnits, place.getForecast());
            }
            this.adapter.setTempertatureUnits(tempertatureUnits);
            this.forecastList.setAdapter(adapter);
            weatherView.setVisibility(View.VISIBLE);
        } else {
            weatherView.setVisibility(View.INVISIBLE);
        }
    }



    // Presenter methods

    @Override
    public Context getAppContext() {
        return getContext();
    }

    @Override
    public void showProgress() {
        this.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void presentWeather(Place place) {
        this.place = place;
        this.adapter = null; // reset adapter as new data is available
        refreshWeatherView();
    }


    @Override
    public void showError(String message) {
        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.service_error)
                .setMessage(message)
                .create()
                .show();
    }

}
