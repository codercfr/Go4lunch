package com.example.go4lunch.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.go4lunch.model.Article;
import com.example.go4lunch.repository.PlacesRepository;
import com.example.go4lunch.response.PlacesResponse;

public class PlaceViewModel extends AndroidViewModel {

    private PlacesRepository placesReponse;
    private LiveData<PlacesResponse> placesResponseLiveData;

    public PlaceViewModel(@NonNull Application application) {
        super(application);
        placesReponse = new PlacesRepository();
        this.placesResponseLiveData = placesReponse.getDashBordNews();
    }

    public LiveData<PlacesResponse>getBashBoardNewsResponseLiveData(){
         return placesResponseLiveData;
    }
}
