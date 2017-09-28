package com.test720.wendujiaoyu.firstFragmentactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.UrlFactory;
import com.test720.wendujiaoyu.WebViewActivity;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.utills.NetImageLoaderHolder;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class CheckActivity extends BaseToolbarActivity {
    @BindView(R.id.convenientBaner)
    ConvenientBanner mConvenientBanner;
    @BindView(R.id.checkGradeRelative)
    RelativeLayout checkGradeRelative;
    @BindView(R.id.checkZuoWeiRelative)
    RelativeLayout checkZuoWeiRelative;
    @BindView(R.id.planRelative)
    RelativeLayout planRelative;
    @BindView(R.id.majorRelative)
    RelativeLayout majorRelative;
    private int[] dots = {R.mipmap.circle1, R.mipmap.circle2};
    private String[] images = {
            "http://pic2.ooopic.com/11/77/47/63b1OOOPIC74.jpg"};


    @Override
    protected int getContentView() {
        return R.layout.activity_check;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        checkGradeRelative.setOnClickListener(this);
        checkZuoWeiRelative.setOnClickListener(this);
        planRelative.setOnClickListener(this);
        majorRelative.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkGradeRelative:
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.URL, UrlFactory.scoreFind + "/type/" + 1);
                bundle.putString(WebViewActivity.TITLE, "成绩查询");
                jumpToActivity(WebViewActivity.class, bundle, false);

                break;
            case R.id.checkZuoWeiRelative:
                bundle = new Bundle();
                bundle.putString(WebViewActivity.URL, UrlFactory.scoreFind + "/type/" + 2);
                bundle.putString(WebViewActivity.TITLE, "座位查询");
                jumpToActivity(WebViewActivity.class, bundle, false);
                break;
            case R.id.planRelative:
                jumpToActivity(ExamPlanActivity.class, false);
                break;
            case R.id.majorRelative:
                jumpToActivity(MajorListsActivity.class, false);
                break;

        }
    }

    @Override
    protected void initView() {
        initToobar("查查", R.mipmap.fanhui);

        List<String> imagsList = Arrays.asList(images);//将图片放进这个集合
        mConvenientBanner.setPointViewVisible(false);//设置小圆点可见
        mConvenientBanner.setPageIndicator(dots);//设置小圆点
        mConvenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        mConvenientBanner.setManualPageable(false);//手动滑动
        mConvenientBanner.setCanLoop(false);
        mConvenientBanner.setPages(new CBViewHolderCreator<NetImageLoaderHolder>() {
            @Override
            public NetImageLoaderHolder createHolder() {
                return new NetImageLoaderHolder();
            }
        }, imagsList);
    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
    }
}
