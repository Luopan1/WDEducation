package com.test.com.activity;

import android.view.View;

import com.test.com.R;
import com.test.com.baseUi.BaseToolbarActivity;

public class CommitSuccessActivity extends BaseToolbarActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_commit_success;
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
        initToobar("提交成功", R.mipmap.fanhui);
    }
}
