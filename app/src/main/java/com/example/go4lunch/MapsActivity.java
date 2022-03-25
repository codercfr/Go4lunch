package com.example.go4lunch;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.go4lunch.fragments.CoworkerFragment;
import com.example.go4lunch.fragments.HomeFragment;
import com.example.go4lunch.fragments.MapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MapsActivity extends FragmentActivity implements
        ActivityCompat.OnRequestPermissionsResultCallback {



    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_view);

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

 }
