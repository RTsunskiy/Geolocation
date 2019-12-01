package com.example.geolocation.domain;

import androidx.annotation.NonNull;

import com.example.geolocation.domain.model.Weather;
import com.example.geolocation.presentetion.MainActivity;

import java.io.IOException;

public class WeatherInterractor {
    private final IWeatherRepository mWeatherRepository;

    public WeatherInterractor(@NonNull IWeatherRepository weatherRepository) {
        mWeatherRepository = weatherRepository;
    }


    public Weather loadWeatherInfo(MainActivity activity) throws LoadWeatherException {
        try {
            return mWeatherRepository.loadWeatherInfo(activity);
        } catch (IOException e) {
            throw new LoadWeatherException("Не удалось загрузить информацию о погоде", e);
        }
    }
}
