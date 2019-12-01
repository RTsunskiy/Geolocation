package com.example.geolocation.data;


import com.example.geolocation.data.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("/data/2.5/weather")
    Call<Example> getWeatherInfo(@Query("lat") String lat,
                                 @Query("lon") String lon,
                                 @Query("units") String celsius,
                              @Query("APPID") String key);
}
