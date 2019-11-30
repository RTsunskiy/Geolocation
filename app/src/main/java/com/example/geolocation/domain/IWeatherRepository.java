package com.example.geolocation.domain;

import androidx.annotation.NonNull;

import com.example.geolocation.domain.model.Weather;

import java.io.IOException;

public interface IWeatherRepository {
    @NonNull
    Weather loadWeatherInfo() throws IOException;
}
