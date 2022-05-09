package com.example.go4lunch.webServices;

import androidx.annotation.VisibleForTesting;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.go4lunch.constants.AppConstant.BASE_URL;

public class RetrofitClient {

    public static final String BASE_URL_Google = "https://maps.googleapis.com";

    //Base url est la mauvaise url pour le test


    public static RetrofitApi getInstance() {
        return getRetrofitClient(HttpUrl.get(BASE_URL_Google));
    }

    @VisibleForTesting
    public static RetrofitApi getRetrofitClient(HttpUrl baseURL) {
         return new Retrofit.Builder()
                    .baseUrl(BASE_URL_Google)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RetrofitApi.class);


    }
}
