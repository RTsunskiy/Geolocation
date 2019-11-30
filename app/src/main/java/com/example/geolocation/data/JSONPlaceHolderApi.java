package com.example.geolocation.data;


import com.example.geolocation.data.model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JSONPlaceHolderApi {
    @GET("a")
    Call<Example> getWeatherInfo(@Query("") String token, @Query("") String v);
}
