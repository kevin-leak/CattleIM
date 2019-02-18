package com.example.thinkpad.cattleim.frags.main.todo;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.common.utils.DateTimeUtil;
import com.example.common.widget.recycler.RecyclerAdapter;
import com.example.factory.contract.todo.TimeLineContract;
import com.example.factory.presenter.todo.TimeLinePresenter;
import com.example.factory.view.BasePresenterFragment;
import com.example.netKit.db.TimeLine;
import com.example.thinkpad.cattleim.R;

import java.util.Date;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author kevin
 * 展示：当天日程，设计课表，计划，会议，遗留 ===== 输入文字时候的监控，打开其他软件
 */
public class TimeLineFragment extends BasePresenterFragment<TimeLineContract.Presenter>
        implements TimeLineContract.View {

    @BindView(R.id.rv_time_line)
    RecyclerView rvTimeLine;
    private RecyclerAdapter<TimeLine> timeLineAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_timeline;
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);


        rvTimeLine.setLayoutManager(new LinearLayoutManager(getContext()));
        timeLineAdapter = new RecyclerAdapter<TimeLine>() {
            @Override
            protected int getItemViewType(int position, TimeLine timeLine) {
                return R.layout.time_line_item;
            }

            @Override
            protected ViewHolder<TimeLine> onCreateViewHolder(View root, int viewType) {
                return new TimeLinetHolder(root);
            }
        };
        rvTimeLine.setAdapter(timeLineAdapter);

    }

    @Override
    protected void onFirstInit() {
        super.onFirstInit();

        presenter.start();
    }

    @Override
    public RecyclerAdapter getRecyclerAdapter() {
        return timeLineAdapter;
    }

    @Override
    public void onAdapterDataChanged() {

    }

    /**
     * 初始化presenter，要求子类必须实现
     */
    @Override
    protected TimeLineContract.Presenter initPresent() {
        return new TimeLinePresenter(this);
    }

    public class TimeLinetHolder extends RecyclerAdapter.ViewHolder<TimeLine> {


        @BindView(R.id.civ_circularity)
        CircleImageView node;

        @BindView(R.id.tv_time)
        TextView tvTime;

        @BindView(R.id.tv_content)
        TextView content;

        public TimeLinetHolder(View root) {
            super(root);
        }

        /**
         * 当触发绑定数据的时候，的回掉；必须复写
         *
         * @param timeLine 绑定的数据
         */
        @Override
        protected void onBind(TimeLine timeLine) {
             tvTime.setText(DateTimeUtil.getTimeline(timeLine.getStartTime()));
             content.setText(timeLine.getContent());
        }
    }
}