package com.example.go4lunch;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.bumptech.glide.Glide;
import com.example.go4lunch.adapter.RestaurantAdapter;
import com.example.go4lunch.model.SavedPlaceModel;
import com.example.go4lunch.model.Users;
import com.example.go4lunch.view_model.RestaurantViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class ShowRestaurantActivity extends AppCompatActivity {

    private ImageView restaurantView;
    private TextView restaurantName;
    private RatingBar rtnRestaurant;
    private TextView streetRestaurantName;
    private FirebaseAuth mAuth;
    private String placeId;
    private DatabaseReference databaseReference;
    private Users user;
    private SavedPlaceModel savedPlaceModel = new SavedPlaceModel();
    private final List<Users> usersList= new ArrayList<>();
    private final RestaurantAdapter restaurantAdapter = new RestaurantAdapter(usersList,this);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showrestaurant);
        restaurantView = findViewById(R.id.id_restaurantimg);
        restaurantName = findViewById(R.id.restaurantname);
        rtnRestaurant = findViewById(R.id.ratingBarSelectedR);
        streetRestaurantName = findViewById(R.id.street_NameSelectedR);
        ImageView callButton = findViewById(R.id.action_call);
        ImageView likeButton = findViewById(R.id.action_like);
        ImageView websiteButton = findViewById(R.id.action_website);
        FloatingActionButton restaurantForLunch = findViewById(R.id.add_choices);
        //cr??e un frag pour le faire marcher.
        RecyclerView recyclerView =(RecyclerView) findViewById(R.id.show_restaurant_recyclerview);
        //recycler vide pour l'instant.
        recyclerView.setAdapter(restaurantAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance("https://go4lunch-5272f-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference= mDatabase.getReference("Users");
        RestaurantViewModel restaurantViewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);

        databaseReference.child((Objects.requireNonNull(mAuth.getCurrentUser())).getUid()).get().addOnSuccessListener(dataSnapshot -> user= dataSnapshot.getValue(Users.class));
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
                user.getLikes().add(savedPlaceModel.getVicinity());
                databaseReference.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                        .setValue(user);
            }catch (Exception e){
                Toast.makeText(this,"no entry into LikeList",Toast.LENGTH_LONG).show();
            }
        });


        websiteButton.setOnClickListener(view -> {
            String url = savedPlaceModel.getWebsite();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        restaurantForLunch.setOnClickListener(view -> {
            user.setPlaceId(savedPlaceModel.getPlaceId());
            user.setRestaurantName(savedPlaceModel.getName());
            try {
                // A voir pour l'instant plante l'appli
                user.setPhotoUser(Objects.requireNonNull(mAuth.getCurrentUser().getPhotoUrl()).toString());
            }catch (Exception exception) {
                exception.printStackTrace();
            }
            oneRequest();
            usersList.add(user);
            databaseReference.child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                    .setValue(user);
            restaurantAdapter.notifyDataSetChanged();
        });

    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull String name, @NonNull @NotNull Context context, @NonNull @NotNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);

    }

    // doit faire une requete on on passe le place id regarder la requete placedetail
    private void receiveData() {
        Intent i = getIntent();
        placeId = i.getStringExtra("ID");
    }

    private int getStarsRestaurant(SavedPlaceModel savedPlaceModel){
        double rating = savedPlaceModel.getRating();
        return (int)rating;
    }

    public void getRestaurantId() {

        restaurantName.setText(savedPlaceModel.getName());
        streetRestaurantName.setText(savedPlaceModel.getAddress());
        rtnRestaurant.setNumStars(getStarsRestaurant(savedPlaceModel));
        String urlPhoto = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=150&photoreference="
                + savedPlaceModel.getPhotos().get(0).getPhotoReference() + "&sensor=true&key=AIzaSyDIC9wuMhHNNjFIr6UZfb64h1Rmauaz7hw";
        Glide.with(restaurantView.getContext())
                .load(urlPhoto)
                .into(restaurantView);
            }

    public void  oneRequest(){
        Date nextDay= new Date();
        Calendar cal= Calendar.getInstance();
        cal.setTime(nextDay);
        cal.add((Calendar.DATE),1);
        cal.set((Calendar.HOUR_OF_DAY),12);
        cal.set((Calendar.MINUTE),0);
        nextDay=cal.getTime();
        Date currentTime = Calendar.getInstance().getTime();
        long diffhours = nextDay.getTime()-currentTime.getTime();
        int hours=(int)(diffhours/(1000*60*60));

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(NotificationCoworker.class)
                .setInitialDelay(hours, TimeUnit.DAYS)
                .build();

        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);

    }
}
