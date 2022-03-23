package com.example.go4lunch;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.go4lunch.model.SavedPlaceModel;
import com.example.go4lunch.model.Users;
import com.example.go4lunch.view_model.RestaurantViewModel;
import com.example.go4lunch.webServices.RetrofitApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ShowRestaurantActivity extends AppCompatActivity {

    private ImageView restaurantView;
    private TextView restaurantName;
    private RatingBar rtnRestaurant;
    private TextView streetRestaurantName;
    private FirebaseAuth mAuth;
    private ImageView callButton, likeButton,websiteButton;
    private String placeId;
    private FirebaseDatabase mDatabase;
    private DatabaseReference databaseReference;
    private Users user;
    private List<String> likesList = new ArrayList<>();
    private SavedPlaceModel savedPlaceModel = new SavedPlaceModel();
    private FloatingActionButton restaurantForLunch;
    private FirebaseUser currentFirbaseUser;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showrestaurant);
        restaurantView = findViewById(R.id.id_restaurantimg);
        restaurantName = findViewById(R.id.restaurantname);
        rtnRestaurant = findViewById(R.id.ratingBarSelectedR);
        streetRestaurantName = findViewById(R.id.street_NameSelectedR);
        callButton=findViewById(R.id.action_call);
        likeButton=findViewById(R.id.action_like);
        websiteButton=findViewById(R.id.action_website);
        restaurantForLunch=findViewById(R.id.add_choices);
        mAuth = FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance("https://go4lunch-5272f-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference= mDatabase.getReference("Users");
        RestaurantViewModel restaurantViewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);
        receiveData();
        restaurantViewModel.getSavedRestaurant(placeId)
                .observe(this, savedPlaceModel -> {
                    this.savedPlaceModel=savedPlaceModel;
                    getRestaurantId();
                });

        callButton.setOnClickListener(view -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:"+savedPlaceModel.getPhoneNumber()));
            startActivity(callIntent);
        });

        likeButton.setOnClickListener(view -> {
            try {
                //trouver comment le faire reprendre le current user.
                //user=mAuth.getCurrentUser();
                user=new Users();
                currentFirbaseUser=mAuth.getCurrentUser();
                likesList.add(savedPlaceModel.getVicinity());
                user.setLikes(likesList);
                databaseReference.child("Users")
                        .child(currentFirbaseUser.getUid())
                        .setValue(user);
            }catch (Exception e){
                Toast.makeText(this,"no entry into LikeList",Toast.LENGTH_LONG);
            }
        });

        websiteButton.setOnClickListener(view -> {
            String url = savedPlaceModel.getWebsite();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        // oublier le type de restaurant pas demandé.
        // pour les likes ajouter dans la liste des likes de la personne
        //enregistrer sur firebase.
        //afficher la liste des personnes qui à séléctionner dans le restaurants ( rien a voir avec les likes ).

    }

    // doit faire une requete on on passe le place id regarder la requete placedetail 
    private void receiveData() {
        Intent i = getIntent();
        String name = i.getStringExtra("ID");
        placeId = name;
    }

    private void getStarsRestaurant(SavedPlaceModel savedPlaceModel){
        double rating = savedPlaceModel.getRating();
        int i = (int)rating;
        switch (i){
            case 1 :i=1;
                rtnRestaurant.setNumStars(1);
                break;

            case 2:i=2;
                rtnRestaurant.setNumStars(2);
                break;

            case 3:i=3;
                rtnRestaurant.setNumStars(3);
                break;

            case 4:i=4;
                rtnRestaurant.setNumStars(4);

            case 5:i=5;
                rtnRestaurant.setNumStars(5);
        }
        // return int du restaurant
    }



    public void getRestaurantId() {

        restaurantName.setText(savedPlaceModel.getName());
        streetRestaurantName.setText(savedPlaceModel.getAddress());
        // reprendre le int du restraunt et le set dans la méthode setNumstars.
        rtnRestaurant.setNumStars(savedPlaceModel.getRating().intValue());
        //getStarsRestaurant(savedPlaceModel);
        String urlPhoto = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=150&photoreference="
                + savedPlaceModel.getPhotos().get(0).getPhotoReference() + "&sensor=true&key=AIzaSyDIC9wuMhHNNjFIr6UZfb64h1Rmauaz7hw";
        Glide.with(restaurantView.getContext())
                .load(urlPhoto)
                .into(restaurantView);
            }


}
