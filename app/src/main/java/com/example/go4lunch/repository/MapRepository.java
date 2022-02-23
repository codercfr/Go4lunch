package com.example.go4lunch.repository;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.R;
import com.example.go4lunch.model.GooglePlaceModel;
import com.example.go4lunch.response.GoogleResponseModel;
import com.example.go4lunch.webServices.RetrofitApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapRepository {
    /*private GoogleMap mMap;
    double currentLat = -33.8670522, currrentLong = 151.1957362;
    private Button btn_find;
    private Marker currentMarker;
    private Location currentLocation;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;
    private RetrofitApi retrofitApi;
    private List<GooglePlaceModel> googlePlaceModelList;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Context context;
    private boolean isLocationPermissionOk;
    private MutableLiveData<List<Location>>locationList= new MutableLiveData<>();
    private List<GooglePlaceModel> listGooglePlaceModels = new ArrayList<>() ;
    // reprendre le répo Places


    //utiliser le getAplicationContext

    public void getRestaurant(String ResturantName){
        //remplacer current lat et currentlong pour avoir les bonnes valeurs
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"+"?location="+currentLat+","
                +currrentLong+"&radius=5000"
                +"&types=restaurant"
                +"&sensor=false"
                +"&key="+ (R.string.apiKey);

        // remplacer le retrofit
        retrofitApi.getNearByPlaces(url).enqueue(new Callback<GoogleResponseModel>() {
            @Override
            public void onResponse(Call<GoogleResponseModel> call, Response<GoogleResponseModel> response) {
                try {
                    mMap.clear();
                    googlePlaceModelList.clear();
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < Objects.requireNonNull(response.body()).getGooglePlaceModelList().size(); i++) {
                        Double lat = response.body().getGooglePlaceModelList().get(i).getGeometry().getLocation().getLat();
                        Double lng = response.body().getGooglePlaceModelList().get(i).getGeometry().getLocation().getLng();
                        String placeName = response.body().getGooglePlaceModelList().get(i).getName();
                        String vicinity = response.body().getGooglePlaceModelList().get(i).getVicinity();
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
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<GoogleResponseModel> call, Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }
*/


}
