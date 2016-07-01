package com.deuxlapins.amsterdam.util;

/**
 * Utility class used to hold static methods and constants that can be used across the app
 * Created by chetan on 27/06/16.
 */
public class Utils {

    /**
     * factor used in conversion between temperature units
     */
    private static final double FACTOR = 5.0/9.0;


    /**
     * Key to be used by User Preferences
     */
    public static final String PREFERNCE_KEY = "preferredUnits";

    /**
     * Converts values from Fahrenheit to Celsius
     * @param tempInF
     * @return
     */
    public static double convertFtoC(double tempInF) {
        return (tempInF - 32) * FACTOR;
    }

    /**
     * Simple formatter to format temperature
     * @param value
     * @return
     */
    public static String formatTemperature(Double value) {
        return String.valueOf(Math.round(value))+"°";
    }
}
