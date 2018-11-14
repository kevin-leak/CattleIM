package com.example.factory.presenter.account;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.common.factory.data.DataSource;
import com.example.factory.R;
import com.example.netKit.NetKit;
import com.example.netKit.db.User;
import com.example.netKit.net.NetInterface;
import com.example.netKit.net.CattleNetWorker;
import com.example.netKit.net.push.PushService;
import com.example.netKit.persistence.Account;
import com.example.netKit.piece.RspPiece;
import com.example.netKit.model.UserModel;
import com.example.netKit.piece.account.LoginPiece;
import com.example.netKit.piece.account.RegisterPiece;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 为了简化present里面的操作，将netkit里面的数据抽象出来给present调用，同时我们需要个db来对数据进行操作
 * 主要第对网络断传过来的信息进行分析，将结果反=反馈给present能够操作的信息
 * 主要是将连接netkit的时候简化封装，使得操作解耦，跟便捷
 *
 * @author KevinLeak
 */
public class AccountHelper {
    private static String TAG = "AccountHelper";
    //TODO 后期加入对储存的优化，进行对本地数据的缓存

    /**
     * @param piece    网络请求参数封装成model
     * @param callback 数据返回
     */
    public static void register(RegisterPiece piece, DataSource.Callback<User> callback) {
        NetInterface connect = CattleNetWorker.getConnect();
        Call<RspPiece<UserModel>> task = connect.register(piece);
        task.enqueue(new AccountCallback(callback));
    }

    /**
     * @param piece    网络请求参数封装成model
     * @param callback 数据返回
     */
    public static void login(LoginPiece piece, DataSource.Callback<User> callback) {
        NetInterface connect = CattleNetWorker.getConnect();
        Call<RspPiece<UserModel>> task = connect.login(piece);
        task.enqueue(new AccountCallback(callback));
    }


    /**
     * 继承retrofit类的回调接口，统一实现对登入注册的处理并将信息通过全局的DataSource进行一个监听回调
     */
    private static class AccountCallback implements Callback<RspPiece<UserModel>> {

        private DataSource.Callback<User> callback;

        public AccountCallback(DataSource.Callback<User> callback) {

            this.callback = callback;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onResponse(Call<RspPiece<UserModel>> call, Response<RspPiece<UserModel>> response) {
            /*
             * 1. 进行数据的本地化处理
             * 2. 进行一个全局的通知回调
             * 3. 可将后端返回的数据与本地数据库的数据相同
             * 4. 开启推送功能
             * */



            RspPiece<UserModel> rspPiece = response.body();
            Log.e(TAG, "onResponse" );
            if (rspPiece == null) {
                callback.onDataNotAvailable(R.string.data_network_error);
            }else if (rspPiece.isSuccess()) {
                UserModel accountPiece = rspPiece.getResult();
                User user = accountPiece.getUser();
//                DbHelper.save(User.class, user);
                user.save();
                // 对数据进行本地化处理
                callback.onDataLoaded(user);
                Account.login(accountPiece);

                // todo c这里需要对推送的id 进行一个绑定

                PushService.startPush();
            } else {
                NetKit.decodeRep(rspPiece, callback);
            }

        }

        @Override
        public void onFailure(Call<RspPiece<UserModel>> call, Throwable t) {
            Log.e(TAG, "onFailure: kkkk");
            // 网络请求失败
            if (callback != null)
                callback.onDataNotAvailable(R.string.data_network_error);
        }
    }

}
