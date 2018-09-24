package com.desenvolvimento.at.soquestionsandanswers.activity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static Retrofit retrofitInstance;

    private static final String BASE_URL = "https://api.stackexchange.com/2.2/";


    public static Retrofit getInstance() {
        if (retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL).build();
        }
        return retrofitInstance;
    }

    /*public IStackOverflowAPI getStackOverflowRepo(){
        return retrofitInstance.create(IStackOverflowAPI.class);
    }*/

}
