package com.example.go4lunch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.bumptech.glide.Glide;
import com.example.go4lunch.fragments.CoworkerFragment;
import com.example.go4lunch.fragments.HomeFragment;
import com.example.go4lunch.fragments.MapFragment;
import com.example.go4lunch.model.Users;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.example.go4lunch.NotificationCoworker.CHANNEL_1_ID;

public class MapsActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private Users user;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase mDatabase;
    private ImageView search, navImageUser;
    private TextView navUsername,navEmail;
    private boolean notifoff=true;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.userNameMenu);
        navEmail=(TextView)headerView.findViewById(R.id.navEmail);
        navImageUser=(ImageView)headerView.findViewById(R.id.navImageUser);
        mAuth = FirebaseAuth.getInstance();
        search=findViewById(R.id.search);
        mDatabase=FirebaseDatabase.getInstance("https://go4lunch-5272f-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference= mDatabase.getReference("Users");
        databaseReference.child((mAuth.getCurrentUser()).getUid()).get().addOnSuccessListener(dataSnapshot ->  {
            user= dataSnapshot.getValue(Users.class);
            navUsername.setText(Objects.requireNonNull(user).getUsername());
            navEmail.setText(user.getEmail());
            //fonctionne pas pour l'instant
            try{
            Glide.with(navImageUser.getContext())
                    .load(user.getPhotoUser())
                    .into(navImageUser);
            }catch (Exception exception) {
                exception.printStackTrace();
            }
        });



        Places.initialize(getApplicationContext(),"AIzaSyDIC9wuMhHNNjFIr6UZfb64h1Rmauaz7hw");

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        assert data != null;
                        Place place = Autocomplete.getPlaceFromIntent(data);
                        //editText.setText(place.getAddress());
                        Intent intent = new Intent(this, ShowRestaurantActivity.class);
                        intent.putExtra("ID",place.getId());
                        try {
                            startActivity(intent);
                        }
                        catch (Exception exception){
                            exception.printStackTrace();
                        }
                    }
                });

        search.setOnClickListener(view -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ID,Place.Field.NAME);
            Intent intent= new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList)
                    .build(MapsActivity.this);
            someActivityResultLauncher.launch(intent);

        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.action_android:
                    selectedFragment = new MapFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.constraint_bottom_view, selectedFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;

                case R.id.action_logo:
                    selectedFragment = new HomeFragment();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.constraint_bottom_view, selectedFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;

                case R.id.coworkerList:
                    selectedFragment = new CoworkerFragment();
                    transaction= getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.constraint_bottom_view,selectedFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;

            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            assert false;
            transaction.replace(R.id.content, selectedFragment);
            transaction.commit();
            return true;
        });
    }

    @Override
    public void onBackPressed(){
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressLint({"NonConstantResourceId", "RestrictedApi"})
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.lunch_restaurant:
                Intent intent = new Intent(this, ShowRestaurantActivity.class);
                intent.putExtra("ID",user.getPlaceId());
                try {
                    if(user.getRestaurantName()==null){
                        Toast.makeText(this,R.string.coworkerChoice,Toast.LENGTH_LONG).show();
                    }else {
                        startActivity(intent);
                    }
                }
                catch (Exception exception){
                    exception.printStackTrace();
                }
                break;
            case R.id.setings:

                if(notifoff) {
                    NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, CHANNEL_1_ID);
                    //cancel peut être pour la notification.
                    notifBuilder.mActions.clear();
                    Toast.makeText(this, R.string.notif_off, Toast.LENGTH_LONG).show();
                    notifoff = false;
                }
                else {
                    oneRequest();
                    Toast.makeText(this, R.string.notif_on, Toast.LENGTH_LONG).show();
                    notifoff=true;
                }

                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                FirebaseDatabase.getInstance().getReference("Token").child(user.getUid()).child("token").removeValue();
                finish();
                break;
    }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
