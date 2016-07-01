package com.deuxlapins.amsterdam.vo;

import com.deuxlapins.amsterdam.util.Utils;

/**
 * Value object to hold the forecast for a particular date/day
 * Created by chetan on 27/06/16.
 */
public class Forecast {

    static boolean useCelsius = false;

    private String date;
    private String day;
    private double minTempInF;
    private double maxTempInF;

    public Forecast(String date, String day, double minTempInF, double maxTempInF) {
        this.date = date;
        this.day = day;
        this.minTempInF = minTempInF;
        this.maxTempInF = maxTempInF;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public double getMinTempInF() {
        return minTempInF;
    }

    public double getMaxTempInF() {
        return maxTempInF;
    }

    public double getMinTempInC() {
        return Utils.convertFtoC(this.minTempInF);
    }

    public double getMaxTempInC() {
        return Utils.convertFtoC(this.maxTempInF);
    }


}
