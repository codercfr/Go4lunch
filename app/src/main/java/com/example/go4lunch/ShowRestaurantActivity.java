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

import com.example.go4lunch.model.GooglePlaceModel;
import com.example.go4lunch.response.GoogleResponseModel;
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
    private GooglePlaceModel googlePlaceModel;


    protected void OnCreate ( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showrestaurant);
        restaurantView=findViewById(R.id.img_restaurant_user);
        restaurantName=findViewById(R.id.restaurantname);
        rtnRestaurant=findViewById(R.id.ratingBarSelectedR);
        streetRestaurantName=findViewById(R.id.street_NameSelectedR);
        getRestaurantId();
    }

    // doit faire une requete on on passe le place id regarder la requete placedetail 
    private void receiveData(){
        Intent i = getIntent();
        String name = i.getStringExtra("ID");
        placeId=name;
    }


    //ajouter plus de détails name, photo, emplacement,stars, type de cuisine.
    private void getRestaurantId(){
        receiveData();
        retrofitApi = RetrofitClient.getRetrofitClient().create(RetrofitApi .class);
         String url="https://maps.googleapis.com/maps/api/place/details/json?place_id="+placeId+
                 "&key="+"AIzaSyDIC9wuMhHNNjFIr6UZfb64h1Rmauaz7hw";

         retrofitApi.getResaurantIdRetrofit(url).enqueue(new Callback<GoogleResponseModel>() {
             @Override
             public void onResponse(Call<GoogleResponseModel> call, Response<GoogleResponseModel> response) {
                 restaurantName.setText(googlePlaceModel.getName());

             }

             @Override
             public void onFailure(Call<GoogleResponseModel> call, Throwable t) {

             }
         });
    }

}
