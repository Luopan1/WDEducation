package com.test720.wendujiaoyu.study.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.study.bean.CacheLisstOne;
import com.test720.wendujiaoyu.study.bean.CurriculumClassificationBean;
import com.test720.wendujiaoyu.study.network.RxSchedulersHelper;
import com.test720.wendujiaoyu.study.network.RxSubscriber;
import com.test720.wendujiaoyu.study.utils.BaseRecyclerAdapter;
import com.test720.wendujiaoyu.study.utils.BaseRecyclerHolder;
import com.test720.wendujiaoyu.study.utils.ItemAnimatorFactory;
import com.test720.wendujiaoyu.study.utils.SpacesItemDecoration;
import com.test720.wendujiaoyu.utills.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CurriculumClassificationActivity extends BaseToolbarActivity {

    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.layout_refreshLayout)
    TwinklingRefreshLayout layoutRefreshLayout;
    private BaseRecyclerAdapter<CurriculumClassificationBean> adapter;
    private List<CurriculumClassificationBean> curriculumClassificationBeanList = new ArrayList<>();



    @Override
    protected int getContentView() {
        return R.layout.activity_curriculum_classification;
    }



    private List<CacheLisstOne> getCacheList() {
        String json = spUtils.get("cache", "list", "").toString();
        List<CacheLisstOne> cacheLisstOnes = new ArrayList<>();
        List<CacheLisstOne> list = new ArrayList<>();
        if (!"".equals(json)) {
            cacheLisstOnes = JSONArray.parseArray(json, CacheLisstOne.class);
            for (int i = 0; i < cacheLisstOnes.size(); i++) {
                if (!"".equals(spUtils.get("cache", "list" + cacheLisstOnes.get(i).getId(), "").toString())) {

                    List<CacheLisstOne> cacheLisstOnes1 = JSONArray.parseArray(spUtils.get("cache", "list" + cacheLisstOnes.get(i).getId(), "").toString(), CacheLisstOne.class);
                    for (int i1 = 0; i1 < cacheLisstOnes1.size(); i1++) {
                        CacheLisstOne cacheLisstOne = cacheLisstOnes1.get(i1);
                        cacheLisstOne.setType(Integer.parseInt(cacheLisstOnes.get(i).getId()));
                        list.add(cacheLisstOne);
                    }
                }
            }
            cacheLisstOnes.addAll(list);
            return cacheLisstOnes;
        }
        else {
            return cacheLisstOnes;
        }
    }


    @Override
    protected void initData() {
        initToobar(getIntent().getStringExtra("name"));
        List<CacheLisstOne> cacheLisstOnes = getCacheList();
        Log.e("===","==="+cacheLisstOnes.size());
        for (int i = 0; i < cacheLisstOnes.size(); i++) {
            Log.e("====","===="+cacheLisstOnes.get(i).toString());
        }

        adapter = new BaseRecyclerAdapter<CurriculumClassificationBean>(context, curriculumClassificationBeanList, R.layout.item_major_choice) {
            @Override
            public void convert(BaseRecyclerHolder holder, CurriculumClassificationBean item, int position, boolean isScrolling) {
//                int tolt = sizeUtils.screenWidth()/2-30;
//                sizeUtils.setLayoutSizeWidht(holder.getView(R.id.layout_bg),tolt);
//                sizeUtils.setLayoutSize(holder.getView(R.id.layout_within),tolt,tolt);
//                sizeUtils.setLayoutSizeHeight(holder.getView(R.id.iv_icon),((int)(tolt/3.5))*2);
                sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_title),20);
                holder.setText(R.id.tv_title,curriculumClassificationBeanList.get(position).getName());
                holder.setImageByUrl(R.id.iv_icon, Constants.ImagHost+curriculumClassificationBeanList.get(position).getImg());
            }

        };
        myRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false));
        myRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein());
        myRecyclerView.addItemDecoration(new SpacesItemDecoration(2, 0, 0, 7, 3));
        myRecyclerView.setAdapter(adapter);
        //设置刷新头
        SinaRefreshView headerView = new SinaRefreshView(mContext);
        headerView.setArrowResource(R.mipmap.arrow_down);
        headerView.setTextColor(0xff745D5C);
        layoutRefreshLayout.setHeaderView(headerView);
        layoutRefreshLayout.setEnableLoadmore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                layoutRefreshLayout.startRefresh();
            }
        },200);


    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {

                if("1".equals(getIntent().getStringExtra("type")) || "2".equals(getIntent().getStringExtra("type")))
                    startActivity(new Intent(mContext,TeacherListActivity.class).putExtra("id",curriculumClassificationBeanList.get(position).getId()).putExtra("type",getIntent().getStringExtra("type")).putExtra("typeID",getIntent().getStringExtra("title")).putExtra("title",curriculumClassificationBeanList.get(position).getName()));
                else
                    startActivity(new Intent(mContext,CurriculumClassificationListActivity.class).putExtra("id",curriculumClassificationBeanList.get(position).getId()).putExtra("type",getIntent().getStringExtra("type")).putExtra("typeID",getIntent().getStringExtra("title")).putExtra("title",curriculumClassificationBeanList.get(position).getName()));

            }
        });
        layoutRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                if("1".equals(getIntent().getStringExtra("type")))
                    getStudyVideoIndex();
                else if("2".equals(getIntent().getStringExtra("type")))
                    getStudyVoiceIndex();
                else if("3".equals(getIntent().getStringExtra("type")))
                    getStudyHandoutIndex();
                else if("4".equals(getIntent().getStringExtra("type")))
                    getStudyQuestionsIndex();
                else if("5".equals(getIntent().getStringExtra("type")))
                    getStudyCollectionIndex();
                else if("6".equals(getIntent().getStringExtra("type")))
                    getStudyErrorIndex();
            }
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
            }
        });

    }

    @Override
    protected void initBase() {

    }

    @Override
    public void onClick(View v) {

    }


    //视频课程
    private void getStudyVideoIndex()
    {
        mSubscription = apiService.getStudyVideoIndex(getIntent().getStringExtra("id")).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if(jsonObject.getIntValue("code") == 1)
                {
                    curriculumClassificationBeanList.clear();
                    curriculumClassificationBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(),CurriculumClassificationBean.class));
                    adapter.notifyDataSetChanged();
                }
                else
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
    //音频课程
    private void getStudyVoiceIndex()
    {
        mSubscription = apiService.getStudyVoiceIndex(getIntent().getStringExtra("id")).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if(jsonObject.getIntValue("code") == 1)
                {
                    curriculumClassificationBeanList.clear();
                    curriculumClassificationBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(),CurriculumClassificationBean.class));
                    adapter.notifyDataSetChanged();
                }
                else
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
    //讲义课程
    private void getStudyHandoutIndex()
    {
        mSubscription = apiService.getStudyHandoutIndex(getIntent().getStringExtra("id")).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if(jsonObject.getIntValue("code") == 1)
                {
                    curriculumClassificationBeanList.clear();
                    curriculumClassificationBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(),CurriculumClassificationBean.class));
                    adapter.notifyDataSetChanged();
                }
                else
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
    //题库课程
    private void getStudyQuestionsIndex()
    {
        mSubscription = apiService.getStudyQuestionsIndex(getIntent().getStringExtra("id")).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if(jsonObject.getIntValue("code") == 1)
                {
                    curriculumClassificationBeanList.clear();
                    curriculumClassificationBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(),CurriculumClassificationBean.class));
                    adapter.notifyDataSetChanged();
                }
                else
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
    //收藏课程
    private void getStudyCollectionIndex()
    {
        mSubscription = apiService.getStudyCollectionIndex(getIntent().getStringExtra("id"), Constants.uid).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if(jsonObject.getIntValue("code") == 1)
                {
                    curriculumClassificationBeanList.clear();
                    curriculumClassificationBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(),CurriculumClassificationBean.class));
                    adapter.notifyDataSetChanged();
                }
                else
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

    //错题集
    private void getStudyErrorIndex()
    {
        mSubscription = apiService.getStudyErrorIndex(getIntent().getStringExtra("id"), Constants.uid).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if(jsonObject.getIntValue("code") == 1)
                {
                    curriculumClassificationBeanList.clear();
                    curriculumClassificationBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONArray("data").toJSONString(),CurriculumClassificationBean.class));
                    adapter.notifyDataSetChanged();
                }
                else
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
