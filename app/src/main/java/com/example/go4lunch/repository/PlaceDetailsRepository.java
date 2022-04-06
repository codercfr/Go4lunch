package com.example.go4lunch.repository;

import android.content.Intent;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.model.SavedPlaceModel;
import com.example.go4lunch.response.SavedPlaceResponseModel;
import com.example.go4lunch.webServices.RetrofitApi;
import com.example.go4lunch.webServices.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceDetailsRepository {

    private SavedPlaceModel savedPlaceModel= new SavedPlaceModel();

    private RetrofitApi retrofitApi;

    final MutableLiveData<SavedPlaceModel> savedPlaceModelMutableLiveData= new MutableLiveData<>();

    //récupérer la valeur d'intent


    // mettre en parametre le place id
    public MutableLiveData<SavedPlaceModel> getSavedPlaceModel(String placeId){

        retrofitApi = RetrofitClient.getRetrofitClient().create(RetrofitApi.class);
        String url = "https://maps.googleapis.com/maps/api/place/details/json?place_id=" + placeId +
                "&key=AIzaSyDIC9wuMhHNNjFIr6UZfb64h1Rmauaz7hw";

        retrofitApi.getRestaurantIdRetrofit(url).enqueue(new Callback<SavedPlaceResponseModel>() {
            @Override
            public void onResponse(Call<SavedPlaceResponseModel> call, Response<SavedPlaceResponseModel> response) {
                savedPlaceModel=response.body().getSavedPlaceModel();
                savedPlaceModelMutableLiveData.setValue(savedPlaceModel);
            }

            @Override
            public void onFailure(Call<SavedPlaceResponseModel> call, Throwable t) {

            }
        });

        return savedPlaceModelMutableLiveData;
    }



    }


