package com.example.go4lunch.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_saved_places, container, false);
        retrofitApi = RetrofitClient.getRetrofitClient().create(RetrofitApi.class);
        recyclerView=rootView.findViewById(R.id.savedRecyclerView);
        // remplacer par le autocomplete de google
        Places.initialize(requireContext(), "AIzaSyDIC9wuMhHNNjFIr6UZfb64h1Rmauaz7hw");
        PlacesClient placesClient = Places.createClient(requireContext());
        // Initialize the AutocompleteSupportFragment.
        // pour getChildFragmentManager dans un framgent
        //getParentFramentManager pour une activité
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        assert autocompleteFragment != null;
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Intent intent = new Intent(requireContext(), ShowRestaurantActivity.class);
                intent.putExtra("ID",place.getId());
                try {
                   startActivity(intent);
                }
                catch (Exception exception){
                    exception.printStackTrace();
                }
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.

            }
        });
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
                    for (int i =0 ; i<googlePlaceModels.size();i++){
                        restaurantName.add(googlePlaceModels.get(i).getName());
                    }
                    googlePlaceAdapter.notifyDataSetChanged();

                });

    }


}