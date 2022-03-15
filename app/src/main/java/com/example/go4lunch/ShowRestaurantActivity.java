package com.example.go4lunch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.example.go4lunch.model.GooglePlaceModel;
import com.example.go4lunch.model.SavedPlaceModel;
import com.example.go4lunch.response.GoogleResponseModel;
import com.example.go4lunch.response.SavedPlaceResponseModel;
import com.example.go4lunch.view_model.RestaurantViewModel;
import com.example.go4lunch.webServices.RetrofitApi;
import com.example.go4lunch.webServices.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShowRestaurantActivity extends AppCompatActivity {

    private ImageView restaurantView;
    private TextView restaurantName;
    private RatingBar rtnRestaurant;
    private TextView streetRestaurantName;
    private FirebaseAuth mAuth;
    private String placeId;
    private RetrofitApi retrofitApi;
    private SavedPlaceModel savedPlaceModel = new SavedPlaceModel();
    private RestaurantViewModel restaurantViewModel;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showrestaurant);
        restaurantView = findViewById(R.id.id_restaurantimg);
        restaurantName = findViewById(R.id.restaurantname);
        rtnRestaurant = findViewById(R.id.ratingBarSelectedR);
        streetRestaurantName = findViewById(R.id.street_NameSelectedR);
        restaurantViewModel= new RestaurantViewModel();
        receiveData();
        restaurantViewModel.getSavedRestaurant(placeId)
                .observe(this, savedPlaceModel -> {
                    this.savedPlaceModel.getName();
                    this.savedPlaceModel.getVicinity();
                    this.savedPlaceModel.getPhotos();
                    getRestaurantId();
                });
    }

    // doit faire une requete on on passe le place id regarder la requete placedetail 
    private void receiveData() {
        Intent i = getIntent();
        String name = i.getStringExtra("ID");
        placeId = name;
    }


    //crée un viewmodel qui prend en parametre le place id
    //laissé l'appel glide et les setText dans l'activité
    //placé juste le resultat de la requete dans le repo.

    public void getRestaurantId() {

        restaurantName.setText(savedPlaceModel.getName());
        streetRestaurantName.setText(savedPlaceModel.getVicinity());

       /* String urlPhoto = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=150&photoreference="
                + savedPlaceModel.getPhotos().get(0).getPhotoReference() + "&sensor=true&key=AIzaSyDIC9wuMhHNNjFIr6UZfb64h1Rmauaz7hw";

        Glide.with(restaurantView.getContext())
                .load(urlPhoto)
                .into(restaurantView);

        */
            }


}
