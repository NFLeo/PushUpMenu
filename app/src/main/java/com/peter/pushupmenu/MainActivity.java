package com.peter.pushupmenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.peter.pushupmenu.view.BasePlusLayout;
import com.peter.pushupmenu.view.MainView;

public class MainActivity extends AppCompatActivity {
    private BasePlusLayout mPlusLayout;
    private LinearLayout mPlusContentLayout;
    private MainView mMainView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainview);
        mPlusContentLayout = (LinearLayout) findViewById(R.id.plus_content);
//        mPlusLayout = mMainView.getPlusLayout();
    }
}
