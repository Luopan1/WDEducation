package com.test.com.study.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.test.com.R;
import com.test.com.activity.MainActivity;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.study.bean.MajorChoiceBean;
import com.test.com.study.network.RxSchedulersHelper;
import com.test.com.study.network.RxSubscriber;
import com.test.com.study.utils.BaseRecyclerAdapter;
import com.test.com.study.utils.BaseRecyclerHolder;
import com.test.com.study.utils.ItemAnimatorFactory;
import com.test.com.study.utils.SpacesItemDecoration;
import com.test.com.utills.ActivityUtil;
import com.test.com.utills.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MajorChoiceActivity extends BaseToolbarActivity {

    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.layout_refreshLayout)
    TwinklingRefreshLayout layoutRefreshLayout;
    private BaseRecyclerAdapter<MajorChoiceBean> adapter;
    private List<MajorChoiceBean> majorChoiceBeanList = new ArrayList<>();


    @Override
    protected int getContentView() {
        return R.layout.activity_major_choice;
    }

    @Override
    protected void initData() {
        if("6".equals(getIntent().getStringExtra("type")))initToobar("我的错题");
            else initToobar("我的自考");




        adapter = new BaseRecyclerAdapter<MajorChoiceBean>(context, majorChoiceBeanList, R.layout.item_major_choice) {
            @Override
            public void convert(BaseRecyclerHolder holder, MajorChoiceBean item, int position, boolean isScrolling) {
//                int tolt = sizeUtils.screenWidth()/2-30;
//                sizeUtils.setLayoutSizeWidht(holder.getView(R.id.layout_bg),tolt);
//                sizeUtils.setLayoutSize(holder.getView(R.id.layout_within),tolt,tolt);
//                sizeUtils.setLayoutSizeHeight(holder.getView(R.id.iv_icon),((int)(tolt/3.5))*2);
                sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_title),20);
                holder.setText(R.id.tv_title,majorChoiceBeanList.get(position).getName());
                holder.setImageByUrl(R.id.iv_icon, Constants.ImagHost+majorChoiceBeanList.get(position).getImg());
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
                if("3".equals(majorChoiceBeanList.get(position).getId()))
                    startActivity(new Intent(mContext,LookVideoListActivity.class));
                else
                {
                    if("".equals(spUtils.get("jihuo",Constants.uniqueness+"index","").toString()))
                    {
                        new AlertDialog.Builder(mContext).setTitle("提示")//设置对话框标题
                                .setMessage("该帐号尚未激活，不能使用该功能，是否前往激活？")//设置显示的内容
                                .setPositiveButton("激活", new DialogInterface.OnClickListener() {//添加确定按钮
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                        // TODO Auto-generated method stub
                                        dialog.dismiss();
                                        MainActivity.indexCount = 1;
                                        ActivityUtil.finishActivity(StudyHomeActivity.class);
                                        finish();
                                    }

                                }).setNegativeButton("取消",null).show();//在按键响应事件中显示此对话框
                    }
                    else
                        startActivity(new Intent(mContext,CurriculumClassificationActivity.class).putExtra("id",majorChoiceBeanList.get(position).getId()).putExtra("type",getIntent().getStringExtra("type")).putExtra("title",majorChoiceBeanList.get(position).getId()).putExtra("name",majorChoiceBeanList.get(position).getName()));

                }
            }
        });
        layoutRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                getStudyEntrance();
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


    private void getStudyEntrance()
    {

        String id = getIntent().getStringExtra("type");
        Log.e("======","===="+id);

        mSubscription = apiService.getStudyEntrance(id).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if(jsonObject.getIntValue("code") == 1)
                {
                    majorChoiceBeanList.clear();
                    majorChoiceBeanList.addAll(JSONObject.parseArray(jsonObject.getJSONArray("data").toJSONString(),MajorChoiceBean.class));
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
