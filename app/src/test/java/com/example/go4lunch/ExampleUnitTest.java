package com.example.go4lunch;

import com.example.go4lunch.model.SavedPlaceModel;
import com.example.go4lunch.model.Users;
import com.example.go4lunch.response.GoogleResponseModel;
import com.example.go4lunch.response.SavedPlaceResponseModel;
import com.example.go4lunch.webServices.RetrofitApi;
import com.example.go4lunch.webServices.RetrofitClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Response;

import static com.example.go4lunch.webServices.RetrofitClient.BASE_URL_Google;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public MockWebServer mockWebServer;
    public RetrofitApi api;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        api = RetrofitClient.getRetrofitClient(mockWebServer.url(BASE_URL_Google));

    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }



    @Test
    public void getStars() throws IOException {
        SavedPlaceModel savedPlaceModel= new SavedPlaceModel();
       String list;
       list ="4.3";

        Response<SavedPlaceResponseModel> responseModelResponse = api.getRestaurantIdRetrofit("https://maps.googleapis.com/maps/api/place/details/json?place_id=" +
                "ChIJa51FEUGuEmsRIXRtDjLFQXM&key=AIzaSyDIC9wuMhHNNjFIr6UZfb64h1Rmauaz7hw").execute();

        assertEquals(list,String.valueOf(responseModelResponse.body().getSavedPlaceModel().getRating()));
    }

    @Test
    public void testRestaurant() throws IOException {

        String jsonTest ="Amora Hotel Jamison Sydney";

        Response<GoogleResponseModel> response1= api.getNearByPlaces("https://maps.googleapis.com/maps/api/place/nearbysearch/" +
                "json?location=-33.8670522,151.1957362&radius=5000&types=restaurant&sensor=false&maxwidth=400&key=AIzaSyDIC9wuMhHNNjFIr6UZfb64h1Rmauaz7hw").execute();

        assert response1.body() != null;
        assertEquals(jsonTest,response1.body().getGooglePlaceModelList().get(0).getName());
    }

    @Test
    public void getDetails() throws IOException {

        String jsonTestDetails= "Amora Hotel Jamison Sydney";
        Response<SavedPlaceResponseModel> responseModelResponse = api.getRestaurantIdRetrofit("https://maps.googleapis.com/maps/api/place/details/json?place_id=" +
                "ChIJa51FEUGuEmsRIXRtDjLFQXM&key=AIzaSyDIC9wuMhHNNjFIr6UZfb64h1Rmauaz7hw").execute();
        assertEquals(jsonTestDetails,responseModelResponse.body().getSavedPlaceModel().getName());
    }






}