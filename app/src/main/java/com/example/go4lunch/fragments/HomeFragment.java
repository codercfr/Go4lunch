package com.example.go4lunch.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.go4lunch.R;
import com.example.go4lunch.adapter.ArticleAdapter;
import com.example.go4lunch.adapter.GooglePlaceAdapter;
import com.example.go4lunch.model.Article;
import com.example.go4lunch.model.GooglePlaceModel;
import com.example.go4lunch.model.GoogleResponseModel;
import com.example.go4lunch.view_model.PlaceViewModel;
import com.example.go4lunch.webServices.RetrofitApi;
import com.example.go4lunch.webServices.RetrofitClient;
import com.google.android.gms.maps.CameraUpdateFactory;
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
    PlaceViewModel placeViewModel;
    double currentLat = -33.8670522, currrentLong = 151.1957362;
    private RetrofitApi retrofitApi;
    private LocationManager locationManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.place_item_layout, container, false);
        retrofitApi = RetrofitClient.getRetrofitClient().create(RetrofitApi.class);
        recyclerView=rootView.findViewById(R.id.savedRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(googlePlaceAdapter);
        getRestaurantName();
        return rootView;
    }


    public void getRestaurantName(){
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"+"?location="+currentLat+","
                +currrentLong+"&radius=5000"
                +"&types=restaurant"
                +"&sensor=false"
                +"&key="+ getResources().getString(R.string.apiKey);

        retrofitApi.getNearByPlaces(url).enqueue(new Callback<GoogleResponseModel>() {
            @Override
            public void onResponse(Call<GoogleResponseModel> call, Response<GoogleResponseModel> response) {
                try {
                    googlePlaceModels.clear();
                    // This loop will go through all the results and add marker on each location.
                    for (int i = 0; i < Objects.requireNonNull(response.body()).getGooglePlaceModelList().size(); i++) {
                        googlePlaceModels.add(response.body().getGooglePlaceModelList().get(i));
                    }
                   googlePlaceAdapter.notifyDataSetChanged();
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


    @Override
    public void onViewCreated( @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //getRestaurantName();
    }





}