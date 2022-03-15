package com.example.go4lunch.webServices;

import com.example.go4lunch.model.SavedPlaceModel;
import com.example.go4lunch.response.GoogleResponseModel;
import com.example.go4lunch.response.SavedPlaceResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitApi {

    @GET
    Call<GoogleResponseModel> getNearByPlaces(@Url String url);

    @GET
    Call<SavedPlaceResponseModel> getRestaurantIdRetrofit(@Url String url );

    // crée une nouveau model pour juste un googlePlaceDetails.


}
