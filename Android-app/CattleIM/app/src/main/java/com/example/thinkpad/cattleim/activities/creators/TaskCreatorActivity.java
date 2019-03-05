package com.example.thinkpad.cattleim.activities.creators;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.common.utils.DateTimeUtil;
import com.example.factory.contract.todo.TaskCreatorContract;
import com.example.factory.presenter.todo.TaskCreatorPresenter;
import com.example.factory.view.PresentToolActivity;
import com.example.netKit.db.LinkTask;
import com.example.netKit.db.TimeLine;
import com.example.thinkpad.cattleim.R;
import com.example.thinkpad.cattleim.adapter.TextWatcherAdapter;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("Registered")
public class TaskCreatorActivity extends
        PresentToolActivity<TaskCreatorContract.Presenter> implements TaskCreatorContract.View {


    public static final int TOPIC = LinkTask.TOPIC;
    public static final int NOTICE = LinkTask.NOTICE;
    public static final int TASK = LinkTask.TASK;


    public static final String KEY_TYPE = "KEY_TYPE";


    @BindView(R.id.rl_send)
    RelativeLayout rlSend;
    @BindView(R.id.tv_star_time)
    TextView tvStarTime;


    @BindView(R.id.rl_start_time)
    RelativeLayout rlStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.rl_end_time)
    RelativeLayout rlEndTime;
    @BindView(R.id.rl_to)
    RelativeLayout rlTo;

    @BindView(R.id.iv_attach)
    ImageView ivAttach;
    @BindView(R.id.rl_remind_time)
    RelativeLayout rlRemindTime;
    @BindView(R.id.tv_remind_time)
    TextView tvRemindTime;


    /**
     * 当前的创造的数据
     */
    private int type;


    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.tie_content)
    TextInputEditText tieContent;


    /**
     * 记录开始的实际
     */
    private String startDateString;

    /**
     * 记录结束的时间
     */
    private String endDateString;


    /**
     * 记录需要提醒的时间
     */
    private String remindTimeString;

    private Calendar cal;
    private int year;
    private int month;
    private int day;
    private int minute;
    private int hour;


    /**
     * 附件的类型
     */
    private int attachType;


    public static void show(Context context, int type) {

        Intent intent = new Intent(context, TaskCreatorActivity.class).putExtra(KEY_TYPE, type);
        context.startActivity(intent);

    }

    /**
     * @param bundle 参数Bundle
     * @return
     */
    @Override
    protected boolean initArgs(Bundle bundle) {

        type = bundle.getInt(KEY_TYPE);

        return type != 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initWidget() {
        super.initWidget();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        initDate();

        registerDatePicker();

        initSend();

        // ivAttach 设置注册context menu
        registerForContextMenu(ivAttach);

    }

    /**
     * 注册日期选择器
     */
    private void registerDatePicker() {
        rlStartTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(TaskCreatorActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDateString = year + "-" + month + "-" + dayOfMonth;
                        tvStarTime.setText(DateTimeUtil.getFormatTaskDate(startDateString));
                    }
                }, year, month, day);

                DatePicker datePicker = dialog.getDatePicker();
                //隐藏月份
                ((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0))
                        .getChildAt(0).setVisibility(View.GONE);
                dialog.show();
            }
        });


        rlEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(TaskCreatorActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDateString = year + "-" + month + "-" + dayOfMonth;
                        tvEndTime.setText(DateTimeUtil.getFormatTaskDate(startDateString));
                    }
                }, year, month, day);

                DatePicker datePicker = dialog.getDatePicker();
                //隐藏月份
                ((ViewGroup) ((ViewGroup) datePicker.getChildAt(0)).getChildAt(0))
                        .getChildAt(0).setVisibility(View.GONE);
                dialog.show();
            }
        });

        rlRemindTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog dialog =
                        new TimePickerDialog(TaskCreatorActivity.this,
                                new TimePickerDialog.OnTimeSetListener() {
                                    @Override
                                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                        remindTimeString = hourOfDay + ":" + minute;
                                        tvRemindTime.setText(DateTimeUtil.getFormatTimeline(remindTimeString));
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
     * 计算出当前的date 信息
     */
    private void initDate() {
        //获取日历的一个对象
        cal = Calendar.getInstance();
        //获取年月日秒
        day = cal.get(Calendar.DAY_OF_MONTH);//当月几日
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        hour = cal.get(Calendar.HOUR_OF_DAY);//当月几日
        minute = cal.get(Calendar.MINUTE);


        reSetTime();
    }


    /**
     * 重新设定时间
     */
    void reSetTime() {
        // 初始化时间
        startDateString = DateTimeUtil.getNowDateAfter(0, DateTimeUtil.FORMAT);
        endDateString = DateTimeUtil.getNowDateAfter(1, DateTimeUtil.FORMAT);
        remindTimeString = DateTimeUtil.getNowTimeAfter(0, DateTimeUtil.FORMAT_TIME_LINE);
        tvStarTime.setText(startDateString);
        tvEndTime.setText(endDateString);
        tvRemindTime.setText(remindTimeString);
    }


    @OnClick(R.id.rl_to)
    void selectSomeone(View v){
        // todo 选择类型
    }

    @OnClick(R.id.iv_attach)
    void selectAttach(View v){
        v.showContextMenu();
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_creator_task;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void initToolbar(Toolbar toolbar) {
        toolbar.setTitle("");
        title.setText("time line");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        super.initToolbar(toolbar);

        switch (type) {
            case TOPIC:
                title.setText(getApplicationContext().getResources().getString(R.string.topic));
                break;
            case TASK:
                title.setText(getApplicationContext().getResources().getString(R.string.linkTasks));
                break;
            case NOTICE:
                title.setText(getApplicationContext().getResources().getString(R.string.notice));
                break;
        }


    }


    /**
     * 初始化Presenter
     *
     * @return Presenter
     */
    @Override
    protected TaskCreatorContract.Presenter initPresenter() {
        return new TaskCreatorPresenter(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.link_task_type, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.task_photos:
                attachType = TimeLine.TYPE_EVERY;
                break;
            case R.id.task_files:
                attachType = LinkTask.TASK_FILES;
                break;
            case R.id.task_cancel:
                attachType = -1;
                break;
        }
        return super.onContextItemSelected(item);
    }


}
