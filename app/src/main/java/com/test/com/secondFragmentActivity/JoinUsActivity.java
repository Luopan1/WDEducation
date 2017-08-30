package com.test.com.secondFragmentActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.WebViewActivity;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.entity.InfoMation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class JoinUsActivity extends BaseToolbarActivity {
    @BindView(R.id.joinUsListView)
    RecyclerView joinUsListView;
    private List<InfoMation> mLists = new ArrayList<>();
    private int mCurrentPage = 1;
    private int mTotalPage;
    private QuickAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_join_us;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.put("type", 5);
        params.put("page", mCurrentPage);
        getDataFromInternet(UrlFactory.informationLists, params, 0);


    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        if (object.getInteger("code").equals(1)) {
            JSONObject date = object.getJSONObject("data");
            JSONArray list = date.getJSONArray("list");
            Log.e("TAG+++", object.toString());
            mTotalPage = date.getInteger("total");
            Log.e("TAG++mTotalPage", mTotalPage + "");
            mLists.addAll(list.toJavaList(InfoMation.class));
            mAdapter.loadMoreComplete();
            mCurrentPage++;
            mAdapter.setNewData(mLists);
            if (mCurrentPage > mTotalPage) {
                mAdapter.loadMoreEnd();
            }
        }

        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initData();
            }
        });

    }

    private void setAdapter() {
        joinUsListView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.TITLE, "加入我们");
                bundle.putString(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + mLists.get(position).getId());
                jumpToActivity(WebViewActivity.class, bundle, false);
            }
        });
    }

    public class QuickAdapter extends BaseQuickAdapter<InfoMation, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_zikaozixun, mLists);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, InfoMation item) {

            viewHolder.setText(R.id.Title, item.getZikao_title());
            viewHolder.setText(R.id.sendTime, item.getTime());
            Glide.with(JoinUsActivity.this).load(item.getImg()).into((ImageView) viewHolder.getView(R.id.newImage));
        }
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
        mAdapter = new QuickAdapter();
        setAdapter();
        initToobar("加入我们", R.mipmap.fanhui);
        joinUsListView.setLayoutManager(new LinearLayoutManager(this));
    }
}
