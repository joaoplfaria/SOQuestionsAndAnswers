package com.desenvolvimento.at.soquestionsandanswers.interfaces;

import com.desenvolvimento.at.soquestionsandanswers.domain.StackOverflowRepo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IStackOverflowAPI {


    @GET("questions?order=desc&sort=activity&tagged={tag}&site=stackoverflow")
    Call<ResponseBody> getQuestionsAndAnswers(@Path("tag") String tag);

    @GET("questions?order=desc&sort=activity&tagged={tag}&site=stackoverflow")
    Call<List<StackOverflowRepo>> getNoTreatment(@Path("tag") String tag);

}
