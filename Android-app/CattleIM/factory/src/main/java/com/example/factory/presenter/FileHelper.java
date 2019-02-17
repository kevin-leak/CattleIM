package com.example.factory.presenter;

import com.example.common.factory.data.DataSource;
import com.example.common.utils.StringsUtils;
import com.example.netKit.db.IMFile;
import com.example.netKit.model.FileModel;
import com.example.netKit.net.NetInterface;
import com.example.netKit.net.CattleNetWorker;
import com.example.netKit.piece.FilePiece;
import com.example.netKit.piece.RspPiece;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileHelper {

    public static void saveBackgroundFile(String path, DataSource.Callback<IMFile> callback) {

        String name = new File(path).getName();
        String imageString = StringsUtils.ImageToStrings(path);
        NetInterface connect = CattleNetWorker.getConnect();
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

    public static String fetchBackgroundFile(String path) {

        String name = new File(path).getName();
        String imageString = StringsUtils.ImageToStrings(path);
        NetInterface connect = CattleNetWorker.getConnect();
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
