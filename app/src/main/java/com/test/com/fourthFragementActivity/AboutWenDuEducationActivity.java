package com.test.com.fourthFragementActivity;

import android.view.View;

import com.test.com.R;
import com.test.com.baseUi.BaseToolbarActivity;

public class AboutWenDuEducationActivity extends BaseToolbarActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_about_wen_du_education;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initView() {
        initToobar("关于文都",R.mipmap.fanhui);
    }
}
