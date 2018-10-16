package com.example.factory.net;

import android.util.Log;

import com.example.factory.model.TestModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Test {

    public void getTest() throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                //设置数据解析器
                .addConverterFactory(GsonConverterFactory.create())
                //设置网络请求的Url地址
                .baseUrl("http://192.168.136.106:8000/")
                .build();
        // 创建网络请求接口的实例
        APi.IServiceApi mApi =  retrofit.create(APi.IServiceApi.class);

        Call<TestModel> call = mApi.getCall();
        call.enqueue(new Callback<TestModel>() {
            @Override
            public void onResponse(Call<TestModel> call, Response<TestModel> response) {
                System.out.print(response.headers());
                System.out.print(response.body());
                System.out.print(response.message());
                System.out.print(response.code());
                System.out.print(response.raw());
                System.out.print(response.body().getDetail());
            }

            @Override
            public void onFailure(Call<TestModel> call, Throwable t) {

            }
        });

    }



    public static void main(String args[]) throws IOException {
        new Test().getTest();
    }

}
