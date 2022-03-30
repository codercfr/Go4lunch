package com.example.go4lunch.model;

import androidx.annotation.Nullable;

import java.util.List;

public class Users {

    private String uid;
    private String username;
    private Boolean isMentor;
    private String placeId;
    private String name;
    private String adresseRestaurant;
    private List<String> likes;
    //reprendre la photo du user
    //place id
    //restau name
    //restau adresse
    // A stocker dans le user.
    // List de strings pour les likes.

    public Users(){

    }



    public Boolean getMentor() {
        return isMentor;
    }

    public void setMentor(Boolean mentor) {
        isMentor = mentor;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdresseRestaurant() {
        return adresseRestaurant;
    }

    public void setAdresseRestaurant(String adresseRestaurant) {
        this.adresseRestaurant = adresseRestaurant;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }


    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUsername() { return username; }
    public Boolean getIsMentor() { return isMentor; }

    // --- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setUid(String uid) { this.uid = uid; }
    public void setIsMentor(Boolean mentor) { isMentor = mentor; }
}
