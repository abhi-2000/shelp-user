package com.example.retrofit.apipackage;
import com.example.retrofit.Response.Bookmark;
import com.example.retrofit.Reotp;
import com.example.retrofit.ResetPass;
import com.example.retrofit.Response.OtpResponse;
import com.example.retrofit.Response.ResendResponse;
import com.example.retrofit.Response.SignupResponse;
import com.example.retrofit.Verify;
import com.example.retrofit.signup;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Streaming;


public interface api {

    @POST("signup/otp")
    Call<OtpResponse> verifymail(@Body Verify otp_ver);

    @POST("signup/otp-resend")
    Call<ResendResponse> resend(@Body Reotp re);

    @PUT("signup")
    Call<SignupResponse> createuser(@Body signup create);

    @POST("signup/resetpassword")
    Call<ResponseBody> resetpass(@Body ResetPass reset);

    @GET("home/{cat}")
    Call<ResponseBody> home(@Path("cat")String category);

    @GET("course/coursename/{courseID}")
    Call<ResponseBody> detail(@Path("courseID")String id , @Header("Authorization") String header);

    @POST("signup/resetOtp")
    Call<ResponseBody> sent(@Body course c);
    @POST("signup/checkOtp")
    Call<ResponseBody> check(@Body Checkotp check);

    @Streaming
    @GET("home/download/{userId}")
    Call<ResponseBody> download(@Path("userId") String userid,@Header("Authorization") String header);

    @POST("home/category/courseTitle")
    Call<ResponseBody> bookmark(@Body Bookmark book,@Header("Authorization") String header);

    @POST("unbookmark")
    Call<ResponseBody> unbookmark(@Body Bookmark book);
    @GET("users/userName/{userId}")
    Call<ResponseBody> bookcourse(@Path("userId")String id , @Header("Authorization") String header);
    @GET("users/userName/{userId}")
    Call<ResponseBody> bookcourse1(@Path("userId")String id , @Header("Authorization") String header);

}
