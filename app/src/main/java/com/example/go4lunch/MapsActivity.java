package com.example.go4lunch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import com.example.go4lunch.fragments.CoworkerFragment;
import com.example.go4lunch.fragments.HomeFragment;
import com.example.go4lunch.fragments.MapFragment;
import com.example.go4lunch.model.Users;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class MapsActivity extends AppCompatActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback,NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;
    private Users user;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase mDatabase;



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
        mAuth = FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance("https://go4lunch-5272f-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference= mDatabase.getReference("Users");
        databaseReference.child((mAuth.getCurrentUser()).getUid()).get().addOnSuccessListener(dataSnapshot ->  {
            user= dataSnapshot.getValue(Users.class);
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
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


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.lunch_restaurant:
                Intent intent = new Intent(this, ShowRestaurantActivity.class);
                intent.putExtra("ID",user.getPlaceId());
                try {
                    startActivity(intent);
                }
                catch (Exception exception){
                    exception.printStackTrace();
                }
                break;
            case R.id.setings:
              // a voir comment s'occuper des notifications.
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent newIntent = new Intent(this, MainActivity.class);
                startActivity(newIntent);
                break;
    }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
