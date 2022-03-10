package com.example.go4lunch.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.R;
import com.example.go4lunch.ShowRestaurantActivity;
import com.example.go4lunch.adapter.GooglePlaceAdapter;
import com.example.go4lunch.model.GooglePlaceModel;
import com.example.go4lunch.response.GoogleResponseModel;
import com.example.go4lunch.view_model.PlacesViewModel;
import com.example.go4lunch.webServices.RetrofitApi;
import com.example.go4lunch.webServices.RetrofitClient;
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


public class HomeFragment extends Fragment {
    @NonNull
    private RecyclerView recyclerView;
    private List<GooglePlaceModel> googlePlaceModels = new ArrayList<>();
    private final GooglePlaceAdapter  googlePlaceAdapter = new GooglePlaceAdapter(googlePlaceModels);
    private RetrofitApi retrofitApi;
    private PlacesViewModel placesViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_saved_places, container, false);
        retrofitApi = RetrofitClient.getRetrofitClient().create(RetrofitApi.class);
        recyclerView=rootView.findViewById(R.id.savedRecyclerView);
        //Init of ViewModel
        placesViewModel= new ViewModelProvider(this).get(PlacesViewModel.class);
        return rootView;
    }

    @Override
    public void onViewCreated( @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(googlePlaceAdapter);
        //creat method pour le viewmodel
        placesViewModel.getTasks()
                .observe(getViewLifecycleOwner(), googlePlaceModels -> {
                    //
                    this.googlePlaceModels.clear();
                    this.googlePlaceModels.addAll(googlePlaceModels);
                    googlePlaceAdapter.notifyDataSetChanged();

                });
    }


}