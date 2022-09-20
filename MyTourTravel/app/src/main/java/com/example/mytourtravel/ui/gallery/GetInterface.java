package com.example.mytourtravel.ui.gallery;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface GetInterface {

    String JSONGETURL = "http://vehicleiot.in/android/tour/";


    @GET("imagerequest.php")
    Call<String> getString();
}
