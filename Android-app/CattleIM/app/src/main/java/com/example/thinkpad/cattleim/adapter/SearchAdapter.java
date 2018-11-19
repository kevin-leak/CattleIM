package com.example.thinkpad.cattleim.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.common.tools.DrawChangeColor;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.SearchActivity;
import com.example.thinkpad.cattleim.frags.search.SearchGroupFragment;
import com.example.thinkpad.cattleim.frags.search.SearchNoticeFragment;
import com.example.thinkpad.cattleim.frags.search.SearchTagFragment;
import com.example.thinkpad.cattleim.frags.search.SearchTaskFragment;
import com.example.thinkpad.cattleim.frags.search.SearchTimeFragment;
import com.example.thinkpad.cattleim.frags.search.SearchTopicFragment;
import com.example.thinkpad.cattleim.frags.search.SearchUserFragment;

/**
 * @author KevinLeak
 *  不可扩展，定死了，用来处理search 的选择栏
 */
public class SearchAdapter extends ArrayAdapter<String> {


    private Integer[] currentIcon = null;
    private String[] currentStr = null;

    private Context context;

    /**
     * @param type 用来判断当前是schedule 1  还是 contact 0
     */
    public SearchAdapter(Context context, int resource, int type) {
        super(context, resource);
        this.context = context;
        initData(type);
    }

    /**
     * @param type 初始化查询的条目
     */
    private void initData(int type) {
        if (type == SearchActivity.CONTACT_TYPE) {
            currentIcon = new Integer[]{
                            R.drawable.ic_account,
                            R.drawable.ic_group,
                            R.drawable.ic_tag,
            };
            // 查询条目的标题
            currentStr = new String[]{
                            SearchUserFragment.TAG, SearchGroupFragment.TAG, SearchTagFragment.TAG
            };
        } else {
            // 查询条目的图标
            currentIcon = new Integer[]{
                            R.drawable.fb_time,
                            R.drawable.fb_flag,
                            R.drawable.fb_topic,
                            R.drawable.fb_notice,
            };
            currentStr = new String[]{
                            SearchTimeFragment.TAG, SearchTaskFragment.TAG,
                            SearchTopicFragment.TAG, SearchNoticeFragment.TAG
            };
        }

        addTitle();
    }

    /**
     * 为item 加入drawable标识
     */
    private void addTitle(){
        if (currentIcon != null && currentStr != null){
            addAll(currentStr);
        }
    }

    /**
     * @param s  传入搜索时候的字符春
     */
    public void updateTitle(String s){
        // 字符串拼接
        String[] changeStr ;
        changeStr = currentStr.clone();
        for (int i = 0; i < changeStr.length; i++) {
            if (TextUtils.isEmpty(s)){
                changeStr[i] += s;
            }else {
                changeStr[i] += "：" + s;
            }
        }
        clear();
        addAll(changeStr);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        // 不改变父类的创建view的方法，拿到view，直接操作
        TextView view = (TextView) super.getView(position, convertView, parent);
        // 设置text view的drawable的颜色
        if (currentIcon!= null)
            setTextDrawable(currentIcon[position], view);
        // 设置drawable的padding
        view.setCompoundDrawablePadding(40);
        return view;
    }

    /**
     * @param resourceId drawable 资源id
     * @param view 一个textview
     */
    private void setTextDrawable(int resourceId, TextView view) {
        Drawable drawable = DrawChangeColor.tintDrawable(
                context.getResources().getDrawable(resourceId), "#5f626e");
        view.setCompoundDrawablesWithIntrinsicBounds(
                drawable, null, null,null);
    }

}