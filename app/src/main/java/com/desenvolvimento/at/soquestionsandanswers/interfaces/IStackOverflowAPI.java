package com.desenvolvimento.at.soquestionsandanswers.interfaces;

import com.desenvolvimento.at.soquestionsandanswers.domain.StackOverflowRepo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IStackOverflowAPI {


    @GET("questions?order=desc&sort=activity&site=stackoverflow")
    Call<List<StackOverflowRepo>> getQuestionsAndAnswers(@Query("tagged") String tag);

    @GET("questions?order=desc&sort=activity&site=stackoverflow")
    Call<ResponseBody> getNoTreatment(@Query("tagged") String tag);

}
