package com.example.thinkpad.cattleim.frags.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.common.app.BaseFragment;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.SearchActivity;
import com.example.thinkpad.cattleim.activities.contact.CreateGroupActivity;
import com.example.thinkpad.cattleim.activities.contact.CreateInfoActivity;
import com.example.thinkpad.cattleim.activities.contact.CreateTagActivity;
import com.example.thinkpad.cattleim.frags.main.contact.DataBaseFragment;
import com.example.thinkpad.cattleim.frags.main.contact.FriendsFragment;
import com.example.thinkpad.cattleim.frags.main.contact.TagFragment;
import com.example.thinkpad.cattleim.helper.NavHelper;

import butterknife.BindView;
import butterknife.OnClick;

public class ContactFragment extends BaseFragment implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnNavChangeListener {


    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.bnv_contactNav)
    BottomNavigationView bnvContactNav;
    @BindView(R.id.contact_container)
    FrameLayout contactContainer;
    @BindView(R.id.tb_contact)
    Toolbar tbContact;
    private FragmentActivity mActivity;
    private NavHelper mHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initData() {
        super.initData();
        // 从底部导中接管我们的Menu，然后进行手动的触发第一次点击
        Menu menu = bnvContactNav.getMenu();
        // 触发首次选中Home
        menu.performIdentifierAction(R.id.group, 0);
    }

    @Override
    protected void initWidgets(View root) {
        super.initWidgets(root);

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(tbContact);

        // 设置底边ico显示自己的颜色
        bnvContactNav.setItemIconTintList(null);
        bindFragment();

    }

    @OnClick(R.id.im_search)
    void onClickSearch() {
        SearchActivity.show(getActivity(), SearchActivity.CONTACT_TYPE);
    }


    /**
     * fragment 与tab相互绑定
     */
    private void bindFragment() {

        mHelper = new NavHelper(mActivity, R.id.contact_container, mActivity.getSupportFragmentManager(), this);
        mHelper.add(R.id.group, new NavHelper.Tab(FriendsFragment.class, R.string.group))
                .add(R.id.tag, new NavHelper.Tab(TagFragment.class, R.string.tag))
                .add(R.id.database, new NavHelper.Tab(DataBaseFragment.class, R.string.database));


        bnvContactNav.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return mHelper.performClickMenu(menuItem.getItemId());
    }

    @Override
    public void OnNavChanged(NavHelper.Tab newTab, NavHelper.Tab oldTab) {

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.contact_action, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.find_some:
                SearchActivity.show(getActivity(), SearchActivity.CONTACT_TYPE);
                break;
            case R.id.add_group:
                // todo
                CreateGroupActivity.show(getActivity());
                break;
            case R.id.add_tag:
                // todo 后期修改
                CreateTagActivity.show(getActivity());
                break;
            case R.id.add_info:
                // todo 后期修改
                CreateInfoActivity.show(getActivity());
                break;
        }

        return true;
    }
}
