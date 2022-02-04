package com.example.go4lunch.webServices;

import com.example.go4lunch.response.GoogleResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitApi {

    @GET
    Call<GoogleResponseModel> getNearByPlaces(@Url String url);
}
