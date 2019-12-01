package com.example.geolocation.data;

import android.location.Location;


import com.example.geolocation.presentetion.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;


import java.lang.ref.WeakReference;


public class LocationService {

    private WeakReference<MainActivity> mMainActivityWeakReference;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LocationCallback mLocationCallback = new MainLocationCallback();
    public static Location mLocation;


    public Location getmLocation() {
        return mLocation;
    }

    public LocationService(MainActivity activity) {
        this.mMainActivityWeakReference = new WeakReference<>(activity);
    }

    public void startLocationService() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mMainActivityWeakReference.get());
        mFusedLocationProviderClient.requestLocationUpdates(getLocationRequest(), mLocationCallback, null);
    }

    public LocationRequest getLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(1000L);
        locationRequest.setFastestInterval(5000L);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return locationRequest;
    }


    private class MainLocationCallback extends LocationCallback {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            if (locationResult == null) {
                return;
            }

            for (Location location : locationResult.getLocations()) {
                mLocation = location;
                mMainActivityWeakReference.get().setmLocation(location);
                mMainActivityWeakReference.get().setupMvvm();
            }
        }
    }


}
