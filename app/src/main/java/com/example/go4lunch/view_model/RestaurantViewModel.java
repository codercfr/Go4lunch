package com.example.go4lunch.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.SavedPlaceModel;
import com.example.go4lunch.repository.PlaceDetailsRepository;

public class RestaurantViewModel extends ViewModel {



    private static final PlaceDetailsRepository placeDetailsRepo = new PlaceDetailsRepository();

    public LiveData<SavedPlaceModel> getSavedRestaurant(String restaurant){return placeDetailsRepo.getSavedPlaceModel(restaurant);}

    public static PlaceDetailsRepository getPlaceDetailsRepo(){return placeDetailsRepo;}


}
