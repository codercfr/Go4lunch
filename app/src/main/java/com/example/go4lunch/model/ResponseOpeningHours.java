package com.example.go4lunch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseOpeningHours {

    @SerializedName("open_now")
    @Expose
    private Boolean openNow;

    public Boolean getOpenNow() {
        return openNow;
    }

}

