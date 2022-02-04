package com.example.go4lunch.view_model;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.model.GooglePlaceModel;
import com.example.go4lunch.repository.PlacesRepository;
import com.example.go4lunch.response.GoogleResponseModel;

import java.util.List;

public class PlacesViewModel extends ViewModel {

    PlacesRepository repository;

    public PlacesViewModel(Context context, RecyclerView recyclerView){
        repository=new PlacesRepository(context,recyclerView);

    }

    public MutableLiveData<List<GooglePlaceModel>> getTasks() {
        return repository.getRestaurantName();
    }


}
