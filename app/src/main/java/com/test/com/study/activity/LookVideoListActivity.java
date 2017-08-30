package com.test.com.study.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.test.com.R;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.study.bean.CurriculumClassificationListBean;
import com.test.com.study.network.RxSchedulersHelper;
import com.test.com.study.network.RxSubscriber;
import com.test.com.study.utils.BaseRecyclerAdapter;
import com.test.com.study.utils.BaseRecyclerHolder;
import com.test.com.study.utils.RecycleViewDivider;
import com.test.com.utills.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LookVideoListActivity extends BaseToolbarActivity {
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.layout_refreshLayout)
    TwinklingRefreshLayout layoutRefreshLayout;
    private List<CurriculumClassificationListBean> curriculumClassificationListBeanList = new ArrayList<>();
    private BaseRecyclerAdapter<CurriculumClassificationListBean> adapter;
    @Override
    protected int getContentView() {
        return R.layout.activity_look_video_list;
    }

    @Override
    protected void initData() {
        initToobar("试看课");
        adapter = new BaseRecyclerAdapter<CurriculumClassificationListBean>(mContext, curriculumClassificationListBeanList, R.layout.item_curriculum_classification_list) {
            @Override
            public void convert(BaseRecyclerHolder holder, CurriculumClassificationListBean item, final int position, boolean isScrolling) {
                sizeUtils.setLayoutSizeHeight(holder.getView(R.id.layout_bg), 90);
                sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_title), 20);
                holder.setText(R.id.tv_title, curriculumClassificationListBeanList.get(position).getName());
            }
        };
        myRecyclerView.setLayoutManager(new LinearLayoutManager(myRecyclerView.getContext()));
        myRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        myRecyclerView.setAdapter(adapter);
        //设置刷新头
        SinaRefreshView headerView = new SinaRefreshView(mContext);
        headerView.setArrowResource(R.mipmap.arrow_down);
        headerView.setTextColor(0xff745D5C);
        layoutRefreshLayout.setHeaderView(headerView);
        //设置刷新尾
        LoadingView loadingView = new LoadingView(mContext);
        layoutRefreshLayout.setBottomView(loadingView);
        layoutRefreshLayout.setEnableLoadmore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutRefreshLayout.startRefresh();
            }
        }, 200);
    }

    @Override
    protected void setListener() {
        layoutRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                getStudyVideoLook();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
            }
        });
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                startActivity(new Intent(mContext, VideoPlayActivity.class).putExtra("url", Constants.ImagHost+ curriculumClassificationListBeanList.get(position).getVideo_src()).putExtra("title",curriculumClassificationListBeanList.get(position).getName()));
            }
        });
    }

    @Override
    protected void initBase() {

    }

    @Override
    public void onClick(View v) {

    }



    //视频列表
    private void getStudyVideoLook() {
        mSubscription = apiService.getStudyVideoLook().compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getIntValue("code") == 1) {
                    curriculumClassificationListBeanList.clear();
                    curriculumClassificationListBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONObject("data").getJSONArray("list").toJSONString(),CurriculumClassificationListBean.class));
                    adapter.notifyDataSetChanged();

                } else
                    ShowToast(jsonObject.getString("msg"));
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                layoutRefreshLayout.finishRefreshing();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                layoutRefreshLayout.finishRefreshing();
            }
        });
    }

}
