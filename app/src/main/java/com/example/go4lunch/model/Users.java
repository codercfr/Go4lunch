package com.example.go4lunch.model;

import java.util.List;

public class Users {

    private String uid;
    private String username;
    private Boolean isMentor;
    private String placeId;
    private String name;
    private String adresseRestaurant;
    private String restaurantName;
    private List<String> likes;
    private String photoUser;
    private String email;
    private String password;
    //reprendre la photo du user
    //place id
    //restau name
    //restau adresse
    // A stocker dans le user.
    // List de strings pour les likes.

    public Users(){

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUser() {
        return photoUser;
    }

    public void setPhotoUser(String photoUser) {
        this.photoUser = photoUser;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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
