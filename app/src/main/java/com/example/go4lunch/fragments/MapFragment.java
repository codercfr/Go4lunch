package com.example.go4lunch.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.R;
import com.example.go4lunch.model.GooglePlaceModel;
import com.example.go4lunch.view_model.MapsViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private Marker currentMarker;
    private Location currentLocation;
    private LocationRequest locationRequest;
    private int radius = 5000;
    private boolean isLocationPermissionOk;
    private List<GooglePlaceModel> googlePlaceModelList;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private MapsViewModel mapsViewModel;
    private ArrayList<String>restaurantName= new ArrayList<>();
    private ArrayAdapter<String>adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_map, container, false);

        googlePlaceModelList=new ArrayList<>();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }


        //ArrayAdapter pour le autocomplete
        //context,adapter déja programmé, la liste de noms des restaurants.
        adapter= new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,restaurantName);
        //initialize fusef location provider client
        //initialize fusef location provider client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        mapsViewModel= new ViewModelProvider(this).get(MapsViewModel.class);
        return rootview;
    }
    private void setUpLocationUpdate() {

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

       getCurentLocation();



    }

    private void moveCameraToLocation(Location location) {

        //zoom +
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new
                LatLng(location.getLatitude(), location.getLongitude()), 17);

        MarkerOptions markerOptions = new MarkerOptions()
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .title("Current Location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));


        if (currentMarker != null) {
            currentMarker.remove();
        }

        currentMarker = mMap.addMarker(markerOptions);
        assert currentMarker != null;
        currentMarker.setTag(703);
        mMap.animateCamera(cameraUpdate);

    }

    private void getCurentLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            isLocationPermissionOk = false;
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            currentLocation=location;
            moveCameraToLocation(currentLocation);
            //lancé la requete réseau.
            //récupérer la location et lancé l'observer livedata
            mapsViewModel.getListRestaurant()
                    .observe(getViewLifecycleOwner(),googlePlaceModels -> {
                        this.googlePlaceModelList.clear();
                        this.googlePlaceModelList.addAll(googlePlaceModels);
                        getRestaurant();
                    });

        });
    }

    public void getRestaurant(){
        try {
            mMap.clear();
            // pas de getValue sur LiveData
            // This loop will go through all the results and add marker on each location.
            for (int i = 0; i < googlePlaceModelList.size(); i++) {
                Double lat = googlePlaceModelList.get(i).getGeometry().getLocation().getLat();
                Double lng = googlePlaceModelList.get(i).getGeometry().getLocation().getLng();
                String placeName = googlePlaceModelList.get(i).getName();
                String vicinity = googlePlaceModelList.get(i).getVicinity();
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng latLng = new LatLng(lat, lng);
                // Position of Marker on Map
                markerOptions.position(latLng);
                // Adding Title to the Marker
                markerOptions.title(placeName + " : " + vicinity);
                // Adding Marker to the Camera.
                Marker m = mMap.addMarker(markerOptions);
                // Adding colour to the marker
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                // move map camera
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
                restaurantName.add(placeName);
            }
        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
        }

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NotNull GoogleMap googleMap) {
         mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            //le remettre en place une fois que l'obersable est crée.
            setUpLocationUpdate();
            adapter.notifyDataSetChanged();

        } else {
            ActivityCompat.requestPermissions((Activity) requireContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }

    }



}


