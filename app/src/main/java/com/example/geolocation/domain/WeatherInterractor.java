package com.example.geolocation.domain;

import android.location.Location;

import androidx.annotation.NonNull;

import com.example.geolocation.domain.model.Weather;


import java.io.IOException;

public class WeatherInterractor {
    private final IWeatherRepository mWeatherRepository;

    public WeatherInterractor(@NonNull IWeatherRepository weatherRepository) {
        mWeatherRepository = weatherRepository;
    }


    public Weather loadWeatherInfo(Location location) throws LoadWeatherException {
        try {
            return mWeatherRepository.loadWeatherInfo(location);
        } catch (IOException e) {
            throw new LoadWeatherException("Не удалось загрузить информацию о погоде", e);
        }
    }
}
