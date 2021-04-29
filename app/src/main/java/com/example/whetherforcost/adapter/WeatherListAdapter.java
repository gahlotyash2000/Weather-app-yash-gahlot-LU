package com.example.whetherforcost.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.whetherforcost.R;
import com.example.whetherforcost.Utils;
import com.example.whetherforcost.model.WeatherListData;

import java.util.List;

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.SoilReportViewHolder> {

    Context context;

    public WeatherListAdapter(Context context, List<WeatherListData> dataArray) {
        this.context = context;
        this.dataArray = dataArray;
    }

    List<WeatherListData> dataArray;


    @NonNull
    @Override
    public SoilReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SoilReportViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inflater_weather_rv_items, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SoilReportViewHolder holder, int position) {

        WeatherListData soilTestReportDataModel= dataArray.get(position);
        holder.time_txt.setText(soilTestReportDataModel.getTime());
        //if(soilTestReportDataModel.getWeather() != null && soilTestReportDataModel.getWeather().size()>0)
        holder.cloud_txt.setText(soilTestReportDataModel.getCloadState());
        try {
            holder.degree_txt.setText(String.valueOf(Utils.round(Double.parseDouble(soilTestReportDataModel.getTempp()), 1)));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }

    public class SoilReportViewHolder extends RecyclerView.ViewHolder {
        TextView time_txt, cloud_txt, degree_txt;
        public SoilReportViewHolder(@NonNull View itemView) {
            super(itemView);
            degree_txt= itemView.findViewById(R.id.degree_txt);
            cloud_txt= itemView.findViewById(R.id.cloud_txt);
            time_txt= itemView.findViewById(R.id.time_txt);

        }
    }
}
