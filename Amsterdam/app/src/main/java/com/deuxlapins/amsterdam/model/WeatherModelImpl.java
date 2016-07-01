package com.deuxlapins.amsterdam.model;

import android.os.AsyncTask;
import android.util.Log;

import com.deuxlapins.amsterdam.model.service.ServiceException;
import com.deuxlapins.amsterdam.model.service.yahoo.YahooWeatherAPI;
import com.deuxlapins.amsterdam.vo.Place;

/**
 * Implements the model interface. Retrieves weather from WeatherService and parses
 * Created by chetan on 27/06/16.
 */
public class WeatherModelImpl implements WeatherModel {

    private static final String DEBUG_TAG = "WeatherModel";

    @Override
    public void loadData(WeatherModelListener listener) {
        Log.d(DEBUG_TAG, "Starting task...");
        new DownloadTask(listener).execute();
    }

    /**
     * Loads the weather data in the background
     */
    private class DownloadTask extends AsyncTask<String, Void, Place> {

        private WeatherModelListener listener;

        DownloadTask(WeatherModelListener listener) {
            this.listener = listener;
        }

        @Override
        protected Place doInBackground(String... params) {
            try {
                return new YahooWeatherAPI().getWeather();
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Place place) {
            if (place != null) {
                listener.onSuccessWithData(place);
            } else {
                listener.onFailedWithError("Error loading data...");
            }
        }
    }

}
