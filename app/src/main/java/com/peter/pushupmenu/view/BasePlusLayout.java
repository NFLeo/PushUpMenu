package com.peter.pushupmenu.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringChain;
import com.facebook.rebound.SpringUtil;
import com.peter.pushupmenu.R;
import com.peter.pushupmenu.view.algorithm.Line;

import java.util.ArrayList;

/**
 * Plus 弹出界面的基类,本应用中三种角色 点击+号展示的内容都不一样
 * <p/>
 * 本类实现的功能主要有弹出效果
 * <p/>
 * Created by jiangbin on 15/4/14.
 */
public abstract class BasePlusLayout extends LinearLayout implements ScalableTextView.AnimationFinishedListener {

    protected SpringChain mSpringChain;
    private int mMT = 196;
    private int mMF = 8;
    private int mAT = 112;
    private int mAF = 10;

    /**
     * 是否拦截事件
     */
    private boolean mInterceptor = false;

    protected HideListener mHideListener;


    protected View mRootView = null;
    private boolean mInit = false;
    protected MainView mainViewNew;

    protected ArrayList<View> mViews = new ArrayList<View>();
    protected ArrayList<Line> mLines = new ArrayList<Line>();

    private Drawable mBackgroundDrawable;
    protected ValueAnimator mInValueAnimator;
    protected ValueAnimator mOutValueAnimator;

    private boolean mShouldClose = true;


    public BasePlusLayout(Context context) {
        super(context);

        fillViews(context);
        mBackgroundDrawable = getResources().getDrawable(R.mipmap.plus_background);
        setBackgroundDrawable(mBackgroundDrawable);
        mInValueAnimator = ValueAnimator.ofInt(0, 255);
        mInValueAnimator.setDuration(400);
        mInValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                mBackgroundDrawable.setAlpha(value);
            }
        });
        mOutValueAnimator = ValueAnimator.ofInt(255, 0);
        mOutValueAnimator.setDuration(400);
        mOutValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                mBackgroundDrawable.setAlpha(value);
            }
        });
    }

    protected abstract void fillViews(Context context);


    public void setMainView(MainView mainView) {
        this.mainViewNew = mainView;
    }

    public void setHideListener(HideListener hideListener) {
        this.mHideListener = hideListener;
    }


    public void startShowAnimation() {
        if (!mInit) {
            mInit = true;
            initModel();
        }
        mInterceptor=true;

        mSpringChain = SpringChain.create(mMT, mMF, mAT, mAF);
        int size = mViews.size();
        for (int i = 0; i < size; i++) {
            mSpringChain.addSpring(new MySimpleSpringListener(mLines.get(i), mViews.get(i), true, i == size - 1, i == 0));
        }
        mInValueAnimator.start();
        mSpringChain
                .setControlSpringIndex(0)
                .getControlSpring()
                .setEndValue(1);
    }


    public void startHideAnimation() {
        mInterceptor=true;
        mOutValueAnimator.start();
        mSpringChain = SpringChain.create(mMT, mMF, mAT, mAF);
        int size = mViews.size();
        for (int i = 0; i < size; i++) {
            mSpringChain.addSpring(new MySimpleSpringListener(mLines.get(i), mViews.get(i), false, i == size - 1, i == 0));
        }
        mSpringChain
                .setControlSpringIndex(0)
                .getControlSpring()
                .setEndValue(1);
    }

    private void initModel() {
        PointF centerPointF = mainViewNew.getPointF();
        for (View view : mViews) {
            PointF sharePointF = ScalableTextView.getPointF2(view);
            Line mShareLine = new Line(centerPointF, sharePointF);
            mLines.add(mShareLine);
        }
    }

    public interface HideListener {
        /**
         * 当关闭的动画结束后 应该将主界面显示出来
         */
        void onHideAnimationEnd();

        /**
         * 当打开的动画结束后该干嘛
         */
        void onShowAnimationEnd();

        /**
         * 关闭+号弹出的页面。
         *
         * @param animate 关闭的时候是否需要做动画
         */
        void hide(boolean animate);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**
         * 在做动画的时候 事件是被拦截掉得
         */
        if (mInterceptor) return true;
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            return true;
        }

        /**
         * 碰到up事件 主动关闭当前页面
         */

        if (action == MotionEvent.ACTION_UP) {
            if (mHideListener != null&&mShouldClose) {
                Log.d("jiangbin","setInterceptor ontouchevent "+mInterceptor);
                mHideListener.hide(true);
            }
            return false;
        }
        return false;

    }

    @Override
    public void openAction(View view) {
        if (mHideListener != null) {
            mInterceptor=true;
            mHideListener.hide(false);
        }
    }

    public boolean getInterceptor() {
        return this.mInterceptor;
    }


    protected class MySimpleSpringListener extends SimpleSpringListener {
        private Line mLine;
        private View mView;
        private boolean mOpen;//打开还是关闭
        private boolean mLast;
        private boolean mFirst;

        public MySimpleSpringListener(Line line, View view, boolean reverse, boolean last, boolean first) {
            this.mLine = line;
            this.mView = view;
            this.mOpen = reverse;
            this.mLast = last;
            this.mFirst = first;
        }

        @Override
        public void onSpringUpdate(Spring spring) {
            super.onSpringUpdate(spring);
            double currentValue = spring.getCurrentValue();

            if (currentValue > 0.9 && !mOpen) {
                this.mView.setAlpha(0);
                mShouldClose =false;
            } else {
                this.mView.setAlpha(1.0f);
                mShouldClose =true;
            }

            if (mLine.mStartPointF.x != mLine.mEndPointF.x) {
                double x;

                if (mOpen) {
                    x = SpringUtil.mapValueFromRangeToRange(currentValue, 0, 1, mLine.mStartPointF.x, mLine.mEndPointF.x);

                } else {
                    x = SpringUtil.mapValueFromRangeToRange(1 - currentValue, 0, 1, mLine.mStartPointF.x, mLine.mEndPointF.x);

                }
                float y = mLine.getY((float) x);
                mView.setX((float) x - mView.getWidth() / 2);
                mView.setY(y - mView.getHeight() / 2);
            } else {
                float y;

                if (mOpen) {
                    y = (float) SpringUtil.mapValueFromRangeToRange(currentValue, 0, 1, mLine.mStartPointF.y, mLine.mEndPointF.y);

                } else {
                    y = (float) SpringUtil.mapValueFromRangeToRange(1 - currentValue, 0, 1, mLine.mStartPointF.y, mLine.mEndPointF.y);

                }
                mView.setX(mLine.mStartPointF.x - mView.getWidth() / 2);
                mView.setY(y - mView.getHeight() / 2);
            }

        }

        @Override
        public void onSpringActivate(Spring spring) {
            super.onSpringActivate(spring);
        }

        @Override
        public void onSpringAtRest(Spring spring) {
            if (mHideListener != null) {
                if (mOpen) {
                    if (mLast) {
                        mHideListener.onShowAnimationEnd();
                    }
                } else {
                    if (mFirst) {
//                        setVisibility(View.INVISIBLE);
                        ((View)getParent()).setVisibility(View.INVISIBLE);
                        mHideListener.onHideAnimationEnd();
                    }
                }
            }
        }
    }

    public void setInterceptor(boolean interceptor) {
        this.mInterceptor=interceptor;
    }
}
