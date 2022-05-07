package com.example.go4lunch.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.model.GooglePlaceModel;
import com.example.go4lunch.response.GoogleResponseModel;
import com.example.go4lunch.webServices.RetrofitApi;
import com.example.go4lunch.webServices.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.go4lunch.webServices.RetrofitClient.BASE_URL_Google;

public class PlacesRepository {

    private List<GooglePlaceModel> listGooglePlaceModels = new ArrayList<>() ;
    private RetrofitApi retrofitApi;
    final MutableLiveData<List<GooglePlaceModel>> googlePlaceModels = new MutableLiveData<>();

    public PlacesRepository() {
    }

    @SuppressLint("VisibleForTests")
    public MutableLiveData<List<GooglePlaceModel>>  getRestaurantName(double currentLat, double currrentLong){
        retrofitApi = RetrofitClient.getRetrofitClient(HttpUrl.get(BASE_URL_Google)).create(RetrofitApi .class);
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"+"?location="+currentLat+","
                +currrentLong+"&radius=5000"
                +"&types=restaurant"
                +"&sensor=false"
                +"&key="+ "AIzaSyDIC9wuMhHNNjFIr6UZfb64h1Rmauaz7hw";

        retrofitApi.getNearByPlaces(url).enqueue(new Callback<GoogleResponseModel>() {
            @Override
            public void onResponse(Call<GoogleResponseModel> call, Response<GoogleResponseModel> response) {
                try {
                        listGooglePlaceModels.clear();
                        // This loop will go through all the results and add marker on each location.
                        for (int i = 0; i < Objects.requireNonNull(response.body()).getGooglePlaceModelList().size(); i++) {
                            //googlePlaceModels.setValue(response.body().getGooglePlaceModelList());
                            listGooglePlaceModels.add(response.body().getGooglePlaceModelList().get(i));
                        }
                    googlePlaceModels.setValue(listGooglePlaceModels);
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
        //setting the value of MutableLivedata
        return googlePlaceModels;
    }
}


//un repository par theme/fonction


// viewmodel récupére les données du repository pour ce dont il a besoin

//repository est un singleton

//p3 regarder pour séléctionner dans le reyclerview