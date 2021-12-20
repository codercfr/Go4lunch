package com.example.go4lunch.model;

import androidx.annotation.Nullable;

public class Users {

    private String uid;
    private String username;
    private Boolean isMentor;


    public Users() { }

    public Users(String uid, String username) {
        this.uid = uid;
        this.username = username;
        this.isMentor = false;
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
