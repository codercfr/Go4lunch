package com.example.go4lunch;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class ShowRestaurantActivity extends AppCompatActivity {

    private ImageView restaurantView;
    private TextView restaurantName;
    private RatingBar rtnRestaurant;
    private TextView streetRestaurantName;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showrestaurant);
        restaurantView=findViewById(R.id.img_restaurant_user);
        restaurantName=findViewById(R.id.restaurantname);
        rtnRestaurant=findViewById(R.id.ratingBarSelectedR);
        streetRestaurantName=findViewById(R.id.street_NameSelectedR);


    }
}
