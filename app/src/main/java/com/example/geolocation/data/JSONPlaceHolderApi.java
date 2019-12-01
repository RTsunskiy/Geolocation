package com.example.geolocation.data;


import com.example.geolocation.data.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("/data/2.5/weather")
    Call<Example> getWeatherInfo(@Query("lat") Double lat,
                                 @Query("lon") Double lon,
                                 @Query("units") String celsius,
                              @Query("APPID") String key);
}
