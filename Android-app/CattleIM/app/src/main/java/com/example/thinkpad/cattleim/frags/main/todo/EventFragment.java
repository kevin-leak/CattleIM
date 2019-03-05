package com.example.thinkpad.cattleim.frags.main.todo;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.common.utils.DateTimeUtil;
import com.example.common.widget.AvatarView;
import com.example.common.widget.recycler.RecyclerAdapter;
import com.example.factory.contract.todo.EventContract;
import com.example.factory.presenter.todo.EventPresenter;
import com.example.factory.view.BasePresenterFragment;
import com.example.netKit.db.Event;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.ConversationActivity;

import java.util.HashMap;

import butterknife.BindView;

/**
 * @author kevin
 * 展示：消息，通话，请求事件 ----> 好友，团，商家
 */
public class EventFragment extends BasePresenterFragment<EventContract.Presenter>
        implements EventContract.View {

    final static String TAG = EventFragment.class.getName();
    public static boolean isOpen;


    @BindView(R.id.rv_event)
    RecyclerView rvEvent;

    RecyclerAdapter<Event> eventAdapter = null;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_event;
    }


    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);


        rvEvent.setLayoutManager(new LinearLayoutManager(getContext()));
        eventAdapter = new RecyclerAdapter<Event>() {
            @Override
            protected int getItemViewType(int position, Event event) {
                return R.layout.cell_chat_list;
            }

            @Override
            protected ViewHolder<Event> onCreateViewHolder(View root, int viewType) {
                return new EventHolder(root);
            }
        };
        rvEvent.setAdapter(eventAdapter);

        eventAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Event>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Event event) {
                // 跳转到聊天界面
                ConversationActivity.show(EventFragment.this, event);

                read(event);
            }
        });

    }


    /**
     * 初始化presenter，要求子类必须实现
     */
    @Override
    protected EventContract.Presenter initPresent() {
        return new EventPresenter(this);
    }


    @Override
    protected void onFirstInit() {
        super.onFirstInit();

        presenter.start();
    }

    @NonNull
    @Override
    public RecyclerAdapter<Event> getRecyclerAdapter() {
        return eventAdapter;
    }

    @Override
    public void onAdapterDataChanged() {


    }

    @Override
    public void read(Event event) {
        // 用来处理消息已经读取与后端的通知
        // todo  只允许，在conversation activity 销毁前调度，实现
        presenter.sendReadEvent(event);
    }

    class EventHolder extends RecyclerAdapter.ViewHolder<Event> {


        @BindView(R.id.civ_avatar)
        AvatarView avatar;

        @BindView(R.id.txt_name)
        TextView txtName;

        @BindView(R.id.txt_content)
        TextView textContent;

        @BindView(R.id.txt_time)
        TextView txtTime;

        @BindView(R.id.btn_chat_count)
        Button chatCount;

        public EventHolder(View itemView) {
            super(itemView);
        }

        /**
         * 当触发绑定数据的时候，的回掉；必须复写
         *
         * @param event 绑定的数据
         */
        @SuppressLint("SetTextI18n")
        @Override
        protected void onBind(Event event) {
            HashMap<String, String> info = event.getSomeone();
            avatar.setup(Glide.with(EventFragment.this), info.get(Event.INFO_AVATAR));

            txtName.setText(info.get(Event.INFO_NAME));

            textContent.setText(event.getContent());

            txtTime.setText(DateTimeUtil.getSampleDate(event.getModifyAt()));

            if (event.getUnReadCount() != 0) {
                chatCount.setVisibility(View.VISIBLE);
                chatCount.setText(Integer.toString(event.getUnReadCount()));
            } else {
                chatCount.setVisibility(View.GONE);
                chatCount.setText("0");
            }

        }

    }

}
