package com.example.thinkpad.cattleim.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.thinkpad.cattleim.R;
import com.varunest.sparkbutton.SparkButton;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 实现在桌面悬浮显示步骤
 * 1. 实现布局可以移动
 * 2. 实现布局可以扩展和隐藏
 * 3. 实现点击事件，并将点击事件的状态传入调用Service的activity或者fragment
 * 4. 要响应点击事件，并对布局文件进行相应的修改
 */
public class StepService extends Service {

    private WindowManager mWM;
    private int mScreenWidth;
    private int mScreenHeight;
    private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private View viewStep;
    private final LocalBinder mBinder = new LocalBinder();
    private ImageButton ibStepCancel;
    private Intent intent;


    @Override
    public void onCreate() {

        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);

        // todo 集成获取屏幕尺寸
        //获取屏幕宽度
        mScreenWidth = mWM.getDefaultDisplay().getWidth();
        mScreenHeight = mWM.getDefaultDisplay().getHeight();

        intent = new Intent();
        intent.setAction("step");

        //初始化布局
//        showStepLayout();
        super.onCreate();
    }

    @SuppressLint({"ClickableViewAccessibility", "InlinedApi"})
    private void showStepLayout() {

        final WindowManager.LayoutParams params = getLayoutParams();

        //view挂载到屏幕上

        viewStep = View.inflate(this, R.layout.step_view, null);

        ibStepCancel = viewStep.findViewById(R.id.ib_step_cancel);


        ibStepCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("cancel", true);
                //防止多次点击
                ibStepCancel.setClickable(false);
                // 实现取消步骤栏
                sendBroadcast(intent);
            }
        });


        touchMoveView(params);

        mWM.addView(viewStep, params);

    }

    /**
     * @param params 获取的桌面参数
     *               控制桌面的view移动
     */
    private void touchMoveView(final WindowManager.LayoutParams params) {
        viewStep.setOnTouchListener(new View.OnTouchListener() {
            private int startX;
            private int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //获取按下的xy坐标
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //获取移动xy坐标和按下的xy坐标做差,做差得到的值小火箭移动的距离
                        //移动过程中做容错处理
                        //第一次移动到的位置,作为第二次移动的初始位置
                        int moveX = (int) event.getRawX();
                        int moveY = (int) event.getRawY();

                        int disX = moveX - startX;
                        int disY = moveY - startY;

                        params.x = params.x + disX;
                        params.y = params.y + disY;

                        //在窗体中仅仅告知吐司的左上角的坐标
                        if (params.x < 0) {
                            params.x = 0;
                        }
                        if (params.y < 0) {
                            params.y = 0;
                        }

                        if (params.x > mScreenWidth - viewStep.getWidth()) {
                            params.x = mScreenWidth - viewStep.getWidth();
                        }

                        if (params.y > mScreenHeight - 22 - viewStep.getHeight()) {
                            params.y = mScreenHeight - 22 - viewStep.getHeight();
                        }

                        //告知吐司在窗体上刷新
                        mWM.updateViewLayout(viewStep, params);

                        //在第一次移动完成后,将最终坐标作为第二次移动的起始坐标
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_UP:
                        //手指放开的时候,如果放手坐标,则指定区域内

                        break;
                }
                return true;
            }
        });

    }

    /**
     * @return 返回桌面的参数
     */
    @NonNull
    private WindowManager.LayoutParams getLayoutParams() {
        //给吐司定义出来的参数(宽高,类型,触摸方式)
        final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//				| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE	因为吐司需要根据手势去移动,所以必须要能触摸
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;

        // todo notify 这里需要特别注意
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }


        //将吐司放置在左上角显示
        params.gravity = Gravity.TOP + Gravity.LEFT;
        params.setTitle("Toast");
        return params;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public class LocalBinder extends Binder {


    }

    public void onDestroy() {
        if(mWM!=null && viewStep!=null){

            mWM.removeView(viewStep);
        }
        super.onDestroy();
    }
}
