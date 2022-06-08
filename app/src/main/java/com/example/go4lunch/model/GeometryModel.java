package com.example.go4lunch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeometryModel {

    @SerializedName("location")
    @Expose
    private LocationModel location;


    public LocationModel getLocation() {
        return location;
    }

}
