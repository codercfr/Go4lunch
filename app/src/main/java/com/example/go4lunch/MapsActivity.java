package com.example.go4lunch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.go4lunch.fragments.HomeFragment;
import com.example.go4lunch.fragments.MapFragment;
import com.example.go4lunch.fragments.SavedPlacesFragment;
import com.example.go4lunch.model.GooglePlaceModel;
import com.example.go4lunch.model.GoogleResponseModel;
import com.example.go4lunch.webServices.RetrofitApi;
import com.example.go4lunch.webServices.RetrofitClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,

        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private Location currentLocation;
    private int radius = 5000;
    private boolean isLocationPermissionOk;
    private RetrofitApi retrofitApi;
    private List<GooglePlaceModel> googlePlaceModelList;
    private GoogleMap mGoogleMap;
    private ArrayList<String> userSavedLocationId;
    private BottomNavigationView bottomNavigationView;
    private MapFragment mapFragment;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);
        bottomNavigationView = findViewById(R.id.bottom_view);

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
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            assert false;
            transaction.replace(R.id.content, selectedFragment);
            transaction.commit();
            return true;
        });
    }


    private void addMarker(GooglePlaceModel googlePlaceModel, int position) {

        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(googlePlaceModel.getGeometry().getLocation().getLat(),
                        googlePlaceModel.getGeometry().getLocation().getLng()))
                .title(googlePlaceModel.getName())
                .snippet(googlePlaceModel.getVicinity());
        mGoogleMap.addMarker(markerOptions).setTag(position);
    }


   /* *//**
     * Enables the My Location layer if the fine location permission has been granted.
     *//*
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            String[] perm ={"android.permission.ACCESS_FINE_LOCATION"};
            // Permission to access the location is missing. Show rationale and request permission
            ActivityCompat.requestPermissions(this,perm,200);
        }
    }*/


    private void getPlaces(String placeName) {

        if (isLocationPermissionOk) {
            String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
                    + currentLocation.getLatitude() + "," + currentLocation.getLongitude()
                    + "&radius=" + radius + "&type=" + placeName + "&key=" +
                    getResources().getString(R.string.apiKey);


            if (currentLocation != null) {


                retrofitApi.getNearByPlaces(url).enqueue(new Callback<GoogleResponseModel>() {
                    @Override
                    public void onResponse(@NonNull Call<GoogleResponseModel> call, @NonNull Response<GoogleResponseModel> response) {
                        Gson gson = new Gson();
                        String res = gson.toJson(response.body());
                        Log.d("TAG", "onResponse: " + res);
                        if (response.errorBody() == null) {
                            if (response.body() != null) {
                                if (response.body().getGooglePlaceModelList() != null && response.body().getGooglePlaceModelList().size() > 0) {

                                    googlePlaceModelList.clear();
                                    mGoogleMap.clear();
                                    for (int i = 0; i < response.body().getGooglePlaceModelList().size(); i++) {

                                        if (userSavedLocationId.contains(response.body().getGooglePlaceModelList().get(i).getPlaceId())) {
                                            response.body().getGooglePlaceModelList().get(i).setSaved(true);
                                        }
                                        googlePlaceModelList.add(response.body().getGooglePlaceModelList().get(i));
                                        addMarker(response.body().getGooglePlaceModelList().get(i), i);
                                    }

                                    //  googlePlaceAdapter.setGooglePlaceModels(googlePlaceModelList);

                                }
                                else {

                                    mGoogleMap.clear();
                                    googlePlaceModelList.clear();
                                    // googlePlaceAdapter.setGooglePlaceModels(googlePlaceModelList);
                                    radius += 1000;
                                    Log.d("TAG", "onResponse: " + radius);
                                    getPlaces(placeName);

                                }
                            }

                        } else {
                            Log.d("TAG", "onResponse: " + response.errorBody());
                            // Toast.makeText(requireContext(), "Error : " + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<GoogleResponseModel> call, Throwable t) {

                    }
                });


            }

        }

    }



    @Override
    public void onMapReady (@NonNull @NotNull GoogleMap googleMap){
        mMap = googleMap;
    }
}