package com.example.factory.net;

import com.example.factory.model.TestModel;

import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.http.GET;

public class APi {

    public interface IServiceApi {
        @GET("/test")
        Call<TestModel>  getCall();
    }

}
