package com.deuxlapins.amsterdam.presenter;

import com.deuxlapins.amsterdam.view.WeatherView;

/**
 * Presenter interface to be used by the view and implemented by the presenter
 * Created by chetan on 27/06/16.
 */
public interface WeatherPresenter {
    void getWeather(WeatherView view);
    void onDestroy();
}
