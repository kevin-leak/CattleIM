package com.example.thinkpad.cattleim.activities.creators;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.common.utils.DateTimeUtil;
import com.example.common.widget.ToolbarActivity;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.activities.MemberSelectActivity;
import com.example.thinkpad.cattleim.adapter.TextWatcherAdapter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@SuppressLint("Registered")
public class TimeCreatorActivity extends ToolbarActivity {

    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.tie_content)
    TextInputEditText tieContent;
    @BindView(R.id.rl_start_time)
    RelativeLayout rlStartTime;
    @BindView(R.id.rl_end_time)
    RelativeLayout rlEndTime;
    @BindView(R.id.rl_to)
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
     * 记录开始的实际
     */
    private String startTimeString;

    /**
     * 记录
     */
    private String endTimeString;

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


    @Override
    public void initToolbar(Toolbar toolbar) {
        toolbar.setTitle("");
        title.setText("日程");
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

        rlStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog =
                        new TimePickerDialog(TimeCreatorActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        startTimeString = hourOfDay + ":" + minute;
                                        tvStarTime.setText(startTimeString);
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
                                        tvEndTime.setText(endTimeString);
                                    }
                                }, day, minute, true);
                dialog.show();
            }
        });

        tvSend.setClickable(false);
        tvSend.setTextColor(Color.parseColor("#FF96D9DD"));
        tieContent.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);

                if (!TextUtils.isEmpty(s.toString())) {
                    tvSend.setClickable(true);
                    tvSend.setTextColor(Color.parseColor("#0099A7"));
                }else {
                    tvSend.setClickable(false);
                    tvSend.setTextColor(Color.parseColor("#FF96D9DD"));
                }
            }
        });

    }

    private void initTime() {
        //获取日历的一个对象
        cal = Calendar.getInstance();
        //获取年月日秒
        day = cal.get(Calendar.DAY_OF_MONTH);//当月几日
        hour = cal.get(Calendar.HOUR_OF_DAY);//当月几日
        minute = cal.get(Calendar.MINUTE);

        // 初始化时间
        tvStarTime.setText(DateTimeUtil.getNowTimeAfter(0, DateTimeUtil.FORMAT_TIME_LINE));
        tvEndTime.setText(DateTimeUtil.getNowTimeAfter(10, DateTimeUtil.FORMAT_TIME_LINE));
    }


    @OnClick(R.id.rl_to)
    void selectMember() {
        MemberSelectActivity.show(TimeCreatorActivity.this);
    }

    @OnClick(R.id.tv_send)
    void sendTimeLine() {
        // todo  presenter
    }

}
