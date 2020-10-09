package com.example.retrofit.apipackage;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
        public static final String BASE_URL = "https://shelp-webapp.herokuapp.com/";
//        http://192.168.43.162:8080/";
        public static RetrofitClient mInstance;
        public Retrofit retrofit;

        public RetrofitClient() {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        public static synchronized RetrofitClient getInstance() {
            if (mInstance==null)
            {
                mInstance=new RetrofitClient();
            }
            return mInstance;
        }
        public ApiInterface getApi() {
            return retrofit.create(ApiInterface.class);
        }
    }

