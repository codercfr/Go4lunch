package com.example.go4lunch.view_model;

import android.util.Log;

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
    public void getRestaurant(GoogleMap map,List<GooglePlaceModel> googlePlaceModel){
        try {
            map.clear();
            googlePlaceModel.clear();
            // pas de getValue sur LiveData
            googlePlaceModel= placesRepository.getRestaurantName().getValue();

            // This loop will go through all the results and add marker on each location.
            for (int i = 0; i < googlePlaceModel.size(); i++) {
                Double lat = googlePlaceModel.get(i).getGeometry().getLocation().getLat();
                Double lng = googlePlaceModel.get(i).getGeometry().getLocation().getLng();
                String placeName = googlePlaceModel.get(i).getName();
                String vicinity = googlePlaceModel.get(i).getVicinity();
                MarkerOptions markerOptions = new MarkerOptions();
                LatLng latLng = new LatLng(lat, lng);
                // Position of Marker on Map
                markerOptions.position(latLng);
                // Adding Title to the Marker
                markerOptions.title(placeName + " : " + vicinity);
                // Adding Marker to the Camera.
                Marker m = map.addMarker(markerOptions);
                // Adding colour to the marker
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                // move map camera
                map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                map.animateCamera(CameraUpdateFactory.zoomTo(11));
            }
        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
        }

    }

    //gérer les marqueurs dans le ViewModel si possible.


}
