package com.example.thinkpad.cattleim.frags.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.common.app.BaseFragment;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.frags.main.contact.DataBaseFragment;
import com.example.thinkpad.cattleim.frags.main.contact.GroupFragment;
import com.example.thinkpad.cattleim.frags.main.contact.TagFragment;
import com.example.thinkpad.cattleim.helper.NavHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ContactFragment extends BaseFragment implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnNavChangeListener {


    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.bnv_contactNav)
    BottomNavigationView bnvContactNav;
    @BindView(R.id.sv_contact)
    SearchView svContact;
    @BindView(R.id.contact_container)
    FrameLayout contactContainer;
    Unbinder unbinder;
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


        changeSearchTextColor();

        // 设置底边ico显示自己的颜色
        bnvContactNav.setItemIconTintList(null);
        bindFragment();
    }

    /**
     * 修改输入框的文字颜色     */
    private void changeSearchTextColor() {
        SearchView.SearchAutoComplete textView = svContact.findViewById(R.id.search_src_text);
        textView.setTextColor(Color.parseColor("#000000"));
    }

    /**
     * fragment 与tab相互绑定
     */
    private void bindFragment() {

        mHelper = new NavHelper(mActivity, R.id.contact_container, mActivity.getSupportFragmentManager(), this);
        mHelper.add(R.id.group, new NavHelper.Tab(GroupFragment.class, R.string.group))
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

}
