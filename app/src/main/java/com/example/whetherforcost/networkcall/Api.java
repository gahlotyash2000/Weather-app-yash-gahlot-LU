package com.example.whetherforcost.networkcall;

import com.example.whetherforcost.model.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {


    @GET("forecast")
    Call<WeatherResponse> getWeatherData(
            @Query("q") String cityName,
            @Query("appid") String appid
    );

}