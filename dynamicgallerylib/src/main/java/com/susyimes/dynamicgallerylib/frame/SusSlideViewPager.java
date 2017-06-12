package com.susyimes.dynamicgallerylib.frame;

/**
 * Created by Susyimes on 2017/1/12 0012.
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class SusSlideViewPager extends ViewPager {

    private int startX;


    private int criticalValue = 200;


    public interface onSideListener {

        void onLeftSide();


        void onRightSide();
    }


    private onSideListener mOnSideListener;


    public void setOnSideListener(onSideListener listener) {
        this.mOnSideListener = listener;
    }

    public void setCriticalValue(int criticalValue) {
        this.criticalValue = criticalValue;
    }

    public SusSlideViewPager(Context context) {
        this(context, null);
    }

    public SusSlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) event.getX();
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (startX - event.getX() > criticalValue && (getCurrentItem() == getAdapter().getCount() - 1)) {
                    if (null != mOnSideListener) {
                        mOnSideListener.onRightSide();
                    }
                }
                if ((event.getX() - startX) > criticalValue && (getCurrentItem() == 0)) {
                    if (null != mOnSideListener) {
                        mOnSideListener.onLeftSide();
                    }
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}
