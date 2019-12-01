package com.example.geolocation.presentetion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geolocation.R;
import com.example.geolocation.data.LocationService;
import com.google.android.gms.location.LocationSettingsStates;



public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;
    private static final int REQUEST_CHECK_SETTINGS = 102;
    private LocationService mLocationService;
    private WeatherViewModel mViewModel;

    private TextView mLongtitude;
    private TextView mLatitude;
    private TextView mCurrentTemperature;
    private TextView mMaxTemperature;
    private TextView mMinTemperature;
    private View mLoadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocationService = new LocationService(this);
        mLocationService.checkGooglePlayServices();
        checkPermission();
        initView();
        setupMvvm();
    }



    @Override
    protected void onResume() {
        super.onResume();
        mLocationService.checkGooglePlayServices();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 1) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    mLocationService.startLocationService();

                } else {
                    finish();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        mLocationService.startLocationService();
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                    default:
                        break;
                }
                break;
        }
    }





    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }

        else {
            mLocationService.checkDeviceSettings();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_CODE);
    }

    private void setupMvvm() {
        mViewModel = ViewModelProviders.of(this, new WeatherViewModelFactory(this))
                .get(WeatherViewModel.class);
        mViewModel.getErrors().observe(this, error ->
                Toast.makeText(this, error, Toast.LENGTH_LONG).show());
        mViewModel.getWeather().observe(this, weather -> {
            mLatitude.setText(weather.getmLatitude());
            mLongtitude.setText(weather.getmLongtitude());
            mCurrentTemperature.setText(weather.getmCurrentWeather());
            mMaxTemperature.setText(weather.getmMaxTemperature());
            mMinTemperature.setText(weather.getmMinTemperature());
        });
        mViewModel.isLoading().observe(this, isLoading -> mLoadingView.setVisibility(isLoading ? View.VISIBLE : View.GONE));
        mViewModel.loadWeather(this);
    }

    private void initView() {
        mLatitude = findViewById(R.id.latitude_value);
        mLongtitude = findViewById(R.id.longtitude_value);
        mCurrentTemperature = findViewById(R.id.current_degrees);
        mMaxTemperature = findViewById(R.id.max_degrees_today);
        mMinTemperature = findViewById(R.id.min_degrees_today);
        mLoadingView = findViewById(R.id.loading_view);
    }

}
