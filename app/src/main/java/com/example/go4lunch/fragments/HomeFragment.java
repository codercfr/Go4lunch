package com.example.go4lunch.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.ShowRestaurantActivity;
import com.example.go4lunch.adapter.GooglePlaceAdapter;
import com.example.go4lunch.model.GooglePlaceModel;
import com.example.go4lunch.view_model.PlacesViewModel;
import com.example.go4lunch.webServices.RetrofitApi;
import com.example.go4lunch.webServices.RetrofitClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class HomeFragment extends Fragment {
    @NonNull
    private RecyclerView recyclerView;
    private List<GooglePlaceModel> googlePlaceModels = new ArrayList<>();
    private final GooglePlaceAdapter  googlePlaceAdapter = new GooglePlaceAdapter(googlePlaceModels);
    private RetrofitApi retrofitApi;
    private PlacesViewModel placesViewModel;
    private ArrayList<String>restaurantName= new ArrayList<>();
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_saved_places, container, false);
        retrofitApi = RetrofitClient.getRetrofitClient().create(RetrofitApi.class);
        recyclerView=rootView.findViewById(R.id.savedRecyclerView);
        // remplacer par le autocomplete de google
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());
        placesViewModel= new ViewModelProvider(this).get(PlacesViewModel.class);
        return rootView;
    }

    @Override
    public void onViewCreated( @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(googlePlaceAdapter);
        //creat method pour le viewmodel
        getCurentLocation();

    }




    private void getCurentLocation() {

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            currentLocation=location;
            //lancé la requete réseau.
            //récupérer la location et lancé l'observer livedata
            placesViewModel.getTasks(currentLocation.getLatitude(),currentLocation.getLongitude())
                    .observe(getViewLifecycleOwner(),googlePlaceModels -> {
                        this.googlePlaceModels.clear();
                        this.googlePlaceModels.addAll(googlePlaceModels);
                        for (int i =0 ; i<googlePlaceModels.size();i++){
                            restaurantName.add(googlePlaceModels.get(i).getName());
                        }
                        googlePlaceAdapter.notifyDataSetChanged();
                    });
            });
        }else{
            ActivityCompat.requestPermissions((Activity) requireContext(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

}