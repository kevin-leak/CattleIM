package com.example.netKit.net;

import com.example.netKit.model.FileModel;
import com.example.netKit.model.TimeLineModel;
import com.example.netKit.model.UserModel;
import com.example.netKit.piece.FilePiece;
import com.example.netKit.piece.RspPiece;
import com.example.netKit.model.AccountModel;
import com.example.netKit.piece.account.AccountInfoPiece;
import com.example.netKit.piece.account.LoginPiece;
import com.example.netKit.piece.account.RegisterPiece;
import com.example.netKit.piece.todo.TimeLinePiece;

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
 *
 * model是用来返回数据，用于gson 转化
 * piece 请求碎片，是用来封装请求信息。
 *
 */
public interface NetInterface {

    @GET("get_test/")
    Call<ResponseBody> getCall();

    @POST("login/")
    Call<RspPiece<AccountModel>> login(@Body LoginPiece piece);

    @POST("save_file/")
    Call<RspPiece<FileModel>> saveFile(@Body FilePiece piece);

    // 登入注册后，看用户信息是否完成，用于请求链接
    @POST("complete_account/")
    Call<RspPiece<AccountModel>> completeAccount(@Body AccountInfoPiece piece);


    @POST("register/")
    Call<RspPiece<AccountModel>> register(@Body RegisterPiece piece); // 此处，后端需要request里面的body接受数据

    @GET("out/")
    Call<RspPiece<String>> logout(); // 此处，后端需要request里面的body接受数据

    // 用户搜索的接口
    @GET("user_search/{name}/{page}")
    Call<RspPiece<List<UserModel>>> userSearch(@Path("name") String name, @Path("page") int page);

    // 添加好友接口
    @GET("add_friend/{uid}/")
    Call<RspPiece<UserModel>> createRelation(@Path("uid") String uid);

    // 添加好友接口
    @GET("search/{uid}/")
    Call<RspPiece<UserModel>> getUserInfo(@Path("uid") String uid);

    @GET("user_contacts/")
    Call<RspPiece<List<UserModel>>> userContacts();

    // 当客户端接收到pushid 的时候，我们回送一条消息，进行一个绑定
    @GET("push_id/{pushId}/")
    Call<RspPiece<AccountModel>> accountBind(@Path("pushId") String pushId);


    // 用来存储time line
    @POST("save_time_line/")
    Call<RspPiece<TimeLineModel>> saveTimeLine(@Body TimeLinePiece piece);
}
