package com.test.com.secondFragmentActivity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.entity.GreateLearner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GreatLearnerAvtivity extends BaseToolbarActivity {
    @BindView(R.id.greateLearnerRevylerView)
    RecyclerView greateLearnerRevylerView;
    private int CurrentPage = 1;
    private int TotalPage;
    List<GreateLearner.DataBean.ListBean> mListBeen = new ArrayList<>();
    private QuickAdapter mQuickAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_great_learner_avtivity;
    }

    @Override
    protected void initData() {
        setAdapter();
        RequestParams params = new RequestParams();
        params.put("page", CurrentPage);
        getDataFromInternet(UrlFactory.excellentStudent, params, 0);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        Gson gson = new Gson();
        GreateLearner learner = gson.fromJson(object.toString(), GreateLearner.class);
        TotalPage = learner.getData().getTotal();
        mListBeen.addAll(learner.getData().getList());
        mQuickAdapter.loadMoreComplete();
        CurrentPage++;
        mQuickAdapter.setNewData(mListBeen);
        if (CurrentPage > TotalPage) {
            mQuickAdapter.loadMoreEnd();
        }

        mQuickAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initData();
            }
        });

    }

    private void setAdapter() {

        greateLearnerRevylerView.setAdapter(mQuickAdapter);
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
    public void onClick(View v) {

    }

    @Override
    protected void initView() {
        initToobar("优秀学员", R.mipmap.fanhui);
        mQuickAdapter = new QuickAdapter();
        greateLearnerRevylerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    public class QuickAdapter extends BaseQuickAdapter<GreateLearner.DataBean.ListBean, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_greatelearenr, mListBeen);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, GreateLearner.DataBean.ListBean item) {
            Glide.with(GreatLearnerAvtivity.this).load(UrlFactory.imagePath + item.getHeader()).into((ImageView) viewHolder.getView(R.id.image));
            viewHolder.setText(R.id.name, item.getName());
            viewHolder.setText(R.id.major, item.getMajor());

        }
    }
}
