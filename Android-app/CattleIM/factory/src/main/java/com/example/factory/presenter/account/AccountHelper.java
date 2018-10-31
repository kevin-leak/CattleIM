package com.example.factory.presenter.account;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.common.factory.data.DataSource;
import com.example.factory.R;
import com.example.netKit.db.User;
import com.example.netKit.net.NetInterface;
import com.example.netKit.net.NetWorker;
import com.example.netKit.piece.RspPiece;
import com.example.netKit.piece.account.AccountPiece;
import com.example.netKit.piece.account.LoginPiece;
import com.example.netKit.piece.account.RegisterPiece;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 为了简化present里面的操作，将netkit里面的数据抽象出来给present调用，同时我们需要个db来对数据进行操作
 * 主要第对网络断传过来的信息进行分析，将其打包成present能够操作的信息
 * 主要是将连接netkit的时候简化封装，使得操作解耦，跟便捷
 * @author KevinLeak
 */
public class AccountHelper {
    private static String   TAG = "AccountHelper";
    //TODO 后期加入对储存的优化，进行对本地数据的缓存

    /**
     * @param piece 网络请求参数封装成model
     * @param callback  数据返回
     */
    public static void register(RegisterPiece piece, DataSource.Callback<User> callback){
        NetInterface connect = NetWorker.getConnect();
        Call<RspPiece<AccountPiece>> task = connect.register(piece);
        task.enqueue(new AccountCallback(callback));
    }

    /**
     * @param piece 网络请求参数封装成model
     * @param callback 数据返回
     */
    public static void login(LoginPiece piece, DataSource.Callback<User> callback){
        NetInterface connect = NetWorker.getConnect();
        Call<RspPiece<AccountPiece>> task = connect.login(piece);
        task.enqueue(new AccountCallback(callback));
    }


    /**
     * 继承retrofit类的回调接口，统一实现对登入注册的处理并将信息通过全局的DataSource进行一个监听回调
     */
    private static class AccountCallback implements Callback<RspPiece<AccountPiece>> {

        private DataSource.Callback<User> callback;

        public AccountCallback(DataSource.Callback<User> callback) {

            this.callback = callback;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onResponse(Call<RspPiece<AccountPiece>> call, Response<RspPiece<AccountPiece>> response) {
            /*todo, 注意哟啊区分登入与注册
             * 1. 进行数据的本地化处理
             * 2. 进行一个全局的通知回调
             * 3. 可将后端返回的数据与本地数据库的数据相同
             * */

//            if (response.body() != null && response.body().success()){
//                RspPiece<AccountPiece> repPiece = response.body();
//                AccountPiece accountPiece = repPiece.getResult();
//                User user = new User();
//                user.get
//
//                }

            if (Objects.nonNull(response.body())) {
                Log.e(TAG, "onResponse: " + response.body().success());
            }

            // 通知全局的数据中心
            callback.onDataLoaded(new User());
        }

        @Override
        public void onFailure(Call<RspPiece<AccountPiece>> call, Throwable t) {
            // 网络请求失败
            if (callback != null)
                callback.onDataNotAvailable(R.string.data_network_error);
        }
    }

}
