package com.finamads.finamads.utilities;




import com.finamads.finamads.model.AccpectCampaginResponse;
import com.finamads.finamads.model.AccpectCampaignInput;
import com.finamads.finamads.model.CheckPhoneResponse;
import com.finamads.finamads.model.FcmToken;
import com.finamads.finamads.model.FcmTokenResponse;
import com.finamads.finamads.model.GetActiveCampaigns;
import com.finamads.finamads.model.GetCurrentCampaign;
import com.finamads.finamads.model.GetDistricts;
import com.finamads.finamads.model.GetHobli;
import com.finamads.finamads.model.GetStates;
import com.finamads.finamads.model.GetTaluks;
import com.finamads.finamads.model.GetUser;
import com.finamads.finamads.model.GetVehicleType;
import com.finamads.finamads.model.GetVehiclesResponse;
import com.finamads.finamads.model.LoginInput;
import com.finamads.finamads.model.LoginResponse;
import com.finamads.finamads.model.OtpInput;
import com.finamads.finamads.model.OtpResponse;
import com.finamads.finamads.model.RegisterInput;
import com.finamads.finamads.model.RegisterResponse;
import com.finamads.finamads.model.RejectCampaignResponse;
import com.finamads.finamads.model.VerifyOtpInput;
import com.finamads.finamads.model.VerifyOtpResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;


/**
 * Created by pradeep on 05-12-2017.
 */

public interface DrService {

    //check User exists
    @FormUrlEncoded
    @POST("api/user-exists")
    Call<CheckPhoneResponse> loginUser(@Field("username") String empid);

    //user registration
    @Headers("Accept: application/json")
    @POST("api/register")
    Call<RegisterResponse> submitUser(@Body RegisterInput registerInput);


    //user login
    @Headers("Accept: application/json")
    @POST("api/login")
    Call<LoginResponse> submitLogin(@Body LoginInput registerInput);


    //send otp
    @Headers("Accept: application/json")
    @POST("api/send-otp")
    Call<OtpResponse> getOtp(@Body OtpInput otpInput);


    //verify otp
    @Headers("Accept: application/json")
    @POST("api/otp-login")
    Call<LoginResponse> submitOtp(@Body VerifyOtpInput verifyOtpInput);



    //user deatils
    @Headers("Accept: application/json")
    @GET("/api/user")
    Call<GetUser> doGetUserDetails(@Header("Authorization") String token);

    @Headers("Accept: application/json")
    @GET("/api/vehicle-types?filter[active]=1")
    Call<ArrayList<GetVehicleType>> getVehicleType(@Header("Authorization") String token);

    @Headers("Accept: application/json")
    @GET("/api/places/states")
    Call<ArrayList<GetStates>> getStates();

    @Headers("Accept: application/json")
    @GET
    Call<ArrayList<GetDistricts>> getDistricts(@Url String url);

    @Headers("Accept: application/json")
    @GET
    Call<ArrayList<GetTaluks>> getTaluks(@Url String url);

    @Headers("Accept: application/json")
    @GET
    Call<ArrayList<GetHobli>> getHobli(@Url String url);

    @Headers("Accept: application/json")
    @POST("api/save-fcm-token")
    Call<FcmTokenResponse> sendFbaseToken(@Header("Authorization") String token, @Body FcmToken tokenInput);

    @Headers("Accept: application/json")
    @GET("api/my-ad-offers")
    Call<ArrayList<GetActiveCampaigns>> doActiveCampaigns(@Header("Authorization") String token);

    @Headers("Accept: application/json")
    @POST("api/accept-ad-offers")
    Call<AccpectCampaginResponse> accpectCampaign(@Header("Authorization") String token,@Body AccpectCampaignInput accpectCampaignInput);


    @Headers("Accept: application/json")
    @POST("api/reject-ad-offers")
    Call<RejectCampaignResponse> rejectCampaign(@Header("Authorization") String token, @Body AccpectCampaignInput accpectCampaignInput);

    @Headers("Accept: application/json")
    @GET("api/running-ads")
    Call<ArrayList<GetCurrentCampaign>> doCurrentCampaigns(@Header("Authorization") String token);

    @Headers("Accept: application/json")
    @GET("/api/vehicles?filter[active]=1")
    Call<ArrayList<GetVehiclesResponse>> getVehicle(@Header("Authorization") String token);


}