package com.example.go4lunch.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.response.PlacesResponse;
import com.example.go4lunch.webServices.RetrofitApi;
import com.example.go4lunch.webServices.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesRepository {


    private static final String TAG = PlacesRepository.class.getSimpleName();

    private final RetrofitApi apiRequest;


    public PlacesRepository() {
        this.apiRequest = RetrofitClient.getRetrofitClient().create(RetrofitApi.class);
    }


    public LiveData<PlacesResponse> getDashBordNews(){
        final MutableLiveData<PlacesResponse> data = new MutableLiveData<>();
        apiRequest.getTopHeadlines()
                .enqueue(new Callback<PlacesResponse>() {
                    @Override
                    public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                        if(response.body()!=null)
                        {
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<PlacesResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
