package com.example.geolocation.domain;

import android.location.Location;

import androidx.annotation.NonNull;

import com.example.geolocation.domain.model.Weather;


import java.io.IOException;

public interface IWeatherRepository {
    @NonNull
    Weather loadWeatherInfo(Location location) throws IOException;
}
