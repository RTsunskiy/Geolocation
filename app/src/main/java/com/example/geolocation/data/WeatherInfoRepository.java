package com.example.geolocation.data;


import android.app.Application;
import android.location.Address;

import androidx.annotation.NonNull;

import com.example.geolocation.data.model.Example;
import com.example.geolocation.domain.IWeatherRepository;
import com.example.geolocation.domain.model.Weather;
import com.example.geolocation.presentetion.MainActivity;
import com.example.geolocation.presentetion.MyApplication;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherInfoRepository implements IWeatherRepository {

    private static final String BASE_URL = "https://samples.openweathermap.org/data/2.5/";
    private final String API_KEY = "e6e474cad3b43e69d80ee2d3c4294b4c";
    private final String CELSIUS_PARAM = "units=metric";
    private Retrofit mRetrofit;
    private final JSONPlaceHolderApi mWeatherApi;
    private LocationService mLocationService;


    public WeatherInfoRepository() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mWeatherApi = mRetrofit.create(JSONPlaceHolderApi.class);
    }

    @NonNull
    @Override
    public Weather loadWeatherInfo(MainActivity activity) throws IOException {
        mLocationService = new LocationService(activity);
        mLocationService.startLocationService();
        Address address = mLocationService.getmAdress();
        Response<Example> response = mWeatherApi.getWeatherInfo(String.valueOf(address.getLatitude()),
                String.valueOf(address.getLongitude()), CELSIUS_PARAM, API_KEY).execute();
        if (response.body() == null || response.errorBody() != null) {
            throw new IOException("Не удалось загрузить информацию о погоде");
        }
        Example weatherObjectInfo = response.body();
        return new Weather(String.valueOf(address.getLatitude()),
                           String.valueOf(address.getLongitude()),
                           weatherObjectInfo.main.temp,
                            weatherObjectInfo.main.tempMax,
                            weatherObjectInfo.main.tempMin);
    }

    }

