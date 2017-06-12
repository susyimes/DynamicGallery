package com.susyimes.dynamicgallerylib.frame;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Susyimes on 2016/12/28 0028.
 */

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class SusNestedScrollView extends NestedScrollView {
    @SuppressWarnings("unused")
    private int slop;
    @SuppressWarnings("unused")
    private float mInitialMotionX;
    @SuppressWarnings("unused")
    private float mInitialMotionY;
    public SusNestedScrollView(Context context) {
        super(context);
    }

    public SusNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SusNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

  /* @Override
   public boolean onTouchEvent(MotionEvent ev) {

       if (!(ev.getAction()== MotionEvent.ACTION_DOWN)&& ev.getHistorySize() > 0) {


           float yVector = ev.getY() - ev.getHistoricalY(0);
           float xVector = ev.getX() - ev.getHistoricalX(0);


           return Math.abs(yVector) <= Math.abs(xVector) || super.onTouchEvent(ev);
       }

       return super.onTouchEvent(ev);
   }*/

    private float xDistance, yDistance, lastX, lastY;
    @SuppressWarnings("unused")
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                lastX = ev.getX();
                lastY = ev.getY();
                // This is very important line that fixes
                computeScroll();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - lastX);
                yDistance += Math.abs(curY - lastY);
                lastX = curX;
                lastY = curY;
                if (xDistance > yDistance) {
                    return false;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }
    public interface OnScrollChangedListener {
        void onScrollChanged(NestedScrollView who, int l, int t, int oldl,
                             int oldt);
    }
    private OnScrollChangedListener mOnScrollChangedListener;
    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        mOnScrollChangedListener = listener;
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedListener != null) {
            mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

}
