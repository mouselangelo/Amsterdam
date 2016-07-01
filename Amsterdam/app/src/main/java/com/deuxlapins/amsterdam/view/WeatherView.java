package com.deuxlapins.amsterdam.view;

import android.content.Context;

import com.deuxlapins.amsterdam.vo.Place;

/**
 * Contract between the actual view (Activity or Fragment) that displays the weather and the Presenter.
 * Used by the presenter to provide the data and manage view related operations
 * To be implemented by the Activity / Fragment and handle the methods to present the data.
 * Created by chetan on 27/06/16.
 */
public interface WeatherView {
    Context getAppContext();
    void showProgress();
    void hideProgress();
    void presentWeather(Place place);
    void showError(String message);
}
