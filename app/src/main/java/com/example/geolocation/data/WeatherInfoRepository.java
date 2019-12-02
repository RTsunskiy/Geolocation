package com.example.geolocation.data;


import android.location.Location;

import androidx.annotation.NonNull;

import com.example.geolocation.data.model.Example;
import com.example.geolocation.domain.IWeatherRepository;
import com.example.geolocation.domain.model.Weather;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherInfoRepository implements IWeatherRepository {

    private static final String BASE_URL = "http://api.openweathermap.org/";
    private final String API_KEY = "e6e474cad3b43e69d80ee2d3c4294b4c";
    private final String CELSIUS_PARAM = "metric";
    private Retrofit mRetrofit;
    private final JSONPlaceHolderApi mWeatherApi;


    public WeatherInfoRepository() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mWeatherApi = mRetrofit.create(JSONPlaceHolderApi.class);
    }

    @NonNull
    @Override
    public Weather loadWeatherInfo(Location location) throws IOException {
        Response<Example> response = mWeatherApi.getWeatherInfo(location.getLatitude(),
                location.getLongitude(), CELSIUS_PARAM, API_KEY).execute();
        if (response.body() == null || response.errorBody() != null) {
            throw new IOException("Не удалось загрузить информацию о погоде");
        }
        Example weatherObjectInfo = response.body();
        Weather weather = new Weather(String.valueOf(location.getLatitude()),
                String.valueOf(location.getLongitude()),
                weatherObjectInfo.main.temp,
                weatherObjectInfo.main.tempMax,
                weatherObjectInfo.main.tempMin);
        return weather;
    }

    }

