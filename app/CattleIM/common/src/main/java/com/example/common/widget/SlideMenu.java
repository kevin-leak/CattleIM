package com.example.common.widget;

import android.animation.FloatEvaluator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.example.common.R;

public class SlideMenu extends FrameLayout{

    private View menuView;
    private View mainView;
    private ViewDragHelper viewDragHelper;
    private float width;
    private float dragRange;
    private FloatEvaluator floatEvaluator;

    public SlideMenu(@NonNull Context context) {
        super(context);
        init();
    }

    public SlideMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SlideMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //定义状态常量
    enum DragState{
        Open,Close;
    }

    private DragState currentState = DragState.Close;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //处理异常
        if(getChildCount() > 2){
            throw new IllegalArgumentException("只能有两个View");
        }
        menuView = getChildAt(0);
        mainView = getChildAt(1);
    }

    public void init(){
        viewDragHelper = ViewDragHelper.create(this,cb);
        floatEvaluator = new FloatEvaluator();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        width = getWidth();
        dragRange = 0.8f*width;
        menuView.findViewById(R.id.llInfo).setMinimumWidth((int)width);
    }

    private ViewDragHelper.Callback cb = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == menuView || child == mainView;
        }


        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == mainView){
                if (left < 0 ) left = 0;
                if (left > dragRange) left = (int)dragRange;
            }
            return left;
        }
        @Override
        public int getViewHorizontalDragRange(View child) {
            return (int) dragRange;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            if (changedView == menuView){
                //固定住menuView;
                menuView.layout(0,0,mainView.getMeasuredWidth(),menuView.getMeasuredHeight());
                int newLeft = mainView.getLeft()+dx;
                if (newLeft < 0 ) newLeft = 0;
                if (newLeft > dragRange) newLeft = (int) dragRange;
                mainView.layout(newLeft,mainView.getTop()+dy,newLeft+mainView.getMeasuredWidth(),menuView.getBottom()+dy);
            }
            float fractionScale = mainView.getLeft() / dragRange;
            float fractionTranslation  = -menuView.getMeasuredWidth();
            //执行伴随的动画
            executeAnim(fractionScale,fractionTranslation);

            //更改状态回调listener
            if(fractionScale == 0 && currentState !=DragState.Close){
                currentState = DragState.Close;
                if(listener != null){
                    listener.onClose();
                }
            }else if(fractionScale == 1f && currentState != DragState.Open){
                currentState = DragState.Open;
                if(listener != null){
                    listener.onOpen();
                }
            }
            if (listener != null){
                listener.onDrag(fractionScale);
            }
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            if (mainView.getLeft() < dragRange/2){
                //处于左半边
                viewDragHelper.smoothSlideViewTo(mainView,0,mainView.getTop());
                //刷新整个界面
                ViewCompat.postInvalidateOnAnimation(SlideMenu.this);
            }else {
                viewDragHelper.smoothSlideViewTo(mainView,(int) dragRange,mainView.getTop());
                //刷新整个界面
                ViewCompat.postInvalidateOnAnimation(SlideMenu.this);
            }
        }
    };
    @SuppressLint("ResourceAsColor")
    private void executeAnim(float fractionScale, float fractionTranslation){
        //fraction:0-1;
        //缩小mainView
        float scaleValue = floatEvaluator.evaluate(fractionScale,1f,0.8f);
//        mainView.setScaleX(scaleValue);
//        mainView.setScaleY(scaleValue);

        //移动menuView
        menuView.setTranslationX(floatEvaluator.evaluate(fractionScale,fractionTranslation,0f));
//        menuView.setScaleX(floatEvaluator.evaluate(fractionScale,0.6f,1f));
//        menuView.setScaleY(floatEvaluator.evaluate(fractionScale,0.6f,1f));

        //change the alpha;
        menuView.setAlpha(floatEvaluator.evaluate(fractionScale,0.3f,1f));
        mainView.setAlpha(floatEvaluator.evaluate(fractionScale,1f,0.9f));

        //蒙上一层颜色
        Log.d("fractionScale","               "+fractionScale);
        if (fractionScale > 0.5){
            mainView.setBackgroundColor(Color.parseColor("#aea9a9"));
            mainView.findViewById(R.id.rlMainTitle).setBackgroundColor(Color.parseColor("#aea9a9"));

            menuView.setBackgroundColor(Color.WHITE);
            menuView.findViewById(R.id.rlInfoTitle).setBackgroundColor(Color.GRAY);
        }else if (fractionScale < 0.5){
            mainView.setBackgroundColor(Color.WHITE);
            mainView.findViewById(R.id.rlMainTitle).setBackgroundColor(Color.parseColor("#e2917d"));

//            menuView.setBackgroundColor(Color.GRAY);
        }
        //#TODO 设法实现蒙层效果
    }
    public void computeScroll(){
        if (viewDragHelper.continueSettling(true)){
            ViewCompat.postInvalidateOnAnimation(SlideMenu.this);
        }
    }

    private OnDragStateListener listener;
    public void setOnDragStateChangeListener(OnDragStateListener listener){
        this.listener = listener;
    }

    //#TODO 暴露接口给MainActivity
    public interface  OnDragStateListener{
        /**
         * 当打开的时候*/
        void onOpen();
        /**
         * 当关闭的时候*/
        void onClose();
        /**当拖拽时候
         * */
        void onDrag(float s);
    }


}
