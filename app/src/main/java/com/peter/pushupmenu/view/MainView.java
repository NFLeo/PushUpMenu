package com.peter.pushupmenu.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.peter.pushupmenu.R;


/**
 * Created by jiangbin on 15/4/13.
 */
public class MainView extends FrameLayout implements View.OnClickListener, BasePlusLayout.HideListener {

    private LinearLayout mTabLinearLayout;
    private ImageButton mPlusButtonInner;
    private boolean mToggleOn = false;
    private BasePlusLayout mPlusLayout;
    private LinearLayout mPlusContentLayout;
    protected ValueAnimator mInValueAnimator;
    protected ValueAnimator mOutValueAnimator;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.activity_main, this, true);
        mTabLinearLayout = (LinearLayout) findViewById(R.id.tab_linearlayout);
        mPlusButtonInner = (ImageButton) findViewById(R.id.plus_button);

        mPlusButtonInner.setOnClickListener(this);
        mInValueAnimator = ValueAnimator.ofFloat(0, 45);
        mInValueAnimator.setDuration(400);
        mInValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                if(value>22){
                    mPlusButtonInner.setImageResource(R.mipmap.tab_icon_add_0);
                }else{
                    mPlusButtonInner.setImageResource(R.mipmap.tab_icon_add_1);

                }
                mPlusButtonInner.setRotation(value);
            }
        });
        mOutValueAnimator = ValueAnimator.ofFloat(45, 0);
        mOutValueAnimator.setDuration(400);
        mOutValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                mPlusButtonInner.setRotation(value);
                if(value>22){
                    mPlusButtonInner.setImageResource(R.mipmap.tab_icon_add_0);
                }else{
                    mPlusButtonInner.setImageResource(R.mipmap.tab_icon_add_1);

                }
            }
        });
//        int mRoleType =Utils.ROLE_STUDENT;
//        switch (mRoleType) {
//            case Utils.ROLE_PARENT:
//                mPlusLayout = new PlusParentLayout(context);
//                break;
//            case Utils.ROLE_STUDENT:
                mPlusLayout = new PlusStudentLayout(context);

//                break;
//            case Utils.ROLE_TEACHER:
//                mPlusLayout = new PlusTeacherLayout(context);
//
//                break;
//        }

        mPlusLayout.setMainView(this);
        mPlusLayout.setHideListener(this);
        mPlusContentLayout = (LinearLayout) findViewById(R.id.plus_content);
        mPlusContentLayout.addView(mPlusLayout,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public void onClick(View v) {
        toggle(true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void toggle(boolean animate) {
        mToggleOn = !mToggleOn;

        mPlusButtonInner.setEnabled(false);
        if (mToggleOn) {
            mInValueAnimator.start();
            this.mPlusContentLayout.setVisibility(View.VISIBLE);
            this.mPlusLayout.startShowAnimation();
        } else {
            mOutValueAnimator.start();
            if (animate) {

                this.mPlusLayout.startHideAnimation();
            } else {
                onHideAnimationEnd();

            }
        }
    }

    public PointF getPointF() {
        PointF pointF = ScalableTextView.getPointF2(mTabLinearLayout);
        int y = getHeight();
        pointF.y = y;
        return pointF;
    }

    @Override
    public void onHideAnimationEnd() {
        mPlusContentLayout.setVisibility(View.INVISIBLE);
        mPlusButtonInner.setEnabled(true);
        mPlusLayout.setInterceptor(false);
    }

    @Override
    public void hide(boolean animate) {
        toggle(animate);
    }

    @Override
    public void onShowAnimationEnd() {
        mPlusButtonInner.setEnabled(true);
        mPlusLayout.setInterceptor(false);

    }

    public BasePlusLayout getPlusLayout() {
        return this.mPlusLayout;
    }
}
