package com.test.com.fourthFragementActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.test.com.R;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.fourthFragementActivity.myorderfragment.AllOrderFragment;
import com.test.com.fourthFragementActivity.myorderfragment.AreadlyPayFragment;
import com.test.com.fourthFragementActivity.myorderfragment.CanclePayFragment;
import com.test.com.fourthFragementActivity.myorderfragment.WatieForPayFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyOrderActivity extends BaseToolbarActivity {

    @BindView(R.id.allOrder)
    RadioButton mAllOrder;
    @BindView(R.id.waite)
    RadioButton mWaite;
    @BindView(R.id.already)
    RadioButton mAlready;
    @BindView(R.id.cancle)
    RadioButton mCancle;
    @BindView(R.id.contentRelative)
    RelativeLayout mContentRelative;
    private Fragment mFragment;
    private FragmentManager mMamager;
    private List<Fragment> mFragments;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isShowToolBar = true;
        isshowActionbar = true;

    }

    @Override
    protected void initView() {
        super.initView();
        initToobar("我的订单", R.mipmap.fanhui);
        mMamager = getSupportFragmentManager();
        FragmentTransaction transaction = mMamager.beginTransaction();

        mFragments = new ArrayList<>();
        mFragments.add(new AllOrderFragment().newInstance());
        mFragments.add(new WatieForPayFragment().newInstance());
        mFragments.add(new AreadlyPayFragment().newInstance());
        mFragments.add(new CanclePayFragment().newInstance());

        mFragment = mFragments.get(0);
        transaction.replace(R.id.contentRelative, mFragments.get(0));
        transaction.commit();
    }

    @OnClick({R.id.allOrder, R.id.waite, R.id.already, R.id.cancle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.allOrder:
                switchContent(mFragment,mFragments.get(0));
                break;
            case R.id.waite:
                switchContent(mFragment,mFragments.get(1));
                break;
            case R.id.already:
                switchContent(mFragment,mFragments.get(2));
                break;
            case R.id.cancle:
                switchContent(mFragment,mFragments.get(3));
                break;

        }
    }


    /**
     * 判断是否被add过
     * add过  隐藏当前的fragment，add下一个到Activity中
     * 否则   隐藏当前的fragment，显示下一个
     */
    public void switchContent(Fragment from, Fragment to) {
        if (mFragment != to) {
            mFragment = to;
            FragmentTransaction transaction = mMamager.beginTransaction();
            if (!to.isAdded()) {
                // 隐藏当前的fragment，add下一个到Activity中
                transaction.hide(from).add(R.id.contentRelative, to).commitAllowingStateLoss();
            } else {
                // 隐藏当前的fragment，显示下一个
                transaction.hide(from).show(to).commitAllowingStateLoss();
            }
        }
    }


}
