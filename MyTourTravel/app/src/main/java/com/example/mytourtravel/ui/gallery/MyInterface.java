package com.example.mytourtravel.ui.gallery;

import com.example.mytourtravel.ApiClient.Commons;
import com.example.mytourtravel.Search_result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyInterface {

    String JSONURL = "http://vehicleiot.in/android/tour/";
    Call<Search_result> SearchFu(@Field("input") String input);

    @GET("imagerequest.php")
    Call<String> getString();




}
