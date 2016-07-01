package com.deuxlapins.amsterdam.model.service.yahoo;

import android.util.Log;

import com.deuxlapins.amsterdam.model.service.ServiceException;
import com.deuxlapins.amsterdam.vo.Forecast;
import com.deuxlapins.amsterdam.vo.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chetan on 01/07/16.
 */
public class YahooWeatherAPI {

    private static final String DEBUG_TAG = "YahooWeatherAPI";

    public Place getWeather() throws ServiceException {
        try {
            Log.d(DEBUG_TAG, "Calling the API...");
            YahooWeatherService service = new YahooWeatherService();
            String jsonString = service.loadData();
            if (jsonString != null && !jsonString.isEmpty()) {
                JSONObject response = new JSONObject(jsonString);
                JSONObject channel = response.getJSONObject("query").getJSONObject("results").getJSONObject("channel");

                JSONObject location = channel.getJSONObject("location");
                JSONObject item = channel.getJSONObject("item");
                JSONObject condition = item.getJSONObject("condition");
                JSONArray forecast = item.getJSONArray("forecast");


                String city = location.getString("city");
                Double currentTemp = condition.getDouble("temp");
                String currentCondition = condition.getString("text");

                Log.d(DEBUG_TAG, city);
                Log.d(DEBUG_TAG, "Temp : " + currentTemp);
                Log.d(DEBUG_TAG, currentCondition);

                List<Forecast> forecastList = new ArrayList<>(forecast.length());

                for (int i = 0; i < forecast.length(); i++) {
                    JSONObject forecastCondition = forecast.getJSONObject(i);
                    String date = forecastCondition.getString("date");
                    String day = forecastCondition.getString("day");
                    Double low = forecastCondition.getDouble("low");
                    Double high = forecastCondition.getDouble("high");
                    Log.d(DEBUG_TAG, "Forecast for " + date + ", " + day + " = Max:" + high + ", Min:" + low);

                    Forecast weatherForecast = new Forecast(date, day, low, high);
                    forecastList.add(weatherForecast);
                }

                Place place = new Place(city, currentTemp, currentCondition, forecastList);
                return place;

            }
        } catch (JSONException e) {
            e.printStackTrace();
            throw new ServiceException("Invalid data");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("Unable to load the data");
        }
        return null;
    }
}
