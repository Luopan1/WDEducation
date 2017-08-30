package com.test.com.firstFragmentactivity;

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
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.entity.Question;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AskActivity extends BaseToolbarActivity {
    @BindView(R.id.askListView)
    RecyclerView askListView;
    @BindView(R.id.imageRight)
    ImageView imageRight;
    /**
     * 当期页数
     */
    private int mCurrentPage = 1;

    /**
     * 总页数
     */

    private static int mTotalPage = 0;
    List<Question> lists = new ArrayList<>();
    private QuickAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_ask;
    }

    @Override
    protected void initData() {
        getData();
        setAdapter();
        showLoadingDialog();
    }

    private void setAdapter() {
        mAdapter = new QuickAdapter();
        askListView.setAdapter(mAdapter);
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
                bundle.putString("id", lists.get(position).getId());
                jumpToActivity(AskAndAnwserInfoActivity.class, bundle, false);
            }
        });


    }

    @Override
    protected void setListener() {

        imageRight.setOnClickListener(this);


    }

    @Override
    protected void initBase() {
        isshowActionbar = true;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageRight:
                //跳转到搜索界面
                jumpToActivity(SearchAskActivity.class, false);
                break;
        }

    }

    @Override
    protected void initView() {
        initToobar(R.mipmap.fanhui, "问问", R.mipmap.sousuo);
        askListView.setLayoutManager(new LinearLayoutManager(this));

    }

    public class QuickAdapter extends BaseQuickAdapter<Question, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_asklistview, lists);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Question item) {
            viewHolder.setText(R.id.askTitle, item.getTitle());
            viewHolder.setText(R.id.loveMan, item.getLovemun());
            viewHolder.setText(R.id.ask_anwser,item.getAnswer());
            Glide.with(AskActivity.this).load(item.touxiang).into((ImageView) viewHolder.getView(R.id.anwserPhotoImage));
        }
    }

    public void getData() {
        Log.e("TAG", "当前页" + mCurrentPage);
        RequestParams params = new RequestParams();
        params.put("page", mCurrentPage);
        getDataFromInternet(UrlFactory.answerLists, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
            Log.e("TAG+wenwen",object.toString());
        JSONObject data = object.getJSONObject("data");

        JSONArray array = data.getJSONArray("list");
        lists.addAll(array.toJavaList(Question.class));
        mTotalPage = data.getInteger("total");
        mAdapter.loadMoreComplete();
        mAdapter.setNewData(lists);
        mCurrentPage++;

        if (mCurrentPage > mTotalPage) {
            mAdapter.loadMoreEnd();
        }
    }
}
