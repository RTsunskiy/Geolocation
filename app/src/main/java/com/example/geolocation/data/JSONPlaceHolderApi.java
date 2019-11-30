package com.example.geolocation.data;


import com.example.geolocation.data.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("weather")
    Call<Example> getWeatherInfo(@Query("latitude") String latitude,
                                 @Query("longtitude") String longtitude,
                                 @Query("key") String key);
}
