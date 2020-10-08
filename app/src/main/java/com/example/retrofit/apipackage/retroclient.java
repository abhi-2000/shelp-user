package com.example.retrofit.apipackage;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retroclient {
    private static final String baseurl = "https://shelp-webapp.herokuapp.com/";

    private static retroclient m_instance;
    private Retrofit retrofit;

    private retroclient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized retroclient getInstance() {
        if (m_instance == null)
            m_instance = new retroclient();

        return m_instance;
    }

    public api getapi() {
        return retrofit.create(api.class);

    }
}
