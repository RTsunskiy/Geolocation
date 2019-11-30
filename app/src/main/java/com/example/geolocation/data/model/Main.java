package com.example.geolocation.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp")
    @Expose
    public String temp;
    @SerializedName("pressure")
    @Expose
    public String pressure;
    @SerializedName("humidity")
    @Expose
    public String humidity;
    @SerializedName("temp_min")
    @Expose
    public String tempMin;
    @SerializedName("temp_max")
    @Expose
    public String tempMax;
}
