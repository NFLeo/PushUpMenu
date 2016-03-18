package com.peter.pushupmenu.view;

import android.content.Context;
import android.graphics.PointF;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;


/**
 * 伸缩效果的TextView,主要是通过ScaleAnimation 来实现伸缩效果
 *
 */
public class ScalableTextView extends TextView implements Animation.AnimationListener {

    private long lastTime = 0;
    private final Animation mZoomOutAnimation = new
            ScaleAnimation(1, 1.2f, 1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    private final Animation mZoomInAnimation = new
            ScaleAnimation(1.2f, 1f, 1.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    private final Animation mZoomInWithHandleAnimation = new
            ScaleAnimation(1.2f, 1f, 1.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
    private boolean mHas = false;

    private AnimationFinishedListener mAnimationFinishedListener;

    public ScalableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mZoomOutAnimation.setFillAfter(true);
        mZoomInAnimation.setFillAfter(true);
        mZoomOutAnimation.setDuration(200);
        mZoomInAnimation.setDuration(200);
        mZoomInWithHandleAnimation.setFillAfter(true);
        mZoomInWithHandleAnimation.setDuration(200);
        mZoomInWithHandleAnimation.setAnimationListener(this);
    }

    public void setAnimationFinishedListener(AnimationFinishedListener
                                                     animationFinishedListener) {
        this.mAnimationFinishedListener = animationFinishedListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        long currentTime = SystemClock.uptimeMillis();
        boolean intercepter = false;
        boolean quit = false;
        if (currentTime - lastTime > 400) {
            lastTime = currentTime;
        } else {
            intercepter = true;
        }
        float x = event.getX();
        float y = event.getY();

        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (!intercepter) {
                    zoomOutAnimation();
                    return true;
                } else {
                    quit = true;
                    return true;
                }
            case MotionEvent.ACTION_MOVE:
                if(quit) return true;
                if (!contains(x, y)) {
                    zoomInAnimation(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                if(quit) return true;
                zoomInAnimation(true);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if(quit) return true;
                zoomInAnimation(false);
                break;
        }
//        return super.onTouchEvent(event);
        return true;
    }

    private void zoomInAnimation(boolean handleEvent) {
        if (mHas) {
            this.clearAnimation();
            if (handleEvent) {

                this.startAnimation(mZoomInWithHandleAnimation);
            } else {
                this.startAnimation(mZoomInAnimation);
            }
            mHas = false;

        }
    }

    private void zoomOutAnimation() {
        if (!mHas) {
            this.clearAnimation();
            this.startAnimation(mZoomOutAnimation);
            mHas = true;
        }
    }

    public boolean contains(float x, float y) {
        int left = 0;
        int top = 0;
        int bottom = getHeight();
        int right = getWidth();

        if (left < x && x < right && top < y && y < bottom)
            return true;
        return false;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        if (this.mAnimationFinishedListener != null) {
            this.mAnimationFinishedListener.openAction(this);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public interface AnimationFinishedListener {
        /**
         * 动画结束后打开相应的界面
         * @param view
         */
        public void openAction(View view);
    }

    public static PointF getPointF(View view){
        int left = view.getLeft();
//        int right = view.getRight();
        int top = view.getTop();
//        int bottom = view.getBottom();
//        int centerX = (left+right)/2;
//        int centerY  = (top+bottom)/2;
        return  new PointF(left,top);
    }
    public static PointF getPointF2(View view){
        int left = view.getLeft();
        int right = view.getRight();
        int top = view.getTop();
        int bottom = view.getBottom();
        int centerX = (left+right)/2;
        int centerY  = (top+bottom)/2;
        return  new PointF(centerX,centerY);
    }
}
