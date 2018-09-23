package com.desenvolvimento.at.soquestionsandanswers.activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static Retrofit retrofitInstance;

    private static final String BASE_URL = "https://api.stackexchange.com/2.2/";


    public static Retrofit getInstance(){
        if (retrofitInstance == null){
//            Gson gson = new GsonBuilder()
//                    .setLenient()
//                    .create();
            retrofitInstance = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL).build();
        }
        return retrofitInstance;
    }

}
