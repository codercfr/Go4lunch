package com.example.go4lunch.response;

import com.example.go4lunch.model.SavedPlaceModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SavedPlaceResponseModel {
    @SerializedName("result")
    @Expose
    private final SavedPlaceModel savedPlaceModel;

    @SerializedName("error_message")
    @Expose
    private final String error;

    public SavedPlaceResponseModel(SavedPlaceModel savedPlaceModel, String error) {
        this.savedPlaceModel = savedPlaceModel;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public SavedPlaceModel getSavedPlaceModel() {
        return savedPlaceModel;
    }
}
