//package com.peter.pushupmenu.view;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//
//import com.peter.pushupmenu.R;
//
//
///**
// * Created by jiangbin on 15/4/13.
// */
//public class PlusParentLayout extends BasePlusLayout {
//    private ScalableTextView mContactTextView;
//    private ScalableTextView mLeaveTextView;
//    private ScalableTextView mShareTextView;
//
//    public PlusParentLayout(Context context) {
//        super(context);
//
//    }
//
//
//    @Override
//    protected void fillViews(Context context) {
//        mRootView = LayoutInflater.from(context).inflate(R.layout.plus_layout_parent, this, true);
//        mContactTextView = (ScalableTextView) findViewById(R.id.plus_contact);
//        mLeaveTextView = (ScalableTextView) findViewById(R.id.plus_leave);
//        mShareTextView = (ScalableTextView) findViewById(R.id.plus_share);
//        mViews.add(mShareTextView);
//        mViews.add(mContactTextView);
//        mViews.add(mLeaveTextView);
//        mContactTextView.setAnimationFinishedListener(this);
//        mLeaveTextView.setAnimationFinishedListener(this);
//        mShareTextView.setAnimationFinishedListener(this);
//    }
//
//    @Override
//    public void openAction(View view) {
//        super.openAction(view);
//        int id = view.getId();
////        switch (id) {
////            case R.id.plus_contact:
////                UiHelper.startContactActivity(getContext());
////                break;
////            case R.id.plus_leave:
////                UiHelper.startLeaveApplyActivity(getContext());
////                break;
////            case R.id.plus_share:
////                UiHelper.startSendCircleActivity(getContext());
////
////        }
//    }
//}
