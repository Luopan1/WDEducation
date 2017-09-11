package com.test.com.fourthFragementActivity;

import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.adapter.CommonAdaper;
import com.test.com.adapter.ViewHolder;
import com.test.com.application.MyApplication;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.entity.DaiJinQuanEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class DaiJinQuanActivity extends BaseToolbarActivity {
    @BindView(R.id.DaijinquanListView)
    ListView daijinqQuanListView;
    private List<DaiJinQuanEntity.DataBean> mLisits = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_dai_jin_quan;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.add("uuid", MyApplication.getDataBase().getUuid());
        getDataFromInternet(UrlFactory.couponList, params, 0);
        showLoadingDialog();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        ShowToast(object.getString("msg"));
        if (object.getInteger("code") == 1) {
            Gson gson = new Gson();
            DaiJinQuanEntity daiJinQuanEntity = gson.fromJson(object.toString(), DaiJinQuanEntity.class);
            mLisits.addAll(daiJinQuanEntity.getData());
            setAdapter();
        }
    }

    private void setAdapter() {
        CommonAdaper adapter = new CommonAdaper<DaiJinQuanEntity.DataBean>(this, mLisits, R.layout.item_daijinquanlistview) {

            @Override
            public void convert(ViewHolder holder, DaiJinQuanEntity.DataBean item, int position) {
                holder.setText(R.id.daiJinQuanName, item.getCoupon_name());
                holder.setText(R.id.Money, item.getMoney());
                holder.setText(R.id.useMethod, item.getCode());
                holder.setText(R.id.DeadLine, "有效期:"+item.getStart_time()+"-" + item.getEnd_time());
            }
        };

        daijinqQuanListView.setAdapter(adapter);
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
        initToobar("代金券","使用规则", R.mipmap.fanhui);
    }

    @Override
    public void RightOnClick() {
        super.RightOnClick();
        jumpToActivity(DaiJinGuiZeActivity.class,false);
    }
}
