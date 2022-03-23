package com.example.go4lunch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SavedPlaceModel {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("photos")
    @Expose
    private List<PhotoModel> photos = null;

    @SerializedName("place_id")
    @Expose
    private String placeId;

    @SerializedName("geometry")
    @Expose
    private GeometryModel geometry;


    @SerializedName("formatted_address")
    @Expose
    private String  address;

    @SerializedName("rating")
    @Expose
    private Double rating;

    @SerializedName("international_phone_number")
    @Expose
    private String phoneNumber;

    @SerializedName("vicinity")
    @Expose
    private String vicinity;

    @SerializedName("website")
    @Expose
    private String website;

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public SavedPlaceModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }


    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public void setPhotos(List<PhotoModel> photos) {
        this.photos = photos;
    }

    public List<PhotoModel> getPhotos() {
        return photos;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public GeometryModel getGeometry() {
        return geometry;
    }

    public void setGeometry(GeometryModel geometry) {
        this.geometry = geometry;
    }
}
