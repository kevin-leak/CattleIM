package com.example.thinkpad.cattleim.frags.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.common.app.BaseFragment;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.SearchActivity;
import com.example.thinkpad.cattleim.frags.main.todo.EventFragment;
import com.example.thinkpad.cattleim.frags.main.todo.LinkTaskFragment;
import com.example.thinkpad.cattleim.frags.main.todo.TimeLineFragment;
import com.example.thinkpad.cattleim.helper.NavHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class TodoFragment extends BaseFragment implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnNavChangeListener {


    @BindView(R.id.todo_nav)
    BottomNavigationView todoNav;
    @BindView(R.id.todo_container)
    FrameLayout todoContainer;
    private NavHelper mHelper;
    private FragmentActivity mActivity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivity = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_todo;
    }

    @Override
    protected void initData() {
        super.initData();
        // 从底部导中接管我们的Menu，然后进行手动的触发第一次点击
        Menu menu = todoNav.getMenu();
        // 触发首次选中Home
        menu.performIdentifierAction(R.id.timeline, 0);

    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);


        bindFragment();
    }


    @OnClick(R.id.im_search)
    void onClickSearch() {
        SearchActivity.show(getActivity(), SearchActivity.SCHEDULE_TYPE);
    }


    /**
     * fragment 与tab相互绑定
     */
    private void bindFragment() {

        mHelper = new NavHelper(mActivity, R.id.todo_container, mActivity.getSupportFragmentManager(), this);
        mHelper.add(R.id.timeline, new NavHelper.Tab(TimeLineFragment.class, R.string.timeline))
                .add(R.id.link_tasks, new NavHelper.Tab(LinkTaskFragment.class, R.string.linkTasks))
                .add(R.id.message_event, new NavHelper.Tab(EventFragment.class, R.string.eventMessage));


        todoNav.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return mHelper.performClickMenu(menuItem.getItemId());
    }

    @Override
    public void OnNavChanged(NavHelper.Tab newTab, NavHelper.Tab oldTab) {
        if (newTab.clx != EventFragment.class ) {
            // 处理是否要进行消息通知
            EventFragment.isOpen = false;
        }else {
            EventFragment.isOpen = true;
        }
    }

}
