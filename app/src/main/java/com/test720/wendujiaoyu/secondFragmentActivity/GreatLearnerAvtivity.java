package com.test720.wendujiaoyu.secondFragmentActivity;

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
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.UrlFactory;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.entity.GreateLearner;

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
        showLoadingDialog();
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
        initToobar("本期优秀学员", R.mipmap.fanhui);
        mQuickAdapter = new QuickAdapter();
        greateLearnerRevylerView.setLayoutManager(new GridLayoutManager(this, 3));
    }

    public class QuickAdapter extends BaseQuickAdapter<GreateLearner.DataBean.ListBean, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_greatelearenr, mListBeen);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, GreateLearner.DataBean.ListBean item) {
            if (item.getSex().equals("0")) {
                Glide.with(GreatLearnerAvtivity.this).load(R.mipmap.nanhai).asBitmap().placeholder(R.mipmap.nanhai).error(R.mipmap.nanhai).into((ImageView) viewHolder.getView(R.id.image));
            } else if (item.getSex().trim().equals("1")) {
                Glide.with(GreatLearnerAvtivity.this).load(R.mipmap.nivhai).asBitmap().placeholder(R.mipmap.nivhai).error(R.mipmap.nivhai).into((ImageView) viewHolder.getView(R.id.image));
            }
            viewHolder.setText(R.id.name, item.getName());
            viewHolder.setText(R.id.major, item.getMajor());

        }
    }
}
