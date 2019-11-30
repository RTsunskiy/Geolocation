package com.example.geolocation.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherInfoRepository {

    private static final String BASE_URL = "https://samples.openweathermap.org/data/2.5/weather";
    private Retrofit mRetrofit;
    private final JSONPlaceHolderApi mWeatherApi;


    public WeatherInfoRepository() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mWeatherApi = mRetrofit.create(JSONPlaceHolderApi.class);
    }
}
