package com.example.geolocation.domain;

public class LoadWeatherException extends Exception {
    public LoadWeatherException(String message, Throwable cause) {
        super(message, cause);
    }
}
