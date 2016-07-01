package com.deuxlapins.amsterdam.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.deuxlapins.amsterdam.model.WeatherModel;
import com.deuxlapins.amsterdam.model.WeatherModelImpl;
import com.deuxlapins.amsterdam.view.WeatherView;
import com.deuxlapins.amsterdam.vo.Place;

import java.lang.ref.WeakReference;

/**
 * Presenter that manages the weather view and model.
 * Created by chetan on 27/06/16.
 */
public class WeatherPresenterImpl implements WeatherPresenter, WeatherModel.WeatherModelListener {

    private static final String DEBUG_TAG = "WeatherPresenterImpl";
    private WeakReference<WeatherView> viewWeakReference;

    @Override
    public void getWeather(WeatherView view) {
        this.viewWeakReference = new WeakReference<>(view);
        try {
            if (isNetworkReachable(view.getAppContext())) {
                view.showProgress();
                WeatherModel model = new WeatherModelImpl();
                model.loadData(this);
            } else {
                view.showError("Network unreachable. Please ensure you are connected to the Internet and try again.");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            // ignore
        }
    }

    @Override
    public void onDestroy() {
        // destroy any references to the view if needed & perform cleanup
        viewWeakReference.clear();
        viewWeakReference = null;
    }

    @Override
    public void onSuccessWithData(Place place) {
        Log.d(DEBUG_TAG, place.toString());
        try {
            WeatherView view = viewWeakReference.get();
            view.hideProgress();
            view.presentWeather(place);
        } catch (NullPointerException e) {
            e.printStackTrace();
            // ignore
        }
    }

    @Override
    public void onFailedWithError(String error) {
        try {
            WeatherView view = viewWeakReference.get();
            view.hideProgress();
            view.showError(error);
        } catch (NullPointerException e) {
            e.printStackTrace();
            // ignore
        }

    }

    /**
     * Checks if the device is able to connect to the internet
     * @return boolean: connectivity status
     */
    private boolean isNetworkReachable(Context context) {
        try {
            // Obtain Connectivity Service from Context
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            // Get the NetworkInfo and check for connectivity
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo.isConnected();

        } catch (Exception e) {
            return false;
        }
    }
}
