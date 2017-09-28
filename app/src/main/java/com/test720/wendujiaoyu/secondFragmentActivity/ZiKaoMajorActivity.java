package com.test720.wendujiaoyu.secondFragmentActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.loopj.android.http.RequestParams;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.UrlFactory;
import com.test720.wendujiaoyu.WebViewActivity;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.entity.InfoMation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZiKaoMajorActivity extends BaseToolbarActivity {
    @BindView(R.id.ZIKaoMajorListview)
    RecyclerView ZIKaoMajorListview;
    List<InfoMation> mLists;
    private QuickAdapter mAdapter;
    /**
     * 当期页数
     */
    private int mCurrentPage = 1;

    /**
     * 总页数
     */

    private static int mTotalPage = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_zi_kao_major;
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();
        getData();
        showLoadingDialog();
    }

    private void setAdapter() {
        ZIKaoMajorListview.setAdapter(mAdapter);
        /** 上拉加载更多*/
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.TITLE, "自考专业");
                bundle.putString(WebViewActivity.URL, UrlFactory.imformationInfo + "/id/" + mLists.get(position).getId());
                jumpToActivity(WebViewActivity.class, bundle, false);
            }
        });
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
        initToobar("自考专业", R.mipmap.fanhui);
        ZIKaoMajorListview.setLayoutManager(new LinearLayoutManager(this));
    }

    public void getData() {
        RequestParams params=new RequestParams();
        params.put("type", 2);
        params.put("page", mCurrentPage);
        getDataFromInternet(UrlFactory.informationLists,params,0);

    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        if (object.getInteger("code").equals(1)) {
            mAdapter = new QuickAdapter();
            JSONObject date = object.getJSONObject("data");
            JSONArray list = date.getJSONArray("list");
            mTotalPage = date.getInteger("total");
            mLists.addAll(list.toJavaList(InfoMation.class));
            mAdapter.loadMoreComplete();
            mAdapter.notifyDataSetChanged();
            mCurrentPage++;
            setAdapter();
            if (mCurrentPage > mTotalPage) {
                mAdapter.loadMoreEnd();
            }
        }
    }

    public class QuickAdapter extends BaseQuickAdapter<InfoMation, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_zikaozixun, mLists);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, InfoMation item) {

            viewHolder.setText(R.id.Title, item.getZikao_title());
            viewHolder.setText(R.id.sendTime, item.getTime());
            Glide.with(ZiKaoMajorActivity.this).load(UrlFactory.baseUrl+item.getImg()).into((ImageView) viewHolder.getView(R.id.newImage));
        }
    }
}
