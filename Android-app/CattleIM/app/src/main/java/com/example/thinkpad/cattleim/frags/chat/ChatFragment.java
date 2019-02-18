package com.example.thinkpad.cattleim.frags.chat;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.common.utils.NetUtils;
import com.example.common.widget.AvatarView;
import com.example.common.widget.ConversationLayout;
import com.example.common.widget.recycler.RecyclerAdapter;
import com.example.factory.contract.chat.ChatContract;
import com.example.factory.view.BasePresenterFragment;
import com.example.netKit.db.Conversation;
import com.example.netKit.db.Event;
import com.example.netKit.db.User;
import com.example.netKit.net.push.ConversationFactory;
import com.example.netKit.net.push.pieces.ConversationAck;
import com.example.netKit.persistence.Account;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.adapter.TextWatcherAdapter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 因为发送消息，不管在group, tag, user,三方面都有共性， 所以先抽象，功能一个个添加
 */
public abstract class ChatFragment<InitModel> extends BasePresenterFragment<ChatContract.Presenter>
        implements ChatContract.View<InitModel> {


    final String TAG = ChatFragment.class.getName();


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.btn_face)
    ImageView btnFace;
    @BindView(R.id.btn_record)
    ImageView btnRecord;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.btn_submit)
    ImageView btnSubmit;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.lay_content)
    ConversationLayout layContent;

    private Adapter mAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_chat;
    }


    @Override
    protected void initWidgets(View root) {

        initHead(root);
        // 控件进行绑定
        super.initWidgets(root);


        initToolbar();
        initAdapter();


        initEditContent();


    }

    private void initAdapter() {
        // RecyclerView基本设置
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new Adapter();
        mRecyclerView.setAdapter(mAdapter);
        // 添加适配器监听器，进行点击的实现
        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<Conversation>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, Conversation message) {

            }
        });
    }

    private void initHead(View root) {
        // 拿到占位布局
        // 替换顶部布局一定需要发生在super之前
        // 防止控件绑定异常
        ViewStub stub = (ViewStub) root.findViewById(R.id.view_stub_header);
        stub.setLayoutResource(getHeaderLayoutId());
        stub.inflate();


    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                    break;
            }
        }

    };

    private void initEditContent() {

        editContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // fixme 没有用
                    handler.sendEmptyMessageDelayed(0, 250);

                }
            }
        });
        editContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                String content = s.toString().trim();
                boolean active = !TextUtils.isEmpty(content);
                btnSubmit.setActivated(active);
            }
        });

    }



    protected abstract int getHeaderLayoutId();

    // 初始化Toolbar
    protected void initToolbar() {
        Toolbar toolbar = mToolbar;
        if (mToolbar != null) {
            toolbar.setNavigationIcon(R.drawable.ic_white_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                }
            });
        }
    }


    @Override
    public RecyclerAdapter<Conversation> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {
        // 界面没有占位布局，Recycler是一直显示的，所有不需要做任何事情

        Log.e(TAG, "onAdapterDataChanged: " + "数据变化了");
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);


    }

    @Override
    protected void initData() {
        super.initData();
        // 开始进行初始化操作
        presenter.start();

    }

    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        if (btnSubmit.isActivated()) {

            if (!NetUtils.isNetworkConnected(getContext())) {
                Toast.makeText(getActivity(),R.string.net_unavailable, Toast.LENGTH_SHORT).show();
            } else {
                // 发送
                String content = editContent.getText().toString();
                editContent.setText("");
                presenter.pushText(content);
            }

        } else {
            onMoreClick();
        }
    }

    private void onMoreClick() {
        // todo 用来发送信息
    }

    public void read(String receiverID, Event event) {
        if (event != null){
            presenter.sendReadEvent(new ConversationAck(receiverID, Account.getUserId(), event.getChatId()), event);
        }
    }


    private class Adapter extends RecyclerAdapter<Conversation> {

        @Override
        protected int getItemViewType(int position, Conversation conversation) {
            //  区分左右消息,用当前用户对 发送者的id进行一个对比就可以知道
            boolean isRight = Account.getUserId().equals(conversation.getSend());

            // fixme 判别对话的类别，是文本，图片，还是音频等其他
            switch (conversation.getCategory()) {
                default:
                    return isRight ? R.layout.message_right : R.layout.message_left;
            }
        }


        @Override
        protected ViewHolder<Conversation> onCreateViewHolder(View root, int viewType) {


            switch (viewType) {
                default:
                    // 是 R.layout.message_right: R.layout.message_left; 两个其中一个都建立text holder
                    // todo 后期发表情，图片，音，holder 有相似之处，我们可以先抽象
                    return new TextHolder(root);
            }
        }

    }

    class BaseHolder extends RecyclerAdapter.ViewHolder<Conversation> {

        @BindView(R.id.av_avatar)
        AvatarView avatar;


        // 这里进行抽象，总体处理时，重新加载的按钮，只有右边有，所以设置可为空
        @Nullable
        @BindView(R.id.iv_reload)
        ImageView reLoad;

        public BaseHolder(View root) {

            super(root);
        }

        /**
         * 当触发绑定数据的时候，的回掉；必须复写
         *
         * @param conversation 绑定的数据
         */
        @Override
        protected void onBind(Conversation conversation) {
            // 设置头像，无论是左边的 界面还是右边的界面，都设置发送者的id
            // 加入是左边的界面，那么一定是别发给我的消息， 则别人是发送者
            // 如果是右边的界面，那么是我发的消息，要再消息栏中显示，那么我是发送者
            // 即使，当前我们要显示的对话条目，都是发送者的条目
            User send = (User) conversation.getSender();
            avatar.setup(Glide.with(ChatFragment.this), send.getAvatar());

            // 获取当前消息的发送转态，来确定，是否显示或者取消重新发送图标
            if (reLoad != null) {
                if (conversation.isDone()) {
                    reLoad.setVisibility(View.GONE);
                } else {
                    reLoad.setVisibility(View.VISIBLE);
                }

                reLoad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 重新发送

                        if (reLoad != null && presenter.rePush(mData)) {
                            // 必须是右边的才有可能需要重新发送
                            // 状态改变需要重新刷新界面当前的信息
                            updateData(mData);
                        }
                    }
                });
            }

        }


    }

    class TextHolder extends BaseHolder {


        @BindView(R.id.tv_chat_content)
        TextView mContent;

        public TextHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void onBind(Conversation conversation) {
            super.onBind(conversation);

            Spannable spannable = new SpannableString(conversation.getContent());

            // todo 表情的解析

            // 把内容设置到布局上
            mContent.setText(spannable);
        }
    }


}
