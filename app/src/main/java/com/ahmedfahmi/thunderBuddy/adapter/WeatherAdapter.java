package com.ahmedfahmi.thunderBuddy.adapter;

/**
 * Created by Ahmed Fahmi on 6/15/2016.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ahmedfahmi.thunderBuddy.R;
import com.ahmedfahmi.thunderBuddy.model.Weather;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {

    private List<Weather> weatherList;

    public WeatherAdapter(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.weather_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Weather weather = weatherList.get(position);
        holder.date.setText(weather.getDate());
        holder.weekday.setText(weather.getDay());
        holder.highTemperature.setText(weather.getHighTemperature());
        holder.lowTemperature.setText(weather.getLowTemperature());
        holder.condition.setText(weather.getCondition());
        holder.icon.setImageBitmap(weather.getIconImage());


    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, highTemperature, lowTemperature, weekday, condition;
        public ImageView icon;

        public MyViewHolder(View view) {
            super(view);

            date = (TextView) view.findViewById(R.id.date);
            weekday = (TextView) view.findViewById(R.id.weekday);
            highTemperature = (TextView) view.findViewById(R.id.highTemperature);
            lowTemperature = (TextView) view.findViewById(R.id.lowTemperature);
            condition = (TextView) view.findViewById(R.id.condition);
            icon = (ImageView) view.findViewById(R.id.weatherIcon);


        }
    }
}
