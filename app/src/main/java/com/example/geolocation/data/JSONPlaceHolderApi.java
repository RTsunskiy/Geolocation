package com.example.geolocation.data;


import com.example.geolocation.data.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("weather")
    Call<Example> getWeatherInfo(@Query("lat") String latitude,
                                 @Query("lon") String longtitude,
                                 @Query("celsius") String celsius,
                                 @Query("key") String key);
}
