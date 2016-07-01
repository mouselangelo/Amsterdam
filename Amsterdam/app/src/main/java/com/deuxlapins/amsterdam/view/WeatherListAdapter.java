package com.deuxlapins.amsterdam.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.deuxlapins.amsterdam.R;
import com.deuxlapins.amsterdam.util.Utils;
import com.deuxlapins.amsterdam.vo.Forecast;
import com.deuxlapins.amsterdam.vo.TempertatureUnits;

import java.util.List;

/**
 * Created by chetan on 27/06/16.
 */
public class WeatherListAdapter extends ArrayAdapter<Forecast> {

    private LayoutInflater inflater;
    private TempertatureUnits tempertatureUnits;

    public WeatherListAdapter(Context context, TempertatureUnits tempertatureUnits, List<Forecast> objects) {
        super(context, -1, objects);
        this.tempertatureUnits = tempertatureUnits;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * The temperature units to be used to display the values in the list
     * @param tempertatureUnits
     */
    public void setTempertatureUnits(TempertatureUnits tempertatureUnits) {
        this.tempertatureUnits = tempertatureUnits;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if (convertView != null) {
            // recycle view
            view = convertView;
            // get the view holder
            holder = (ViewHolder) view.getTag();
        } else {
            // create a new view
            view = inflater.inflate(R.layout.weather_list_item, parent, false);
            // create a new view holder for quick access to views
            holder = new ViewHolder();
            view.setTag(holder);
            holder.day = (TextView) view.findViewById(R.id.day);
            holder.date = (TextView) view.findViewById(R.id.date);
            holder.minTemp = (TextView) view.findViewById(R.id.minTemp);
            holder.maxTemp = (TextView) view.findViewById(R.id.maxTemp);
        }

        // get the VO object
        Forecast forecast = getItem(position);

        // update list values
        holder.day.setText(forecast.getDay());
        holder.date.setText(forecast.getDate());

        String maxTemp;
        String minTemp;

        if (tempertatureUnits == TempertatureUnits.FAHRENHEIT) {
            maxTemp = Utils.formatTemperature(forecast.getMaxTempInF());
            minTemp = Utils.formatTemperature(forecast.getMinTempInF());
        } else {
            maxTemp = Utils.formatTemperature(forecast.getMaxTempInC());
            minTemp = Utils.formatTemperature(forecast.getMinTempInC());
        }

        holder.maxTemp.setText(maxTemp);
        holder.minTemp.setText(minTemp);

        return view;
    }

    /**
     * holder class to hold references of list item sub views
     */
    private class ViewHolder {
        TextView day;
        TextView date;
        TextView maxTemp;
        TextView minTemp;
    }


}
