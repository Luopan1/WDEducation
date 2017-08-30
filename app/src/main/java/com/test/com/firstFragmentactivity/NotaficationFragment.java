package com.test.com.firstFragmentactivity;

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
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.WebViewActivity;
import com.test.com.baseUi.BaseFragment;
import com.test.com.entity.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoPan on 2017/8/8 11:46.
 */

public class NotaficationFragment extends BaseFragment {
    private List<News> mLists;
    private RecyclerView mListView;
    /**
     * 当期页数
     */
    private int mCurrentPage = 1;

    /**
     * 总页数
     */

    private static int mTotalPage = 0;
    private QuickAdapter mAdapter;

    @Override
    protected void initView() {
        mListView = getView(R.id.listview);
        mListView.setLayoutManager(new LinearLayoutManager(getActivity()));


    }

    private void setAdapter() {

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.URL, UrlFactory.activityInfo + "/activityId/" + mLists.get(position).getActivity_id());
                bundle.putString(WebViewActivity.TITLE, "通知");
                jumpToActivity(WebViewActivity.class, bundle, false);
            }
        });

        mListView.setAdapter(mAdapter);
        /** 上拉加载更多*/
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                getData();
            }
        });
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();
        getData();

    }

    public void getData() {
        RequestParams params=new RequestParams();
        params.put("type", 2);
        params.put("page", mCurrentPage);
        getDataFromInternet(UrlFactory.activityList,params,0);
        showLoadingDialog("加载中");

    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        if (object.getInteger("code").equals(1)) {
            mAdapter = new QuickAdapter();
            JSONObject date = object.getJSONObject("data");
            JSONArray list = date.getJSONArray("list");
            mTotalPage = date.getInteger("total");
            mLists.addAll(list.toJavaList(News.class));
            mAdapter.loadMoreComplete();
            mAdapter.notifyDataSetChanged();
            setAdapter();
            mCurrentPage++;
            if (mCurrentPage > mTotalPage) {
                mAdapter.loadMoreEnd();
            }
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.notificationfragment_activity;
    }

    public class QuickAdapter extends BaseQuickAdapter<News, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_newslistview, mLists);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, News item) {
            viewHolder.setText(R.id.time, item.getAdd_time());
            viewHolder.setText(R.id.title, item.getTitle());
            viewHolder.setText(R.id.descriptio, item.getDescribe());
            Glide.with(getActivity()).load(item.getCover_img()).into((ImageView) viewHolder.getView(R.id.imageView));
        }
    }

}
