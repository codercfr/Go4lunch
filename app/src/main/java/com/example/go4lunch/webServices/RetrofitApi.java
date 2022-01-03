package com.example.go4lunch.webServices;

import com.example.go4lunch.model.GoogleResponseModel;
import com.example.go4lunch.response.PlacesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

import static com.example.go4lunch.constants.AppConstant.API_KEY;

public interface RetrofitApi {

    @GET
    Call<GoogleResponseModel> getNearByPlaces(@Url String url);

    //@GET
    //Call<DirectionResponseModel> getDirection(@Url String url);

    @GET("top-healines?country=in&category=buisness&apiKey="+API_KEY)
    Call<PlacesResponse>getTopHeadlines();
}
