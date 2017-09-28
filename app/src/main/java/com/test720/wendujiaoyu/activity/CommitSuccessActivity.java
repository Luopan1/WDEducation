package com.test720.wendujiaoyu.activity;

import android.view.View;

import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;

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
