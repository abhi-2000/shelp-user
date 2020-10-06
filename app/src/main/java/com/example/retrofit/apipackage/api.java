package com.example.retrofit.apipackage;

import com.example.retrofit.Reotp;
import com.example.retrofit.ResetPass;
import com.example.retrofit.Response.OtpResponse;
import com.example.retrofit.Response.ResendResponse;
import com.example.retrofit.Response.ResetpassotpResponse;
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


public interface api {

    @POST("signup/otp")
    Call<OtpResponse> verifymail(@Body Verify otp_ver);

    @POST("signup/otp-resend")
    Call<ResendResponse> resend(@Body Reotp re);

    @PUT("signup")
    Call<SignupResponse> createuser(@Body signup create);

    @POST("reset-password")
    Call<ResponseBody> resetpass(@Body ResetPass reset);


    @GET("home/{cat}")
    Call<ResponseBody> home(@Path("cat")String category);

    @GET("/course/coursename/{courseID}")
    Call<ResponseBody> detail(@Header("token") String token, @Path("courseID")String id);

    @POST("signup/resetOtp")
    Call<ResponseBody> sent(@Body course c);
    @POST("signup/checkOtp")
    Call<ResponseBody> check(@Body String otp,String token);
}
