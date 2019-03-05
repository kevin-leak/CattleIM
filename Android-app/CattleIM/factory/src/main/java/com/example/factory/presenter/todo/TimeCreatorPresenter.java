package com.example.factory.presenter.todo;

import android.support.annotation.NonNull;

import com.example.common.factory.data.DataSource;
import com.example.factory.R;
import com.example.factory.contract.todo.TimeCreatorContract;
import com.example.factory.presenter.BasePresenter;
import com.example.netKit.db.TimeLine;
import com.example.netKit.model.TimeLineModel;
import com.example.netKit.net.CattleNetWorker;
import com.example.netKit.net.NetInterface;
import com.example.netKit.piece.RspPiece;
import com.example.netKit.piece.todo.TimeLinePiece;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeCreatorPresenter extends BasePresenter<TimeCreatorContract.View>
        implements TimeCreatorContract.Presenter, DataSource.Callback<TimeLineModel> {


    public TimeCreatorPresenter(TimeCreatorContract.View view) {
        super(view);
    }

    @Override
    public void onDataLoaded(TimeLineModel timeLineModel) {

    }

    @Override
    public void onDataNotAvailable(int strRes) {

    }


    @Override
    public void add(String content, Date start, Date end, int type) {
        TimeLinePiece timeLinePiece = new TimeLinePiece(content, start, end, type);
        NetInterface connect = CattleNetWorker.getConnect();
        Call<RspPiece<TimeLineModel>> rspPieceCall = connect.saveTimeLine(timeLinePiece);

        rspPieceCall.enqueue(new Callback<RspPiece<TimeLineModel>>() {
            @Override
            public void onResponse(@NonNull Call<RspPiece<TimeLineModel>> call, @NonNull Response<RspPiece<TimeLineModel>> response) {


            }

            @Override
            public void onFailure(@NonNull Call<RspPiece<TimeLineModel>> call, @NonNull Throwable t) {
                // 网络请求失败
                TimeCreatorPresenter.this.onDataNotAvailable(R.string.data_network_error);
            }
        });
    }
}
