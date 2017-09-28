package com.test720.wendujiaoyu.study.activity;

import android.content.Intent;
import android.net.Uri;
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
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.study.bean.CurriculumClassificationListBean;
import com.test720.wendujiaoyu.study.download.dbcontrol.FileHelper;
import com.test720.wendujiaoyu.study.network.RxSchedulersHelper;
import com.test720.wendujiaoyu.study.network.RxSubscriber;
import com.test720.wendujiaoyu.study.utils.BaseRecyclerAdapter;
import com.test720.wendujiaoyu.study.utils.BaseRecyclerHolder;
import com.test720.wendujiaoyu.study.utils.RecycleViewDivider;
import com.test720.wendujiaoyu.utills.Constants;

import java.io.File;
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
        initToobar(getIntent().getStringExtra("title"));
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
                if("1".equals(getIntent().getStringExtra("type")))
                    getStudyVideoLook();
                else
                    getStudyHandout();

            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
            }
        });
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                if("1".equals(getIntent().getStringExtra("type")))
                    startActivity(new Intent(mContext, VideoPlayActivity.class).putExtra("url", Constants.ImagHost+ curriculumClassificationListBeanList.get(position).getVideo_src()).putExtra("title",curriculumClassificationListBeanList.get(position).getName()));
                else
                {
                    String url = curriculumClassificationListBeanList.get(position).getHandout_src();
                    String prefix = url.substring(url.lastIndexOf("."));
                    String src = FileHelper.getFileDefaultPath() + "/" + curriculumClassificationListBeanList.get(position).getName() + prefix;
                    if (fileIsExists(src)) {
                        if(!prefix.equals(".pdf"))
                        {
                            startActivity(getWordFileIntent(src));
                        }
                        else
                        {

                            startActivity(getPDFFileIntent(src));

//                            startActivity(new Intent(mContext,FileSeeActivity.class).putExtra("title",curriculumClassificationListBeanList.get(position).getName()).putExtra("src",curriculumClassificationListBeanList.get(position).getHandout_src()).putExtra("id",curriculumClassificationListBeanList.get(position).getId()));
                        }
                    }
                    else
                    {
                        startActivity(new Intent(mContext,FileSeeActivity.class).putExtra("title",curriculumClassificationListBeanList.get(position).getName()).putExtra("src",curriculumClassificationListBeanList.get(position).getHandout_src()).putExtra("id",curriculumClassificationListBeanList.get(position).getId()));
                    }
                }
            }
        });
    }

    @Override
    protected void initBase() {

    }

    @Override
    public void onClick(View v) {

    }

    public Intent getWordFileIntent(String param) {
        Intent intent = null;
        try {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "application/msword");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            ShowToast("请安装office");
            e.printStackTrace();
        }
        return intent;
    }

    public Intent getPDFFileIntent(String param) {
        Intent intent = null;
        try {
            intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, "application/pdf");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            ShowToast("请安装office");
            e.printStackTrace();
        }
        return intent;
    }

    public boolean fileIsExists(String src) {
        try {
            File f = new File(src);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }



    //视频列表
    private void getStudyVideoLook() {
        mSubscription = apiService.getStudyVideoLook(getIntent().getStringExtra("id")).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
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



    //视频列表
    private void getStudyHandout() {
        mSubscription = apiService.getStudyHandout(getIntent().getStringExtra("id")).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
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
