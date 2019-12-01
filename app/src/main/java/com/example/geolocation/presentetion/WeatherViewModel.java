package com.example.geolocation.presentetion;


import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.geolocation.R;
import com.example.geolocation.domain.LoadWeatherException;
import com.example.geolocation.domain.SingleLiveEvent;
import com.example.geolocation.domain.WeatherInterractor;
import com.example.geolocation.domain.model.Weather;
import com.example.geolocation.presentetion.utils.IResourceWrapper;

import java.util.concurrent.Executor;

public class WeatherViewModel extends ViewModel {

    private final Executor mExecutor;
    private final WeatherInterractor mWeatherInteractor;
    private final MutableLiveData<Weather> mWeather = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    private final SingleLiveEvent<String> mErrors = new SingleLiveEvent<>();
    private final IResourceWrapper mResourceWrapper;

    WeatherViewModel(@NonNull Executor executor,
                         @NonNull WeatherInterractor weatherInterractor,
                         @NonNull IResourceWrapper resourceWrapper) {
        mWeatherInteractor = weatherInterractor;
        mExecutor = executor;
        mResourceWrapper = resourceWrapper;
    }

    void loadWeather(Location location) {
        mIsLoading.setValue(true);
        mExecutor.execute(() -> {
            try {
                Weather weather = mWeatherInteractor.loadWeatherInfo(location);
                mWeather.postValue(weather);
            } catch (LoadWeatherException e) {
                mErrors.postValue(mResourceWrapper.getString(R.string.eror_loading_weather));
            }
            mIsLoading.postValue(false);
        });
    }

    @NonNull
    LiveData<Weather> getWeather() {
        return mWeather;
    }

    @NonNull
    LiveData<Boolean> isLoading() {
        return mIsLoading;
    }

    @NonNull
    LiveData<String> getErrors() {
        return mErrors;
    }
}
