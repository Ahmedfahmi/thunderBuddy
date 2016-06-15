package com.ahmedfahmi.challenge.Assets;

/**
 * Created by Ahmed Fahmi on 6/15/2016.
 */

import android.view.LayoutInflater;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ahmedfahmi.challenge.R;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {

    private List<Weather> weatherList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView date, temperature, day;

        public MyViewHolder(View view) {
            super(view);

            date = (TextView) view.findViewById(R.id.date);
            day = (TextView) view.findViewById(R.id.day);
            temperature = (TextView) view.findViewById(R.id.temperature);
        }
    }


    public WeatherAdapter(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Weather weather = weatherList.get(position);
        holder.date.setText(weather.getDate());
        holder.day.setText(weather.getDay());
        holder.temperature.setText(weather.getTemperature());
        if (position==0){

        }else {


        }


    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }
}
