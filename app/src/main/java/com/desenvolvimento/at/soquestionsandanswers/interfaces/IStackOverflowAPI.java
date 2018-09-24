package com.desenvolvimento.at.soquestionsandanswers.interfaces;

import com.desenvolvimento.at.soquestionsandanswers.activity.ListWrapper;
import com.desenvolvimento.at.soquestionsandanswers.domain.StackOverflowRepo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IStackOverflowAPI {


    @GET("search?order=desc&sort=activity&site=stackoverflow")
    Call<ListWrapper<StackOverflowRepo>> loadQuestions(@Query("tagged") String tagged, @Query("intitle") String intitle);

    @GET("search?order=desc&sort=activity&site=stackoverflow")
    Call<ResponseBody> getNoTreatment(@Query("tagged") String tagged, @Query("intitle") String intitle);

}
