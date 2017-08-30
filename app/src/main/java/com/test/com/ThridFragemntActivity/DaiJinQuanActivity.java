package com.test.com.ThridFragemntActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.adapter.CommonAdaper;
import com.test.com.adapter.ViewHolder;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.baseUi.MListView;
import com.test.com.entity.BaoMingDaijinQuan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DaiJinQuanActivity extends BaseToolbarActivity {


    @BindView(R.id.canUseDaiJinQuanListView)
    MListView mCanUseDaiJinQuanListView;
    @BindView(R.id.cantUseDaiJinQuanListView)
    MListView mCantUseDaiJinQuanListView;
    @BindView(R.id.howToGetDaijinquan)
    Button howToGetDaijinquan;

    @BindView(R.id.canUserDaijinquan)
    TextView mCanUserDaijinquan;
    @BindView(R.id.cantuserDaijinquan)
    TextView mCantuserDaijinquan;

    private List<BaoMingDaijinQuan.DataBean.DaijinBean> mCanUseLists;
    private List<BaoMingDaijinQuan.DataBean.DaijinBean> mCantUseLists;

    @Override
    protected int getContentView() {
        return R.layout.activity_dai_jin_quan2;
    }

    @Override
    protected void initData() {

        RequestParams params = new RequestParams();
        params.put("type", 0);
        getDataFromInternet(UrlFactory.RegistrationFee, params, 0);
        showLoadingDialog();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        if (object.getInteger("code") == 1) {
            Log.e("TAG+++++++", object.toString());
            Gson gson = new Gson();
            mCanUseLists = new ArrayList<>();
            mCantUseLists = new ArrayList<>();
            BaoMingDaijinQuan daijinQuan = gson.fromJson(object.toString(), BaoMingDaijinQuan.class);
            List<BaoMingDaijinQuan.DataBean.DaijinBean> datalists = new ArrayList<>();
            datalists.addAll(daijinQuan.getData().getDaijin());

            Log.e("======", datalists.toString());

            for (int i = 0; i < datalists.size(); i++) {
                if (datalists.get(i).getIs_use().equals("0")) {
                    //可用的代金券
                    mCanUseLists.add(datalists.get(i));
                } else if (datalists.get(i).getIs_use().equals("1")) {
                    //不可用的代金券
                    mCantUseLists.add(datalists.get(i));
                }

            }
            Log.e("mCanUseLists", mCanUseLists.toString());
            Log.e("=====", mCantUseLists.toString());
            if (mCanUseLists.size() == 0) {
                mCanUserDaijinquan.setVisibility(View.GONE);
            } else {
                mCanUserDaijinquan.setVisibility(View.VISIBLE);
            }
            if (mCantUseLists.size() == 0) {
                mCantuserDaijinquan.setVisibility(View.GONE);
            } else {
                mCantuserDaijinquan.setVisibility(View.VISIBLE);
            }
            serAdapter();
        }

    }

    private void serAdapter() {
        CommonAdaper canUseAdapter = new CommonAdaper<BaoMingDaijinQuan.DataBean.DaijinBean>(this, mCanUseLists, R.layout.item_daijinquancan_use) {

            @Override
            public void convert(ViewHolder holder, BaoMingDaijinQuan.DataBean.DaijinBean item, int position) {
                holder.setText(R.id.Money, item.getMoney());
                holder.setText(R.id.daiJinQuanName, item.getCoupon_name());
                holder.setText(R.id.useMethod, item.getCode());
                holder.setText(R.id.DeadLine, "有效期:" + item.getStart_time() + "-" + item.getEnd_time());
            }
        };
        mCanUseDaiJinQuanListView.setAdapter(canUseAdapter);
        CommonAdaper cantUseAdapter = new CommonAdaper<BaoMingDaijinQuan.DataBean.DaijinBean>(this, mCantUseLists, R.layout.item_daijinquancant_use) {

            @Override
            public void convert(ViewHolder holder, BaoMingDaijinQuan.DataBean.DaijinBean item, int position) {
                holder.setText(R.id.Money, item.getMoney());
                holder.setText(R.id.daiJinQuanName, item.getCoupon_name());
                holder.setText(R.id.useMethod, item.getCode());
                holder.setText(R.id.DeadLine, "有效期:" + item.getStart_time() + "-" + item.getEnd_time());
            }
        };
        mCantUseDaiJinQuanListView.setAdapter(cantUseAdapter);
    }

    @Override
    protected void setListener() {
        howToGetDaijinquan.setOnClickListener(this);
        mCanUseDaiJinQuanListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("daijinquan", (Serializable) mCanUseLists.get(position));
                setResult(3, intent);
                finish();
            }
        });
    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
        isShowToolBar = true;
    }

    @Override
    protected void initView() {
        super.initView();
        initToobar("代金券", R.mipmap.fanhui);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.howToGetDaijinquan:
                jumpToActivity(HowToGetDaijinquanActivity.class, false);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
