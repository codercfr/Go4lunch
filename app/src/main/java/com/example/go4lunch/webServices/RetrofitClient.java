package com.example.go4lunch.webServices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.go4lunch.constants.AppConstant.BASE_URL;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    public static final String BASE_URL_Google = "https://maps.googleapis.com";

    //Base url est la mauvaise url pour le test
    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_Google)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
