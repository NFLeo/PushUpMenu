package com.peter.pushupmenu.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.peter.pushupmenu.R;


/**
 * Created by jiangbin on 15/4/13.
 */
public class PlusStudentLayout extends BasePlusLayout implements ScalableTextView.AnimationFinishedListener {
    private ScalableTextView mShareScalableTextView;
    private ScalableTextView mContactScalableTextView;
    private ScalableTextView mLeaveScalableTextView;
    private ScalableTextView mGroupScalableTextView;


    public PlusStudentLayout(Context context) {
        super(context);
    }


    public void fillViews(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.plus_layout_student, this, true
        );
        mShareScalableTextView = (ScalableTextView) findViewById(R.id.plus_share);
        mContactScalableTextView = (ScalableTextView) findViewById(R.id.plus_contact);
        mLeaveScalableTextView = (ScalableTextView) findViewById(R.id.plus_leave);
        mGroupScalableTextView = (ScalableTextView) findViewById(R.id.plus_group);

        mViews.add(mShareScalableTextView);
        mViews.add(mGroupScalableTextView);
        mViews.add(mContactScalableTextView);
        mViews.add(mLeaveScalableTextView);

        mShareScalableTextView.setAnimationFinishedListener(this);
        mContactScalableTextView.setAnimationFinishedListener(this);
        mLeaveScalableTextView.setAnimationFinishedListener(this);
        mGroupScalableTextView.setAnimationFinishedListener(this);
    }


    @Override

    public void openAction(View view) {
        super.openAction(view);
        int id = view.getId();
        switch (id) {
            case R.id.plus_share:
//                UiHelper.startSendCircleActivity(getContext());
                break;
            case R.id.plus_contact:
//                UiHelper.startContactActivity(getContext());
                break;
            case R.id.plus_leave:
//                UiHelper.startLeaveApplyActivity(getContext());
                break;
            case R.id.plus_group:
//                UiHelper.startGroupTalkCrossingActivity(getContext());
                break;
        }
    }





}
