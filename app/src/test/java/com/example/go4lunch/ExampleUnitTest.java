package com.example.go4lunch;

import com.example.go4lunch.model.Users;
import com.example.go4lunch.response.GoogleResponseModel;
import com.example.go4lunch.response.SavedPlaceResponseModel;
import com.example.go4lunch.webServices.RetrofitApi;
import com.example.go4lunch.webServices.RetrofitClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void getUser(){
        final Users task1 = new Users();
        final Users task2 = new Users();
        final Users task3 = new Users();

        task1.setUid("1");
        task1.setUid("2");
        task1.setUid("3");

        assertEquals("1",task1.getUid());
        assertEquals("2",task2.getUid());
        assertEquals("3",task3.getUid());
    }

    @Test
    public void getStars(){
        final Users task1 = new Users();
    }

    @Test
    public void testRestaurant() throws IOException {
        String json="{\n" +
                "         \"business_status\" : \"OPERATIONAL\",\n" +
                "         \"geometry\" : {\n" +
                "            \"location\" : {\n" +
                "               \"lat\" : -33.864458,\n" +
                "               \"lng\" : 151.2062952\n" +
                "            },\n" +
                "            \"viewport\" : {\n" +
                "               \"northeast\" : {\n" +
                "                  \"lat\" : -33.8630026197085,\n" +
                "                  \"lng\" : 151.2075776802915\n" +
                "               },\n" +
                "               \"southwest\" : {\n" +
                "                  \"lat\" : -33.8657005802915,\n" +
                "                  \"lng\" : 151.2048797197085\n" +
                "               }\n" +
                "            }\n" +
                "         },\n" +
                "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/v1/png_71/lodging-71.png\",\n" +
                "         \"icon_background_color\" : \"#909CE1\",\n" +
                "         \"icon_mask_base_uri\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/v2/hotel_pinlet\",\n" +
                "         \"name\" : \"Amora Hotel Jamison Sydney\",\n" +
                "         \"photos\" : [\n" +
                "            {\n" +
                "               \"height\" : 608,\n" +
                "               \"html_attributions\" : [\n" +
                "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/112581335421444049471\\\"\\u003eAmora Hotel\\u003c/a\\u003e\"\n" +
                "               ],\n" +
                "               \"photo_reference\" : \"Aap_uEAlHebHvFrBQBxOdemuu18kYytvMr7a0Oo5r1PYvwotg84K9h7BpivhV_lTqtp-yhrAPSyn4kupu41TFq77hYux1KXnhe42cbE44GLufLA-JUqG1tCvpq6LmdQfmyUUtVWG1NZRNPDSbrmR94MI3CKPj2U6hrfIo_ulhuDKYrbEuNcH\",\n" +
                "               \"width\" : 1080\n" +
                "            }\n" +
                "         ],\n" +
                "         \"place_id\" : \"ChIJa51FEUGuEmsRIXRtDjLFQXM\",\n" +
                "         \"plus_code\" : {\n" +
                "            \"compound_code\" : \"46P4+6G Sydney NSW, Australia\",\n" +
                "            \"global_code\" : \"4RRH46P4+6G\"\n" +
                "         },\n" +
                "         \"price_level\" : 3,\n" +
                "         \"rating\" : 4.3,\n" +
                "         \"reference\" : \"ChIJa51FEUGuEmsRIXRtDjLFQXM\",\n" +
                "         \"scope\" : \"GOOGLE\",\n" +
                "         \"types\" : [\n" +
                "            \"spa\",\n" +
                "            \"lodging\",\n" +
                "            \"bar\",\n" +
                "            \"restaurant\",\n" +
                "            \"food\",\n" +
                "            \"point_of_interest\",\n" +
                "            \"establishment\"\n" +
                "         ],\n" +
                "         \"user_ratings_total\" : 1904,\n" +
                "         \"vicinity\" : \"11 Jamison Street, Sydney\"\n" +
                "      }";


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
    // récupérer le restaurant.

    //récupérer la liste des restauarants

    // tester d'envoyer place details





}