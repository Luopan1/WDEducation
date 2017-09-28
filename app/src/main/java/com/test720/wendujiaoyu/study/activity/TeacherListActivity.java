package com.test720.wendujiaoyu.study.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.study.bean.TeacherListBean;
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

public class TeacherListActivity extends BaseToolbarActivity {
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.layout_refreshLayout)
    TwinklingRefreshLayout layoutRefreshLayout;
    private BaseRecyclerAdapter<TeacherListBean> adapter;
    private List<TeacherListBean> teacherListBeanList = new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.activity_teacher_list;
    }

    @Override
    protected void initData() {
        initToobar(getIntent().getStringExtra("title"));
        adapter = new BaseRecyclerAdapter<TeacherListBean>(context, teacherListBeanList, R.layout.item_major_choice) {
            @Override
            public void convert(BaseRecyclerHolder holder, TeacherListBean item, int position, boolean isScrolling) {
//                int tolt = sizeUtils.screenWidth()/2-30;
//                sizeUtils.setLayoutSizeWidht(holder.getView(R.id.layout_bg),tolt);
//                sizeUtils.setLayoutSize(holder.getView(R.id.layout_within),tolt,tolt);
//                sizeUtils.setLayoutSizeHeight(holder.getView(R.id.iv_icon),((int)(tolt/3.5))*2);
                sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_title),20);
                holder.setText(R.id.tv_title,teacherListBeanList.get(position).getName());
                holder.setImageByUrl(R.id.iv_icon, Constants.ImagHost+teacherListBeanList.get(position).getHead());
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
        layoutRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                getStudyVideoTeacher();
            }
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
            }
        });
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                startActivity(new Intent(mContext,CurriculumClassificationListActivity.class).putExtra("cid",teacherListBeanList.get(position).getId()).putExtra("type",getIntent().getStringExtra("type")).putExtra("typeID",getIntent().getStringExtra("title")).putExtra("title",teacherListBeanList.get(position).getName()).putExtra("id",getIntent().getStringExtra("id")));
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
    private void getStudyVideoTeacher()
    {
        mSubscription = apiService.getStudyVideoTeacher(getIntent().getStringExtra("id")).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if(jsonObject.getIntValue("code") == 1)
                {
                    teacherListBeanList.clear();
                    teacherListBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONObject("data").getJSONArray("list").toJSONString(),TeacherListBean.class));
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
