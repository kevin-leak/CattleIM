package com.example.factory.net;

import com.example.factory.model.AccountModel;
import com.example.factory.model.TestModel;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface NetInterface {

    @GET("get_test/")
    Call<ResponseBody> getCall();

    @FormUrlEncoded
    @POST("login/")
    Call<AccountModel> login(@Field("name") String name, @Field("password") String password);

}
