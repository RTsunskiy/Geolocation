package com.example.geolocation.domain;

import androidx.annotation.NonNull;

import com.example.geolocation.domain.model.Weather;
import com.example.geolocation.presentetion.MainActivity;

import java.io.IOException;

public interface IWeatherRepository {
    @NonNull
    Weather loadWeatherInfo(MainActivity activity) throws IOException;
}
