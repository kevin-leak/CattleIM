package com.example.thinkpad.cattleim.frags.main.contact;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.common.tools.DateTimeUtil;
import com.example.common.widget.AvatarView;
import com.example.common.widget.recycler.Decoration;
import com.example.common.widget.recycler.RecyclerAdapter;
import com.example.factory.presenter.friends.FriendsContract;
import com.example.factory.presenter.friends.FriendsPresenter;
import com.example.factory.view.BasePresenterFragment;
import com.example.netKit.db.User;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.MessageActivity;
import com.example.thinkpad.cattleim.activities.PersonActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.ContentValues.TAG;

/**
 * 用于好友与团的查询
 */
public class FriendsFragment extends BasePresenterFragment<FriendsContract.Presenter>
        implements FriendsContract.View {

    final static String TAG = "FriendsFragment";

    @BindView(R.id.rcv_friends)
    RecyclerView rcvFriends;
    private RecyclerAdapter<User> mAdapter;

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_group;
    }

    @Override
    protected void intiArgs(Bundle arguments) {
        super.intiArgs(arguments);
    }

    /**
     * 初始化presenter，要求子类必须实现
     */
    @Override
    protected FriendsContract.Presenter initPresent() {
        return new FriendsPresenter(this);
    }


    @Override
    protected void onFirstInit() {
        super.onFirstInit();

        presenter.start();
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);


        // 初始化Recycler
        rcvFriends.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvFriends.addItemDecoration(new Decoration());
        rcvFriends.setAdapter(mAdapter = new RecyclerAdapter<User>() {
            /**
             * 得到布局的类型
             *
             * @param position 坐标
             * @param user     当前的数据
             * @return XML文件的ID，用于创建ViewHolder
             */
            @Override
            protected int getItemViewType(int position, User user) {
                return R.layout.cell_friends_contact;
            }

            /**
             * 得到一个新的ViewHolder
             *
             * @param root     根布局
             * @param viewType 布局类型，其实就是XML的ID
             * @return ViewHolder
             */
            @Override
            protected ViewHolder<User> onCreateViewHolder(View root, int viewType) {
                return new FriendsFragment.ViewHolder(root);
            }
        });

        mAdapter.setListener(new RecyclerAdapter.AdapterListenerImpl<User>() {
            @Override
            public void onItemClick(RecyclerAdapter.ViewHolder holder, User user) {
                // 跳转到聊天界面
                MessageActivity.show(FriendsFragment.this.getActivity(), user);
            }
        });

    }

    @Override
    public RecyclerAdapter<User> getRecyclerAdapter() {
        return mAdapter;
    }

    @Override
    public void onAdapterDataChanged() {


    }

    class ViewHolder extends  RecyclerAdapter.ViewHolder<User> {


        @BindView(R.id.civ_avatar)
        AvatarView civAvatar;

        @BindView(R.id.txt_name)
        TextView txtName;

        @BindView(R.id.txt_time)
        TextView txtTime;



        @BindView(R.id.txt_desc)
        TextView txtDesc;


        private User user;


        public ViewHolder(View root) {
            super(root);
        }

        /**
         * 当触发绑定数据的时候，的回掉；必须复写
         *
         * @param user 绑定的数据
         */
        @Override
        protected void onBind(User user) {
            this.user = user;
            civAvatar.setup(Glide.with(FriendsFragment.this), user);
            txtName.setText(user.getUsername());
            txtTime.setText(DateTimeUtil.getSampleDate(user.getModifyAt()));
            txtDesc.setText(user.getDesc());
        }



        @OnClick(R.id.civ_avatar)
        void showDetails(){
            PersonActivity.show(getActivity(), user.getId());
        }
    }
}
