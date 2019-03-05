package com.example.thinkpad.cattleim.activities.creators;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.common.utils.DateTimeUtil;
import com.example.factory.contract.todo.TimeCreatorContract;
import com.example.factory.presenter.todo.TimeCreatorPresenter;
import com.example.factory.view.PresentToolActivity;
import com.example.netKit.db.TimeLine;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.adapter.TextWatcherAdapter;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("Registered")
public class TimeCreatorActivity extends PresentToolActivity<TimeCreatorContract.Presenter>
        implements TimeCreatorContract.View, PopupMenu.OnMenuItemClickListener {

    @BindView(R.id.tv_repeat_type)
    TextView tvRepeatType;
    private String TAG = TimeCreatorActivity.class.getName();

    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.tie_content)
    TextInputEditText tieContent;
    @BindView(R.id.rl_start_time)
    RelativeLayout rlStartTime;
    @BindView(R.id.rl_end_time)
    RelativeLayout rlEndTime;


    @BindView(R.id.rl_type)
    RelativeLayout rlTo;


    @BindView(R.id.tv_star_time)
    TextView tvStarTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_send)
    TextView tvSend;


    private Calendar cal;
    private int day;
    private int minute;
    private int hour;

    /**
     * 出来设定选择重复的
     */
    private int repeatType = TimeLine.TYPE_EVERY;


    /**
     * 记录开始的实际
     */
    private String startTimeString;

    /**
     * 记录
     */
    private String endTimeString;
    private int year;
    private int month;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initWindows() {
        super.initWindows();

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_creator_time;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void initToolbar(Toolbar toolbar) {
        toolbar.setTitle("");
        title.setText("time line");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        super.initToolbar(toolbar);
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initWidget() {
        super.initWidget();

        initTime();

        registerTimePicker();

        initSend();

        // rlTo 设置注册context menu
        registerForContextMenu(rlTo);
    }

    /**
     * 初始化时间选择器
     */
    private void registerTimePicker() {
        rlStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog =
                        new TimePickerDialog(TimeCreatorActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        startTimeString = hourOfDay + ":" + minute;
                                        tvStarTime.setText(DateTimeUtil.getFormatTimeline(startTimeString));
                                    }
                                }, day, minute, true);
                dialog.show();
            }
        });


        rlEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog =
                        new TimePickerDialog(TimeCreatorActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        endTimeString = hourOfDay + ":" + minute;
                                        tvEndTime.setText(DateTimeUtil.getFormatTimeline(endTimeString));
                                    }
                                }, day, minute, true);
                dialog.show();
            }
        });
    }

    /**
     * 初始化发送按钮
     */
    private void initSend() {
        tvSend.setClickable(false);
        tvSend.setTextColor(Color.parseColor("#FF96D9DD"));
        tieContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);

                if (!TextUtils.isEmpty(s.toString())) {
                    tvSend.setClickable(true);
                    tvSend.setTextColor(Color.parseColor("#0099A7"));
                } else {
                    tvSend.setClickable(false);
                    tvSend.setTextColor(Color.parseColor("#FF96D9DD"));
                }
            }
        });
    }

    /**
     * 初始化当前时间
     */
    private void initTime() {
        //获取日历的一个对象
        cal = Calendar.getInstance();
        //获取年月日秒
        day = cal.get(Calendar.DAY_OF_MONTH);//当月几日
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        hour = cal.get(Calendar.HOUR_OF_DAY);//当月几日
        minute = cal.get(Calendar.MINUTE);


        reSetTime();
    }


    /**
     * 重新设定时间
     */
    void reSetTime() {
        // 初始化时间
        startTimeString = DateTimeUtil.getNowTimeAfter(0, DateTimeUtil.FORMAT_TIME_LINE);
        endTimeString = DateTimeUtil.getNowTimeAfter(10, DateTimeUtil.FORMAT_TIME_LINE);
        tvStarTime.setText(startTimeString);
        tvEndTime.setText(endTimeString);
    }

    @OnClick(R.id.rl_type)
    void selectMember(View v) {
//        v.showContextMenu();
        //创建弹出式菜单对象（最低版本11）
        PopupMenu popup = new PopupMenu(this, v);//第二个参数是绑定的那个view
        //获取菜单填充器
        MenuInflater inflater = popup.getMenuInflater();
        //填充菜单
        inflater.inflate(R.menu.time_line_type, popup.getMenu());
        //绑定菜单项的点击事件
        popup.setOnMenuItemClickListener(this);

        //显示(这一行代码不要忘记了)
        popup.show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.time_line_type, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.every_time:
                repeatType = TimeLine.TYPE_EVERY;
                tvRepeatType.setText(R.string.every_time);
                break;
            case R.id.every_week:
                repeatType = TimeLine.TYPE_EVERY_WEEK;
                tvRepeatType.setText(R.string.every_week);
                break;
            case R.id.every_moth:
                repeatType = TimeLine.TYPE_EVERY_MOTH;
                tvRepeatType.setText(R.string.every_moth);
                break;
        }
        return super.onContextItemSelected(item);
    }


    @OnClick(R.id.tv_send)
    void sendTimeLine() {

//
        if (DateTimeUtil.getDateTime(startTimeString, DateTimeUtil.FORMAT_TIME_LINE).getTime() >
                DateTimeUtil.getDateTime(endTimeString, DateTimeUtil.FORMAT_TIME_LINE).getTime()) {
            reSetTime();
            Toast.makeText(TimeCreatorActivity.this, "时间设置不正常", Toast.LENGTH_LONG).show();
        } else {
//            mPresenter.add(tieContent.getText(), )
            Date start = DateTimeUtil
                    .getDateTime(year + ":" + month + ":" + startTimeString
                            , DateTimeUtil.Detail_TIME_LINE);
            Date end = DateTimeUtil
                    .getDateTime(year + ":" + month + ":" + endTimeString
                            , DateTimeUtil.Detail_TIME_LINE);


            mPresenter.add(tieContent.getText().toString(), start, end, repeatType);

        }
    }

    /**
     * 初始化Presenter
     *
     * @return Presenter
     */
    @Override
    protected TimeCreatorContract.Presenter initPresenter() {
        return new TimeCreatorPresenter(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }
}
