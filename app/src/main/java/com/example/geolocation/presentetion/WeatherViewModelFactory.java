package com.example.geolocation.presentetion;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.geolocation.data.WeatherInfoRepository;
import com.example.geolocation.domain.IWeatherRepository;
import com.example.geolocation.domain.WeatherInterractor;
import com.example.geolocation.presentetion.utils.ResourceWrapper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WeatherViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final Context mApplicationContext;

    public WeatherViewModelFactory(@NonNull Context context) {
        mApplicationContext = context.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (WeatherViewModel.class.equals(modelClass)) {
            IWeatherRepository weatherRepository = new WeatherInfoRepository();
            WeatherInterractor interactor = new WeatherInterractor(weatherRepository);
            Executor executor = Executors.newSingleThreadExecutor();
            ResourceWrapper resourceWrapper = new ResourceWrapper(mApplicationContext.getResources());
            return (T) new WeatherViewModel(
                    executor,
                    interactor,
                    resourceWrapper);
        } else {
            return super.create(modelClass);
        }
    }
}
