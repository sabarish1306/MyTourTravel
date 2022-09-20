package com.example.mytourtravel.ApiClient;

import com.example.mytourtravel.Img_Pojo;
import com.example.mytourtravel.Login_Response;
import com.example.mytourtravel.Register_Response;
import com.example.mytourtravel.Search_Responce;
import com.example.mytourtravel.Search_result;
import com.example.mytourtravel.imgserchresponce;
import com.example.mytourtravel.ui.home.FarmerResponse;


import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by USER on 14-Sep-17.
 */


public interface ApiInterface {


    @FormUrlEncoded
    @POST(Commons.Register)
    Call<Register_Response> RegisterActivation(@Field("input") String input);



   @FormUrlEncoded
    @POST(Commons.searchrslt)
    Call<Search_Responce> SearchResponce(@Field("input") String input);



    @Multipart
    @POST(Commons.upload)
    Call<FarmerResponse> UpdateProfile(@Part("input") okhttp3.RequestBody data,
                                       @Part MultipartBody.Part file);
    @Multipart
    @POST(Commons.imgsearch)
    Call<imgserchresponce> imgsrch(@Part("input") okhttp3.RequestBody data,
                                         @Part MultipartBody.Part file);
    @FormUrlEncoded
    @POST(Commons.login)
    Call<Login_Response> LoginActivation(@Field("input") String input);

    @FormUrlEncoded
    @POST(Commons.searchrslt)
    Call<Search_result> SearchFu(@Field("input") String input);


/*
@Multipart
    @POST(Commons.uploadimg)
    Call<Response> uploadImage(@Part MultipartBody.Part image);






    @FormUrlEncoded
    @POST(Commons.card_accept_key)
    Call<Account_No_Accept_Response> card_accept_key(@Field("input") String input);
     @FormUrlEncoded
    @POST(Commons.card_transfer_amount)
    Call<RfSuccessResponse> RFresponse(@Field("input") String input);
   @FormUrlEncoded
    @POST(Commons.OrderProduct)
    Call<OrdernoResponse> OrderProduct(@Field("input") String input);

    @Multipart
    @POST(Commons.Agntupdate)
    Call<UpdateResponse> UpdateProfile(@Part("input") okhttp3.RequestBody data,
                                       @Part MultipartBody.Part file);

   @Multipart
    @POST(Commons.KEY_MEETING_START_STOP)
    Call<DefaultResponse> meetingStopServiceSuccess(@Part("meetingStartStop") RequestBody data, @Part("meetingId") RequestBody meetingId,
                                                    @Part("meetingTimingId") RequestBody meetingTimingId,
                                                    @Part("endDateTime") RequestBody endDateTime,
                                                    @Part("meetingStatus") RequestBody meetingStatus, @Part("meetingResult") RequestBody meetingResult,
                                                    @Part("deliveryDate") RequestBody deliveryDate,
                                                    @Part MultipartBody.Part file,
                                                    @Part("productCount") RequestBody productCount, @Part("sendOrSave") RequestBody sendOrSave);
*/}
