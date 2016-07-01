package com.deuxlapins.amsterdam.vo;

import com.deuxlapins.amsterdam.util.Utils;

import java.util.List;

/**
 * Value object to hold current weather conditions and forecast for a particular place.
 * Created by chetan on 27/06/16.
 */
public class Place {

    private static boolean useCelsius = false;

    public static void setUnits(boolean celsius) {
        useCelsius = celsius;
        Forecast.useCelsius = celsius;
    }

    private String name;

    private Double currentTempInF;

    private String currentCondition;

    private List<Forecast> forecast;

    public Place(String name, Double tempInF, String condition, List<Forecast> forecast) {
        this.name = name;
        this.currentTempInF = tempInF;
        this.currentCondition = condition;
        this.forecast = forecast;
    }

    public String getName() {
        return name;
    }

    public Double getCurrentTempInF() {
        return currentTempInF;
    }

    public Double getCurrentTempInC() {
        return Utils.convertFtoC(currentTempInF);
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public List<Forecast> getForecast() {
        return forecast;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Weather for : "+name);
        builder.append("\nCurrent Temp : ");
        builder.append(currentTempInF);
        builder.append("\nCondition : ");
        builder.append(currentCondition);
        builder.append("\nForecast :\n");
        for (Forecast weatherForecast : this.forecast) {
            builder.append(weatherForecast.getDay()+", "+weatherForecast.getDate() + " Min: "+weatherForecast.getMinTempInF() + " Max: "+weatherForecast.getMaxTempInF()+"\n");
        }
        return builder.toString();
    }
}
