package com.example.factory.presenter.user;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.common.factory.data.DataSource;
import com.example.factory.R;
import com.example.netKit.NetKit;
import com.example.netKit.db.User;
import com.example.netKit.model.UserModel;
import com.example.netKit.net.CattleNetWorker;
import com.example.netKit.net.NetInterface;
import com.example.netKit.piece.RspPiece;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * 用来处理用户的信息的更新
 */
public class UserHelper {



    /**
     * @param searchStr 网络用户查询
     * @param page 用户当前翻页
     * @param callback 资源加载观察者
     * @return 返回当前的加载器，方式用户频繁输入，导致加载很慢，提供一个对象用于取消
     */
    public static Call searchBack(String searchStr, int page, final DataSource.Callback<List<UserModel>> callback) {

        NetInterface connect = CattleNetWorker.getConnect();
        Call<RspPiece<List<UserModel>>> rspPieceCall = connect.userSearch(searchStr, page);
        rspPieceCall.enqueue(new Callback<RspPiece<List<UserModel>>>() {
            @Override
            public void onResponse(@NonNull Call<RspPiece<List<UserModel>>> call,
                                   @NonNull Response<RspPiece<List<UserModel>>> response) {
                RspPiece<List<UserModel>> rspPiece = response.body();
                if (rspPiece == null){
                    callback.onDataNotAvailable(R.string.data_network_error);
                    return;
                }
                if (rspPiece.isSuccess()){
                    if (rspPiece.getResult().size() !=0){
                        callback.onDataLoaded(rspPiece.getResult());
                    }else {
                        callback.onDataNotAvailable(R.string.data_null);
                    }
                }else {
                    Log.e(TAG, "onResponse: " + "sdkkaf");
                    NetKit.decodeRep(rspPiece, callback);
                }

            }

            @Override
            public void onFailure(@NonNull Call<RspPiece<List<UserModel>>> call, @NonNull Throwable t) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });

        return rspPieceCall;
    }


    public static void createRelation(String id, final DataSource.Callback<UserModel> callback) {

        NetInterface connect = CattleNetWorker.getConnect();

        Call<RspPiece<UserModel>> relation = connect.createRelation(id);

        relation.enqueue(new Callback<RspPiece<UserModel>>() {
            @Override
            public void onResponse(Call<RspPiece<UserModel>> call, Response<RspPiece<UserModel>> response) {
                // todo 调用本地数据库进行储存，同时通知联系人页面，进行页面的更新
                // 暂时先不处理
                RspPiece<UserModel> rspPiece = response.body();

                if (rspPiece.isSuccess())
                    callback.onDataLoaded(rspPiece.getResult());
                else {
                    NetKit.decodeRep(rspPiece, callback);
                }
            }

            @Override
            public void onFailure(Call<RspPiece<UserModel>> call, Throwable t) {
                Log.e("createRelation", "onFailure: " + "sadfkjasl;k" );
                callback.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }


    public static User getInfo(String userId) {
        User user = getFromNet(userId);
        if (user == null){
            // 从数据库中取出
        }
        return user;
    }

    private static User getFromNet(final String userId) {

        User user = null;

        try {
            Call<RspPiece<UserModel>> userInfo = CattleNetWorker.getConnect().getUserInfo(userId);
            Response<RspPiece<UserModel>> execute = userInfo.execute();
            RspPiece<UserModel> body = execute.body();
            if (body != null){
                UserModel userModel = body.getResult();
                user = userModel.Build();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return user;
    }
}
