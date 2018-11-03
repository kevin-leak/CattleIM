package com.example.factory.presenter;

import android.annotation.SuppressLint;
import android.net.Network;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.util.Log;

import com.example.common.factory.data.DataSource;
import com.example.common.tools.StringsTools;
import com.example.netKit.db.IMFile;
import com.example.netKit.db.User;
import com.example.netKit.model.FileModel;
import com.example.netKit.net.NetInterface;
import com.example.netKit.net.NetWorker;
import com.example.netKit.piece.FilePiece;
import com.example.netKit.piece.RspPiece;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileHelper {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void saveBackgoundFile(String path, DataSource.Callback<IMFile> callback) {

        String name = new File(path).getName();
        String imageString = StringsTools.ImageToStrings(path);
        NetInterface connect = NetWorker.getConnect();
        Call<RspPiece<FileModel>> task = connect.saveFile(new FilePiece(name, imageString));
        task.enqueue(new FileCallBack(callback));
    }

    private static class FileCallBack implements Callback<RspPiece<FileModel>> {
        private DataSource.Callback<IMFile> callback;

        public FileCallBack(DataSource.Callback<IMFile> callback) {

            this.callback = callback;
        }

        @Override
        public void onResponse(Call<RspPiece<FileModel>> call, Response<RspPiece<FileModel>> response) {
            // todo 处理失败逻辑
        }

        @Override
        public void onFailure(Call<RspPiece<FileModel>> call, Throwable t) {

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String fetchBackgroundFile(String path) {

        String name = new File(path).getName();
        String imageString = StringsTools.ImageToStrings(path);
        NetInterface connect = NetWorker.getConnect();
        Call<RspPiece<FileModel>> task = connect.saveFile(new FilePiece(name, imageString));
        String urlString = null;
        try {
            Response<RspPiece<FileModel>> execute = task.execute();
            if (execute.body() == null){
                return null;
            }
            urlString = execute.body().getResult().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urlString;
    }


}
