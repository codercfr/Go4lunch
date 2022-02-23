package com.example.go4lunch.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.GooglePlaceModel;
import com.example.go4lunch.repository.PlacesRepository;

import java.util.List;

public class PlacesViewModel extends ViewModel {
    //renomer les ViewModel pour avoir le même nom que le frag.
    // le viewmodel factory sert si le viewmodel à besoin de paramètre.

    private static final  PlacesRepository repository = new PlacesRepository();


    public LiveData<List<GooglePlaceModel>> getTasks() {
        return repository.getRestaurantName();
    }

    public static PlacesRepository getRepository(){
        return repository;
    }


}
