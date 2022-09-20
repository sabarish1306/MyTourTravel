package com.example.mytourtravel;

/**
 * Created by Appstry on 1/27/2019.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Register_Response {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("id")
    @Expose
    private String id;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}