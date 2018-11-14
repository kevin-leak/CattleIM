package com.example.netKit.net;

import com.example.netKit.model.FileModel;
import com.example.netKit.piece.FilePiece;
import com.example.netKit.piece.RspPiece;
import com.example.netKit.model.UserModel;
import com.example.netKit.piece.account.LoginPiece;
import com.example.netKit.piece.account.RegisterPiece;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author KevinLeak
 * 里面记录所有的网络接口
 */
public interface NetInterface {

    @GET("get_test/")
    Call<ResponseBody> getCall();

    @POST("login/")
    Call<RspPiece<UserModel>> login(@Body LoginPiece piece);

    @POST("save_file/")
    Call<RspPiece<FileModel>> saveFile(@Body FilePiece piece);



    @POST("register/")
    Call<RspPiece<UserModel>> register(@Body RegisterPiece piece); // 此处，后端需要request里面的body接受数据

    @GET("out/")
    Call<ResponseBody> logout(); // 此处，后端需要request里面的body接受数据

    // 用户搜索的接口
    @GET("user_search/{name}/{page}")
    Call<RspPiece<List<UserModel>>> userSearch(@Path("name") String name, @Path("page") int page);

    // 添加好友接口
    @GET("add_friend/{uid}/")
    Call<RspPiece<UserModel>> createRelation(@Path("uid") String uid);
}
