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

public class ActivityFragment extends BaseFragment {
    private List<News> mLists = new ArrayList<>();
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
        mAdapter = new QuickAdapter();
        mListView.setAdapter(mAdapter);
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
                bundle.putString(WebViewActivity.URL, UrlFactory.activityInfo + "/activityId/" + mLists.get(position).getActivity_id());
                bundle.putString(WebViewActivity.TITLE, "活动");
                jumpToActivity(WebViewActivity.class, bundle, false);
            }
        });
    }

    @Override
    protected void initData() {
        showLoadingDialog("加载中");
        setAdapter();
        getData();

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_activity;
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

    public void getData() {

        RequestParams params = new RequestParams();
        params.put("type", 1);
        params.put("page", mCurrentPage);
        getDataFromInternet(UrlFactory.activityList, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        if (object.getInteger("code").equals(1)) {
            JSONObject data = object.getJSONObject("data");
            JSONArray array = data.getJSONArray("list");
            mLists.addAll(array.toJavaList(News.class));
            mTotalPage = data.getInteger("total");
            mAdapter.loadMoreComplete();
            mAdapter.notifyDataSetChanged();
            mCurrentPage++;
            if (mCurrentPage > mTotalPage) {
                mAdapter.loadMoreEnd();
            }

        }
    }
}
