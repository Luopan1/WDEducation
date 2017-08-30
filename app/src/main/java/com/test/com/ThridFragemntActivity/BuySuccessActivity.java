package com.test.com.ThridFragemntActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.test.com.R;
import com.test.com.baseUi.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class BuySuccessActivity extends BaseToolbarActivity {


    @BindView(R.id.text)
    TextView mText;
    @BindView(R.id.confirm)
    Button mConfirm;

    @Override
    protected int getContentView() {
        return R.layout.activity_buy_success;
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
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        initToobar("缴费成功", R.mipmap.fanhui);
    }

    @Override
    public void onClick(View v) {

    }


    @OnClick(R.id.confirm)
    public void onClick() {
        finish();
    }
}
