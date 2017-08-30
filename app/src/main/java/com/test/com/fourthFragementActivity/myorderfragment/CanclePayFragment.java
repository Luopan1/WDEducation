package com.test.com.fourthFragementActivity.myorderfragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.ThridFragemntActivity.ComfirmOrderActivity;
import com.test.com.UrlFactory;
import com.test.com.baseUi.BaseFragment;
import com.test.com.entity.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoPan on 2017/8/23 16:59.
 */

public class CanclePayFragment extends BaseFragment {

    private RecyclerView mAllOrderRecylerView;
    List<Order.DataBean> mStringList = new ArrayList<>();
    private QuickAdapter mAdapter;

    public CanclePayFragment newInstance() {
        CanclePayFragment fragment = new CanclePayFragment();
        return fragment;
    }


    @Override
    protected void initView() {
        mAllOrderRecylerView = getView(R.id.AllOrderRecyclerView);
        mAllOrderRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.put("type", "2");
        getDataFromInternet(UrlFactory.allpay, params, 0);
        showLoadingDialog(" ");
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        Log.e("TAG+++++", object.toString());
        if (object.getInteger("code") == 1) {
            Gson gson = new Gson();
            Order order = gson.fromJson(object.toString(), Order.class);
            mStringList.addAll(order.getData());
            setAdapter();
        }

    }

    private void setAdapter() {
        mAdapter = new QuickAdapter();
        mAllOrderRecylerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {

    }

    public class QuickAdapter extends BaseQuickAdapter<Order.DataBean, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_myorder, mStringList);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Order.DataBean item) {
            viewHolder.setText(R.id.title, "报考专业：" + item.getBaokao_major() + ":(" + item.getStay_school() + ")");
            viewHolder.setText(R.id.time, item.getTime());
            viewHolder.setText(R.id.totalMoney, "￥" + item.getPrice());

            TextView statue = viewHolder.getView(R.id.statue);
            if (item.getType().equals("0")) {

            } else if (item.getType().equals("1")) {
                statue.setText("已支付");
                TextPaint tp = statue.getPaint();
                tp.setFakeBoldText(true);
                statue.setTextColor(Color.parseColor("#6e6e6e"));
                statue.setBackground(getResources().getDrawable(R.drawable.white_color_corn));
            } else if (item.getType().equals("2")) {
                statue.setText("已取消");
                TextPaint tp = statue.getPaint();
                tp.setFakeBoldText(true);
                statue.setTextColor(Color.parseColor("#6e6e6e"));
                statue.setBackground(getResources().getDrawable(R.drawable.white_color_corn));
            }
            if (statue.getText().toString().trim().equals("支付")) {
                statue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpToActivity(ComfirmOrderActivity.class, false);
                    }
                });

            }

        }
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_allorder;
    }
}
