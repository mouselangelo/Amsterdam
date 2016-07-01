package com.deuxlapins.amsterdam.model.service.yahoo;

import com.deuxlapins.amsterdam.model.service.BaseService;
import com.deuxlapins.amsterdam.model.service.ServiceException;

/**
 * Uses the yahoo weather service to return the weather (as String)
 * Created by chetan on 27/06/16.
 */
class YahooWeatherService extends BaseService {

    public static final String AMSTERDAM_URL = "https://query.yahooapis.com/v1/public/yql?q=" +
            "select%20*%20from%20weather.forecast%20where%20woeid%20in%20" +
            "(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22amsterdam%22)" +
            "&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    YahooWeatherService() {
        super(AMSTERDAM_URL);
    }

    public String loadData() throws ServiceException {
        return this.loadDataAsString();
    }

    @Override
    protected String loadDataAsString() throws ServiceException {
        String jsonData = super.loadDataAsString();
        return jsonData;
    }
}
