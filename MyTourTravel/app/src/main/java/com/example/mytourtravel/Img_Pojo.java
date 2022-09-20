package com.example.mytourtravel;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Img_Pojo {


    @SerializedName("image_name")
    private String Title;

    @SerializedName("image")
    private String Image;


    @SerializedName("response")
    private String Response;

    public String getResponse() {
        return Response;
    }
}
