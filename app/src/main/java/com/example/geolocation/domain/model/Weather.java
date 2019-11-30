package com.example.geolocation.domain.model;

public class Weather {

    private final String mIcon;
    private final String mLongtitude;
    private final String mLatitude;
    private final String mCurrentWeather;
    private final String mMaxTemperature;
    private final String mMinTemperature;

    public Weather(String mIcon,
                   String mLongtitude,
                   String mLatitude,
                   String mCurrentWeather,
                   String mMaxTemperature,
                   String mMinTemperature) {
        this.mIcon = mIcon;
        this.mLongtitude = mLongtitude;
        this.mLatitude = mLatitude;
        this.mCurrentWeather = mCurrentWeather;
        this.mMaxTemperature = mMaxTemperature;
        this.mMinTemperature = mMinTemperature;
    }

    public String getmIcon() {
        return mIcon;
    }

    public String getmLongtitude() {
        return mLongtitude;
    }

    public String getmLatitude() {
        return mLatitude;
    }

    public String getmCurrentWeather() {
        return mCurrentWeather;
    }

    public String getmMaxTemperature() {
        return mMaxTemperature;
    }

    public String getmMinTemperature() {
        return mMinTemperature;
    }
}
