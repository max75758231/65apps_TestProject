package com.example.a65apps_testproject.retrofit;

import com.example.a65apps_testproject.data.datamodel.ResponseData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("/images/testTask.json")
    Call<ResponseData> getJSON();
}
