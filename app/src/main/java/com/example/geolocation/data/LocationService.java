package com.example.geolocation.data;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;


import androidx.annotation.NonNull;

import com.example.geolocation.presentetion.MainActivity;
import com.example.geolocation.presentetion.MyApplication;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.lang.ref.WeakReference;
import java.util.Locale;

public class LocationService {

    private WeakReference<MainActivity> mMainActivityWeakReference;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int REQUEST_CHECK_SETTINGS = 102;
    private Geocoder mGeocoder;
    private LocationCallback mLocationCallback = new MainLocationCallback();
    public static Location mLocation;


    public Location getmLocation() {
        return mLocation;
    }

    public LocationService(MainActivity activity) {
        this.mMainActivityWeakReference = new WeakReference<>(activity);
        mGeocoder = new Geocoder(mMainActivityWeakReference.get(), Locale.getDefault());
    }


    public void checkGooglePlayServices() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int statusCode = googleApiAvailability.isGooglePlayServicesAvailable(MyApplication.getAppContext());
        if (statusCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = googleApiAvailability.getErrorDialog(mMainActivityWeakReference.get(), statusCode,
                    0, new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            mMainActivityWeakReference.get().finish();
                        }
                    });
            errorDialog.show();
        } else {
            mMainActivityWeakReference.get().checkPermission();
        }
    }

    public void checkDeviceSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(getLocationRequest());
        SettingsClient client = LocationServices.getSettingsClient(mMainActivityWeakReference.get());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(mMainActivityWeakReference.get(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                startLocationService();
            }
        });
        task.addOnFailureListener(mMainActivityWeakReference.get(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(mMainActivityWeakReference.get(),
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });

    }

    public void startLocationService() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mMainActivityWeakReference.get());
        mFusedLocationProviderClient.requestLocationUpdates(getLocationRequest(), mLocationCallback, null);
    }

    private LocationRequest getLocationRequest() {
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
            }
        }
    }


}
