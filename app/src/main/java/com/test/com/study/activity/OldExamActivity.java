package com.test.com.study.activity;

import android.view.View;

import com.test.com.R;
import com.test.com.baseUi.BaseToolbarActivity;

public class OldExamActivity extends BaseToolbarActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_old_exam;
    }

    @Override
    protected void initData() {
        initToobar("历年真题");
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {

    }

    @Override
    public void onClick(View v) {

    }
}
