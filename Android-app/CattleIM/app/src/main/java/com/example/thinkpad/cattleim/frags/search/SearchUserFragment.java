package com.example.thinkpad.cattleim.frags.search;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.common.app.Application;
import com.example.common.widget.AvatarView;
import com.example.common.widget.recycler.Decoration;
import com.example.common.widget.recycler.RecyclerAdapter;
import com.example.factory.contract.search.SearchContract;
import com.example.factory.presenter.friends.FriendContract;
import com.example.factory.presenter.friends.FriendPresenter;
import com.example.factory.presenter.search.SearchUserPresenter;
import com.example.factory.view.BasePresenterFragment;
import com.example.netKit.model.UserModel;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.PersonActivity;
import com.example.thinkpad.cattleim.activities.SearchActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchUserFragment extends BasePresenterFragment<SearchContract.Presenter>
        implements SearchActivity.SearchFragment, SearchContract.UserView {

    public static final String TAG = "找人";
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    private ProgressDialog dialog;
    private RecyclerAdapter<UserModel> mAdapter;

    public SearchUserFragment() {

    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_search_user;
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);

        // 初始化Recycler
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.addItemDecoration(new Decoration());
        mRecycler.setAdapter(mAdapter = new RecyclerAdapter<UserModel>() {
            @Override
            protected int getItemViewType(int position, UserModel userCard) {
                // 返回cell的布局id
                return R.layout.cell_search_list;
            }

            @Override
            protected ViewHolder<UserModel> onCreateViewHolder(View root, int viewType) {
                return new SearchUserFragment.ViewHolder(root);
            }
        });


    }

    /**
     * 初始化presenter，要求子类必须实现
     */
    @Override
    protected SearchContract.Presenter initPresent() {
        return new SearchUserPresenter(this);
    }

    @Override
    public void search(String content) {
        // fixme 先默认为零，到时根据recycle view来修改
        if (presenter != null && !TextUtils.isEmpty(content)) {
            Log.e(TAG, "search: " + "ppp" );
            presenter.search(content, 0);
        }
    }

    @Override
    public void onSearchDone(List<UserModel> userModel) {
        dialog.cancel();
        Log.e(TAG, "onSearchDone: " + "====>>>");

        // 数据成功的情况下返回数据
        mAdapter.replace(userModel);
    }

    @Override
    public void showDialog() {
        super.showDialog();

        dialog = ProgressDialog.show(this.getActivity(), "加载中", "");
    }

    @Override
    public void showError(int error) {
        super.showError(error);
        dialog.cancel();
        if (dialog != null) {
            dialog.cancel();
            Log.e(TAG, "onSearchDone: " + "====>>>");
        }
    }



    class ViewHolder extends RecyclerAdapter.ViewHolder<UserModel>
            implements FriendContract.View {

        @BindView(R.id.civ_avatar)
        AvatarView civAvatar;

        @BindView(R.id.txt_name)
        TextView mName;

        @BindView(R.id.btn_add)
        Button btnAdd;
        private FriendContract.Present present;
        private UserModel userModel;

        public ViewHolder(View itemView) {
            super(itemView);
            new FriendPresenter(this);
        }

        /**
         * 当触发绑定数据的时候，的回掉；必须复写
         *
         * @param userModel 绑定的数据
         */
        @Override
        protected void onBind(UserModel userModel) {
            this.userModel = userModel;
            mName.setText(userModel.getUsername());
            civAvatar.setup(Glide.with(SearchUserFragment.this), userModel);
        }

        /**
         * View 必须具有展示错误信息的能力
         *
         * @param error 传入错误信息
         */
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void showError(int error) {
            btnAdd.setBackground(getResources().getDrawable(R.drawable.shape_add));
            btnAdd.setTextColor(Color.WHITE);
            btnAdd.setText("添加");
            btnAdd.setClickable(true);

        }

        @OnClick(R.id.btn_add)
        void addFriends(){
            btnAdd.setBackgroundColor(Color.TRANSPARENT);
            btnAdd.setTextColor(Color.GRAY);
            btnAdd.setText("等待验证");
            btnAdd.setClickable(false);
            present.addFriend(userModel.getId());
        }

        @OnClick(R.id.civ_avatar)
        void showDetails(){
            PersonActivity.show(getActivity(), userModel.getId());
        }


        /**
         * @param present 当前的present
         */
        @Override
        public void setPresenter(FriendContract.Present present) {
            this.present = present;
        }

        /**
         * 展示进度栏
         */
        @Override
        public void showDialog() {

        }

        /**
         * 由于子类在都是fragment的情况下这个会被自动的复写
         * 我们不需要管她，在这里我们只是先声明一下我们子类具有这个方法
         * 方便我们在主线程和子线程之间的切换
         */
        @Override
        public Activity getActivity() {
            return SearchUserFragment.this.getActivity();
        }

        @Override
        public void sendSuccess() {
            btnAdd.setText("通过验证");
            btnAdd.setClickable(false);
        }

    }
}
