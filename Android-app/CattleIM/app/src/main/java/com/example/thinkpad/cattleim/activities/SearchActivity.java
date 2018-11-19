package com.example.thinkpad.cattleim.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.common.app.BaseActivity;
import com.example.common.app.BaseFragment;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.adapter.SearchAdapter;
import com.example.thinkpad.cattleim.frags.search.SearchGroupFragment;
import com.example.thinkpad.cattleim.frags.search.SearchNoticeFragment;
import com.example.thinkpad.cattleim.frags.search.SearchTagFragment;
import com.example.thinkpad.cattleim.frags.search.SearchTaskFragment;
import com.example.thinkpad.cattleim.frags.search.SearchTimeFragment;
import com.example.thinkpad.cattleim.frags.search.SearchTopicFragment;
import com.example.thinkpad.cattleim.frags.search.SearchUserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    /**
     * user, group, tag 的搜索
     */
    public static final int CONTACT_TYPE = 0;

    /**
     * task, event, topic, notice
     */
    public static final int SCHEDULE_TYPE = 1;
    @BindView(R.id.lv_select)
    ListView lvSelect;
    @BindView(R.id.sv_Search)
    SearchView svSearch;
    @BindView(R.id.btn_back)
    TextView btnBack;
    @BindView(R.id.lay_container)
    FrameLayout layContainer;
    private SearchAdapter selectAdapter;
    private String TAG = "SearchActivity";

    private int currentType;
    /**
     * 当前出输入框中的文字
     */
    private String searchStr;
    private List<SearchFragment> fragmentList;
    private SearchFragment currentFragment;
    private FragmentTransaction fragmentTransaction;
    private SearchFragment lastFragment;


    public static void show(Context context, int type) {
        context.startActivity(new Intent(context, SearchActivity.class).putExtra("type", type));
    }


    @OnClick(R.id.btn_back)
    void onBack() {
        onBackPressed();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected boolean initArgs(Bundle bundle) {
        currentType = bundle.getInt("type");
        fragmentList = new ArrayList<>();
        initFragment();

        return super.initArgs(bundle);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint({"ResourceType", "NewApi"})
    @Override
    protected void initWidget() {
        super.initWidget();

        initSearch();

        // 初始化当前list view的adapter 同时传入，当前面查询的类型
        selectAdapter = new SearchAdapter(this, R.layout.search_item, currentType);
        lvSelect.setAdapter(selectAdapter);
        // 设置当前listView item 的分割线
        lvSelect.setDivider(getDrawable(R.drawable.search_divider));
        lvSelect.setDividerHeight(1);

    }

    /**
     * 为了能够及时搜索，必须先初始化fragment，
     * 不然等选择完fragment的时候present无法初始化
     *
     * */
    private void initFragment() {
        if (currentType == CONTACT_TYPE){
            fragmentList.add(new SearchUserFragment());
            fragmentList.add(new SearchGroupFragment());
            fragmentList.add(new SearchTagFragment());
        }else {
            fragmentList.add(new SearchTimeFragment());
            fragmentList.add(new SearchTaskFragment());
            fragmentList.add( new SearchTopicFragment());
            fragmentList.add(new SearchNoticeFragment());
        }
    }

    /**
     * 用来初始化search
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initSearch() {
        // 设置取消的图标，换成自己的
        ImageView closeViewIcon = svSearch.findViewById(R.id.search_close_btn);
        closeViewIcon.setImageDrawable(ContextCompat
                .getDrawable(this, R.drawable.ic_cancel_input));
        // 设置下边线
        svSearch.findViewById(R.id.search_plate).setBackground(null);
        svSearch.findViewById(R.id.submit_area).setBackground(null);

        // 设置search空间默认为展开且聚焦
        svSearch.setFocusable(true);
        svSearch.onActionViewExpanded();
    }


    @SuppressLint({"NewApi", "CommitTransaction"})
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initData() {
        super.initData();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        dispatchSearch();
    }

    /**
     * 设置分发的观察器
     */
    public void dispatchSearch() {
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String str) {
                searchStr = str;
                query(str);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String str) {
                searchStr = str;
                selectAdapter.updateTitle(str);

                return true;
            }
        });

        lvSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lvSelect.setVisibility(View.GONE);
                currentFragment = fragmentList.get(position);


                if (lastFragment != null){
                    fragmentTransaction.detach((BaseFragment) lastFragment);
                }
                currentFragment = fragmentList.get(position);


                if (lastFragment != currentFragment){
                    fragmentTransaction
                            .add(R.id.lay_container, (BaseFragment) currentFragment)
                            .commit();
                }
                lastFragment = currentFragment;
                query(searchStr);
            }
        });

    }

    private void query(String s) {
        if (lvSelect.getVisibility() == View.GONE && currentFragment != null){
            Log.e(TAG, "query: " + "ppp" );
            currentFragment.search(searchStr);
        }
    }


    /**
     * 搜索的Fragment必须继承的接口
     */
    public interface SearchFragment {
        void search(String content);
    }


    @Override
    public void onBackPressed() {
        // 开始进入，shape_search activity 时候listView 是可见的，当我们我选择item查询时时不可见的
        // 为了使得用户能够返回重新选择，拦截处理了一下back 按钮
        if (lvSelect.getVisibility() != View.VISIBLE) {
            lvSelect.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

}
