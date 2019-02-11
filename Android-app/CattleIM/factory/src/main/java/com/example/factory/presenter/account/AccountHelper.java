package com.example.factory.presenter.account;

import android.support.annotation.NonNull;
import android.text.TextUtils;
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
import com.example.netKit.model.AccountModel;
import com.example.netKit.piece.account.AccountInfoPiece;
import com.example.netKit.piece.account.LoginPiece;
import com.example.netKit.piece.account.RegisterPiece;

import okhttp3.ResponseBody;
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
        Call<RspPiece<AccountModel>> task = connect.register(piece);
        task.enqueue(new AccountCallback(callback));
    }

    /**
     * @param piece    网络请求参数封装成model
     * @param callback 数据返回
     */
    public static void login(LoginPiece piece, DataSource.Callback<User> callback) {
        NetInterface connect = CattleNetWorker.getConnect();
        Call<RspPiece<AccountModel>> task = connect.login(piece);
        task.enqueue(new AccountCallback(callback));
    }

    public static void completeInfo(AccountInfoPiece infoPiece, DataSource.Callback<User> callback) {
        NetInterface connect = CattleNetWorker.getConnect();
        Call<RspPiece<AccountModel>> task = connect.completeAccount(infoPiece);
        task.enqueue(new AccountCallback(callback));
    }

    public static void loginOut(final DataSource.Callback<String> callback) {

        NetInterface connect = CattleNetWorker.getConnect();
        final Call<RspPiece<String>> logout = connect.logout();
        logout.enqueue(new Callback<RspPiece<String>>() {
            @Override
            public void onResponse(Call<RspPiece<String>> call, Response<RspPiece<String>> response) {
                RspPiece<String> body = response.body();

                if (body != null && body.isSuccess()){
                    Log.e(TAG, "onResponse: " + body.getResult());
                    callback.onDataLoaded(body.getResult());
                } else {
                    if (body != null) {
                        NetKit.decodeRep(body, callback);
                    }
                }
            }

            @Override
            public void onFailure(Call<RspPiece<String>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + "out----------" );
            }
        });

    }

    /**
     * 对设备Id进行绑定的操作
     *
     * @param callback Callback
     */
    public static void bindPush(final DataSource.Callback<User> callback) {
        // 检查是否为空
        String pushId = Account.getPushId();
        if (TextUtils.isEmpty(pushId))
            return;

        // 调用Retrofit对我们的网络请求接口做代理
        NetInterface service = CattleNetWorker.getConnect();
        Call<RspPiece<AccountModel>> call = service.accountBind(pushId);
        call.enqueue(new AccountCallback(callback));
    }


    /**
     * 继承retrofit类的回调接口，统一实现对登入注册的处理并将信息通过全局的DataSource进行一个监听回调
     */
    private static class AccountCallback implements Callback<RspPiece<AccountModel>> {

        private DataSource.Callback<User> callback;

        public AccountCallback(DataSource.Callback<User> callback) {

            this.callback = callback;
        }

        @Override
        public void onResponse(@NonNull Call<RspPiece<AccountModel>> call,
                               @NonNull Response<RspPiece<AccountModel>> response) {
            /*
             * 1. 进行数据的本地化处理
             * 2. 进行一个全局的通知回调
             * 3. 可将后端返回的数据与本地数据库的数据相同
             * 4. 开启推送功能
             * */

            RspPiece<AccountModel> rspPiece = response.body();
            if (rspPiece != null && rspPiece.isSuccess()) {
                AccountModel accountPiece = rspPiece.getResult();
                User user = accountPiece.getUser();
//                DbHelper.save(User.class, user);
                user.save();
                // 对数据进行本地化处理
                Account.login(accountPiece);

                // todo c这里需要对推送的id 进行一个绑定


                // 判断绑定状态，是否绑定设备
                if (accountPiece.isBind()) {
                    // 设置绑定状态为True
                    Account.setBind(true);
                    // 然后返回
                    if (callback != null)
                        callback.onDataLoaded(user);
                } else {
                    if (Account.isComplete())
                        // 进行绑定的唤起
                        bindPush(callback);
                }
            } else {
                if (rspPiece != null) {
                    NetKit.decodeRep(rspPiece, callback);
                }
            }

        }

        @Override
        public void onFailure(@NonNull Call<RspPiece<AccountModel>> call, @NonNull Throwable t) {
            Log.e(TAG, "onFailure: " + "login----------" );
            // 网络请求失败
            if (callback != null)
                callback.onDataNotAvailable(R.string.data_network_error);
        }
    }

}
