package com.deuxlapins.amsterdam.model;

import com.deuxlapins.amsterdam.vo.Place;

/**
 * Model classes should implement this to provide the model for the app
 * Created by chetan on 27/06/16.
 */
public interface WeatherModel {
    /**
     * Listener
     * implement onSuccessWithData - to return successfully with the weather for the Place
     * implement onFailedWithError - to return when unable to retrieve the data, fail with a friendly message
     * Created by chetan on 01/07/16.
     */
    interface WeatherModelListener {
        /**
         * Called when data is loaded successfully
         * @param place - Place with weather details
         */
        void onSuccessWithData(Place place);

        /**
         * Called when the service returns an error
         * @param error - a short description of the error
         */
        void onFailedWithError(String error);

    }

    void loadData(WeatherModelListener listener);
}
