package com.test.com.firstFragmentactivity;

import android.view.View;

import com.test.com.R;
import com.test.com.baseUi.BaseToolbarActivity;

public class CheckGradeActivity extends BaseToolbarActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_check_grade;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initView() {
        initToobar("成绩查询",R.mipmap.fanhui);
    }

    @Override
    protected void initBase() {
        isshowActionbar=true;
    }
}
