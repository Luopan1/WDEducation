package com.test.com.firstFragmentactivity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

import com.flyco.tablayout.SegmentTabLayout;
import com.test.com.R;
import com.test.com.baseUi.BaseToolbarActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class NewsActivity1 extends BaseToolbarActivity {
    private String[] mTitles = {"活动", "通知"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    @BindView(R.id.fanhuiRelative)
    RelativeLayout fanhuiRelatiove;

    @Override
    protected int getContentView() {
        return R.layout.activity_news1;
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void setListener() {
        fanhuiRelatiove.setOnClickListener(this);
    }

    @Override
    protected void initBase() {
        isShowToolBar = false;
        isshowActionbar = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fanhuiRelative:
                finish();
                break;
        }
    }

    @Override
    protected void initView() {
        SegmentTabLayout tabLayout = (SegmentTabLayout) findViewById(R.id.tl_1);
        mFragments.add(new ActivityFragment());
        mFragments.add(new NotaficationFragment());
        tabLayout.setTabData(mTitles, this, R.id.contentRelative, mFragments);
        tabLayout.setCurrentTab(0);

    }


}
