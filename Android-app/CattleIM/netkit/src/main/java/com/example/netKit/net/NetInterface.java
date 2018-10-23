package com.example.netKit.net;

import com.example.netKit.piece.account.AccountPiece;
import com.example.netKit.piece.account.RegisterPiece;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * @author KevinLeak
 * 里面记录所有的网络接口
 */
public interface NetInterface {

    @GET("get_test/")
    Call<ResponseBody> getCall();

    @FormUrlEncoded
    @POST("login/")
    Call<AccountPiece> login(@Field("phone") String name, @Field("password") String password);



    @POST("register/")
    Call<AccountPiece> register(@Body RegisterPiece model); // 此处，后端需要request里面的body接受数据

}
