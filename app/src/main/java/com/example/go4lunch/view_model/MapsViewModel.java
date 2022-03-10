package com.example.go4lunch.view_model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.GooglePlaceModel;
import com.example.go4lunch.repository.MapRepository;
import com.example.go4lunch.repository.PlacesRepository;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Map;

public class MapsViewModel extends ViewModel {

    // instancier le Repository de places
    //créer un singleton pour chaques repository.
    private static final PlacesRepository placesRepository= new PlacesRepository();


    // remettre la map pour le fragment.
    // faire lien entre repo et fragment
    // return la livedata du repository et c'est tout.

    public LiveData<List<GooglePlaceModel>>getListRestaurant(){return placesRepository.getRestaurantName();}



    //gérer les marqueurs dans le ViewModel si possible.


}
