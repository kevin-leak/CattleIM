package com.example.netKit.net;

import com.example.netKit.model.FileModel;
import com.example.netKit.piece.FilePiece;
import com.example.netKit.piece.RspPiece;
import com.example.netKit.piece.account.AccountPiece;
import com.example.netKit.piece.account.LoginPiece;
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

    @POST("login/")
    Call<RspPiece<AccountPiece>> login(@Body LoginPiece piece);

    @POST("save_file/")
    Call<RspPiece<FileModel>> saveFile(@Body FilePiece piece);



    @POST("register/")
    Call<RspPiece<AccountPiece>> register(@Body RegisterPiece piece); // 此处，后端需要request里面的body接受数据

}
