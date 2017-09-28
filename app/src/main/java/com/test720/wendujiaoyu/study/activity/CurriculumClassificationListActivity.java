package com.test720.wendujiaoyu.study.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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
import com.test720.wendujiaoyu.study.bean.DownloadBean;
import com.test720.wendujiaoyu.study.download.DownLoadListener;
import com.test720.wendujiaoyu.study.download.DownLoadManager;
import com.test720.wendujiaoyu.study.download.DownLoadService;
import com.test720.wendujiaoyu.study.download.TaskInfo;
import com.test720.wendujiaoyu.study.download.dbcontrol.FileHelper;
import com.test720.wendujiaoyu.study.download.dbcontrol.bean.SQLDownLoadInfo;
import com.test720.wendujiaoyu.study.network.RxSchedulersHelper;
import com.test720.wendujiaoyu.study.network.RxSubscriber;
import com.test720.wendujiaoyu.study.utils.BaseRecyclerAdapter;
import com.test720.wendujiaoyu.study.utils.BaseRecyclerHolder;
import com.test720.wendujiaoyu.study.utils.Player;
import com.test720.wendujiaoyu.study.utils.RecycleViewDivider;
import com.test720.wendujiaoyu.utills.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CurriculumClassificationListActivity extends BaseToolbarActivity {
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.layout_refreshLayout)
    TwinklingRefreshLayout layoutRefreshLayout;
    @BindView(R.id.see_bar)
    SeekBar seeBar;
    @BindView(R.id.layout_play_left)
    RelativeLayout layoutPlayLeft;
    @BindView(R.id.layout_play_middle)
    RelativeLayout layoutPlayMiddle;
    @BindView(R.id.layout_play_right)
    RelativeLayout layoutPlayRight;
    @BindView(R.id.layout_bg)
    RelativeLayout layoutBg;
    @BindView(R.id.iv_middle)
    ImageView ivMiddle;
    @BindView(R.id.tv_stats)
    TextView tvStats;
    @BindView(R.id.tv_end)
    TextView tvEnd;

    private BaseRecyclerAdapter<CurriculumClassificationListBean> adapter;
    private List<CurriculumClassificationListBean> curriculumClassificationListBeanList = new ArrayList<>();
    private Player player; // 播放器
    private int count = 0;
    private ArrayList<TaskInfo> listdata = new  ArrayList<TaskInfo>(); //下载队列
    private int page = 1;
    @Override
    protected int getContentView() {
        return R.layout.activity_curriculum_classification_list;
    }

    @Override
    protected void initData() {
        initToobar(getIntent().getStringExtra("title"));
        //下载管理器需要启动一个Service,在刚启动应用的时候需要等Service启动起来后才能获取下载管理器，所以稍微延时获取下载管理器
        handler.sendEmptyMessageDelayed(1, 50);
        player = new Player(seeBar,tvStats,tvEnd);
        adapter = new BaseRecyclerAdapter<CurriculumClassificationListBean>(context, curriculumClassificationListBeanList, R.layout.item_curriculum_classification_list) {
            @Override
            public void convert(BaseRecyclerHolder holder, CurriculumClassificationListBean item, final int position, boolean isScrolling) {
                sizeUtils.setLayoutSizeHeight(holder.getView(R.id.layout_bg), 90);
                sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_title), 20);
                holder.setText(R.id.tv_title, curriculumClassificationListBeanList.get(position).getName());
                RelativeLayout layoutStats = holder.getView(R.id.layout_left);
                ImageView ivStats = holder.getView(R.id.iv_stats);
                TextView tvStats = holder.getView(R.id.tv_stats);
//                if(getDownloadFlie(curriculumClassificationListBeanList.get(position).getId())) ivStats.setImageResource(R.mipmap.ic_xiazai_gou);
//                else ivStats.setImageResource(R.mipmap.ic_xiazai_xiazai);
                int stats = curriculumClassificationListBeanList.get(position).getStats();
                ivStats.setVisibility(View.VISIBLE);
                tvStats.setVisibility(View.GONE);
                Log.e("======","====stats"+stats);
                if(stats == 0)
                {
                    ivStats.setImageResource(R.mipmap.ic_xiazai_xiazai);
                }
                else  if(stats == 1)
                {
                    tvStats.setVisibility(View.VISIBLE);
                    ivStats.setVisibility(View.GONE);
                    if(curriculumClassificationListBeanList.get(position).getProgress()!=null)
                    {
                        tvStats.setText(curriculumClassificationListBeanList.get(position).getProgress()+"%");
                    }
                    else
                    {
                        tvStats.setText("0%");
                    }
                }
                else  if(stats == 2)  ivStats.setImageResource(R.mipmap.ic_xiazai_bofnag);
                else ivStats.setImageResource(R.mipmap.ic_xiazai_gou);
                if ("1".equals(getIntent().getStringExtra("type"))) layoutStats.setVisibility(View.VISIBLE);
                else if ("2".equals(getIntent().getStringExtra("type"))) layoutStats.setVisibility(View.VISIBLE);
                layoutStats.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = curriculumClassificationListBeanList.get(position).getId();
                        if(curriculumClassificationListBeanList.get(position).getStats() == 0)
                        {
                            String url = "";
                            if ("1".equals(getIntent().getStringExtra("type"))) url = curriculumClassificationListBeanList.get(position).getVideo_src();
                            else if ("2".equals(getIntent().getStringExtra("type"))) url = curriculumClassificationListBeanList.get(position).getVoice_src();
                            String prefix=url.substring(url.lastIndexOf("."));
                            String path = curriculumClassificationListBeanList.get(position).getName()+prefix;
                            /*将任务添加到下载队列，下载器会自动开始下载*/
                           int index =  manager.addTask(name, Constants.ImagHost+url, path);
                            Log.e("====","===="+index);
                            curriculumClassificationListBeanList.get(position).setStats(1);
                            notifyDataSetChanged();
                        }
                        else  if(curriculumClassificationListBeanList.get(position).getStats() == 1)
                        {
                            curriculumClassificationListBeanList.get(position).setStats(2);
                            manager.stopTask(name);
                            notifyDataSetChanged();
                        }
                        else if(curriculumClassificationListBeanList.get(position).getStats() == 2)
                        {
                            manager.startTask(name);
                            curriculumClassificationListBeanList.get(position).setStats(1);
                            notifyDataSetChanged();
                        }
                    }
                });


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
        seeBar.setOnSeekBarChangeListener(new SeekBarChangeEvent());
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                if ("1".equals(getIntent().getStringExtra("type")))
                {
                    String url = curriculumClassificationListBeanList.get(position).getVideo_src();
                    String prefix=url.substring(url.lastIndexOf("."));
                    String path = FileHelper.getFileDefaultPath() + "/" +curriculumClassificationListBeanList.get(position).getName()+prefix;
                    if(!fileIsExists(path))
                    {
                        path = Constants.ImagHost+ curriculumClassificationListBeanList.get(position).getVideo_src();
                    }
                    startActivity(new Intent(mContext, VideoPlayActivity.class).putExtra("url",path).putExtra("title",curriculumClassificationListBeanList.get(position).getName()));
                }

                else if ("2".equals(getIntent().getStringExtra("type")))
                {
                    layoutBg.setVisibility(View.VISIBLE);
                    count = position;
                    getStartPlayer(count);
                }
                else if ("3".equals(getIntent().getStringExtra("type")))
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
//                            startActivity(getPDFFileIntent(src));
                            startActivity(new Intent(mContext,FileSeeActivity.class).putExtra("title",curriculumClassificationListBeanList.get(position).getName()).putExtra("src",curriculumClassificationListBeanList.get(position).getHandout_src()).putExtra("id",curriculumClassificationListBeanList.get(position).getId()));
                        }
                    }
                    else
                    {
                        startActivity(new Intent(mContext,FileSeeActivity.class).putExtra("title",curriculumClassificationListBeanList.get(position).getName()).putExtra("src",curriculumClassificationListBeanList.get(position).getHandout_src()).putExtra("id",curriculumClassificationListBeanList.get(position).getId()));
                    }
                }
                else if ("4".equals(getIntent().getStringExtra("type")))
                startActivity(new Intent(mContext,AnswerActivity.class).putExtra("id",curriculumClassificationListBeanList.get(position).getId()).putExtra("title",curriculumClassificationListBeanList.get(position).getName()).putExtra("cid",getIntent().getStringExtra("id")));
                else if ("5".equals(getIntent().getStringExtra("type")))
                    startActivity(new Intent(mContext,AnswerActivity.class).putExtra("id",curriculumClassificationListBeanList.get(position).getId()).putExtra("title",curriculumClassificationListBeanList.get(position).getName()).putExtra("cid",getIntent().getStringExtra("id")).putExtra("type","1"));
                else if ("6".equals(getIntent().getStringExtra("type")))
                    startActivity(new Intent(mContext,ErrorAnswerActivity.class).putExtra("id",curriculumClassificationListBeanList.get(position).getId()).putExtra("title",curriculumClassificationListBeanList.get(position).getName()).putExtra("cid",getIntent().getStringExtra("id")));

            }
        });
        layoutRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                page = 1;
                if ("1".equals(getIntent().getStringExtra("type")))
                    getStudyVideoVideoList();
                else if ("2".equals(getIntent().getStringExtra("type")))
                    getStudyVoiceVoiceList();
                else if ("3".equals(getIntent().getStringExtra("type")))
                    getStudyHandoutHandoutList();
                else if ("4".equals(getIntent().getStringExtra("type")))
                    getStudyQuestionsQusestionList();
                else if ("5".equals(getIntent().getStringExtra("type")))
                    getStudyCollectionCollectionList();
                else if ("6".equals(getIntent().getStringExtra("type")))
                    getStudyErrorErrorCourseList();
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                page ++;
                if ("1".equals(getIntent().getStringExtra("type")))
                    getStudyVideoVideoList();
                else if ("2".equals(getIntent().getStringExtra("type")))
                    getStudyVoiceVoiceList();
                else if ("3".equals(getIntent().getStringExtra("type")))
                    getStudyHandoutHandoutList();
                else if ("4".equals(getIntent().getStringExtra("type")))
                    getStudyQuestionsQusestionList();
                else if ("5".equals(getIntent().getStringExtra("type")))
                    getStudyCollectionCollectionList();
                else if ("6".equals(getIntent().getStringExtra("type")))
                    getStudyErrorErrorCourseList();
            }
        });
    }

    @Override
    protected void initBase() {

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
    private void getStudyVideoVideoList() {
        mSubscription = apiService.getStudyVideoVideoList(getIntent().getStringExtra("cid"),page).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getIntValue("code") == 1) {
                    if(jsonObject.getJSONObject("data").getJSONArray("list").size() < 10)
                        layoutRefreshLayout.setEnableLoadmore(false);
                    else
                        layoutRefreshLayout.setEnableLoadmore(true);
                    if(page == 1)
                        curriculumClassificationListBeanList.clear();
                    curriculumClassificationListBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONObject("data").getJSONArray("list").toJSONString(), CurriculumClassificationListBean.class));
                    List<DownloadBean> downloadBeen = getDownloadBeens();
                    for (int i = 0; i < curriculumClassificationListBeanList.size(); i++) {
                        String  id =  curriculumClassificationListBeanList.get(i).getId();
                        for (int i1 = 0; i1 < downloadBeen.size(); i1++) {
                            if(downloadBeen.get(i1).getId().equals(id))
                            {
                                curriculumClassificationListBeanList.get(i).setStats(3);
                            }
                        }
                        for (int i1 = 0; i1 < listdata.size(); i1++) {
                            if(listdata.get(i1).getTaskID().equals(id))
                            {
                                curriculumClassificationListBeanList.get(i).setStats(2);
                            }
                        }
                    }
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
                onStopLoad();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onStopLoad();
                page = page == 1 ? 1 : page - 1;
            }
        });
    }

    //音频列表
    private void getStudyVoiceVoiceList() {
        mSubscription = apiService.getStudyVoiceVoiceList(getIntent().getStringExtra("cid"),page).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getIntValue("code") == 1) {
                    if(jsonObject.getJSONObject("data").getJSONArray("list").size() < 10)
                        layoutRefreshLayout.setEnableLoadmore(false);
                    else
                        layoutRefreshLayout.setEnableLoadmore(true);
                    if(page == 1)
                        curriculumClassificationListBeanList.clear();
                    curriculumClassificationListBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONObject("data").getJSONArray("list").toJSONString(), CurriculumClassificationListBean.class));
                    List<DownloadBean> downloadBeen = getDownloadBeens();
                    for (int i = 0; i < curriculumClassificationListBeanList.size(); i++) {
                        String  id =  curriculumClassificationListBeanList.get(i).getId();
                        for (int i1 = 0; i1 < downloadBeen.size(); i1++) {
                            if(downloadBeen.get(i1).getId().equals(id))
                            {
                                curriculumClassificationListBeanList.get(i).setStats(3);
                            }
                        }
                        for (int i1 = 0; i1 < listdata.size(); i1++) {
                            if(listdata.get(i1).getTaskID().equals(id))
                            {
                                curriculumClassificationListBeanList.get(i).setStats(1);
                            }
                        }
                    }
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
                onStopLoad();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onStopLoad();
                page = page == 1 ? 1 : page - 1;
            }
        });
    }

    //讲义列表
    private void getStudyHandoutHandoutList() {
        mSubscription = apiService.getStudyHandoutHandoutList(getIntent().getStringExtra("id"),page).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getIntValue("code") == 1) {
                    if(jsonObject.getJSONObject("data").getJSONArray("list").size() < 10)
                        layoutRefreshLayout.setEnableLoadmore(false);
                    else
                        layoutRefreshLayout.setEnableLoadmore(true);
                    if(page == 1)
                        curriculumClassificationListBeanList.clear();
                    curriculumClassificationListBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONObject("data").getJSONArray("list").toJSONString(), CurriculumClassificationListBean.class));
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
                onStopLoad();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onStopLoad();
                page = page == 1 ? 1 : page - 1;
            }
        });
    }

    //题库列表
    private void getStudyQuestionsQusestionList() {
        mSubscription = apiService.getStudyQuestionsQusestionList(getIntent().getStringExtra("id"),page).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getIntValue("code") == 1) {
                    if(jsonObject.getJSONObject("data").getJSONArray("list").size() < 10)
                        layoutRefreshLayout.setEnableLoadmore(false);
                    else
                        layoutRefreshLayout.setEnableLoadmore(true);
                    if(page == 1)
                        curriculumClassificationListBeanList.clear();
                    curriculumClassificationListBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONObject("data").getJSONArray("list").toJSONString(), CurriculumClassificationListBean.class));
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
                onStopLoad();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onStopLoad();
                page = page == 1 ? 1 : page - 1;
            }
        });
    }

    //收藏列表
    private void getStudyCollectionCollectionList() {
        mSubscription = apiService.getStudyCollectionCollectionList(getIntent().getStringExtra("id"), Constants.uid,page).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getIntValue("code") == 1) {
                    if(jsonObject.getJSONObject("data").getJSONArray("list").size() < 10)
                        layoutRefreshLayout.setEnableLoadmore(false);
                    else
                        layoutRefreshLayout.setEnableLoadmore(true);
                    if(page == 1)
                        curriculumClassificationListBeanList.clear();
                    curriculumClassificationListBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONObject("data").getJSONArray("list").toJSONString(), CurriculumClassificationListBean.class));
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
                onStopLoad();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onStopLoad();
                page = page == 1 ? 1 : page - 1;
            }
        });
    }

    //收藏列表
    private void getStudyErrorErrorCourseList() {
        mSubscription = apiService.getStudyErrorErrorCourseList(getIntent().getStringExtra("id"), Constants.uid,page).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getIntValue("code") == 1) {
                    if(jsonObject.getJSONObject("data").getJSONArray("list").size() < 10)
                        layoutRefreshLayout.setEnableLoadmore(false);
                    else
                        layoutRefreshLayout.setEnableLoadmore(true);
                    if(page == 1)
                        curriculumClassificationListBeanList.clear();
                    curriculumClassificationListBeanList.addAll(JSONArray.parseArray(jsonObject.getJSONObject("data").getJSONArray("list").toJSONString(), CurriculumClassificationListBean.class));
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
                onStopLoad();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onStopLoad();
                page = page == 1 ? 1 : page - 1;
            }
        });
    }


    /*****************************************************
     * 播放音频
     *****************************************************/
    private void getStartPlayer(final int position) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                String url = curriculumClassificationListBeanList.get(position).getVoice_src();
                String prefix=url.substring(url.lastIndexOf("."));
                String path = FileHelper.getFileDefaultPath() + "/" +curriculumClassificationListBeanList.get(position).getName()+prefix;
                if(!fileIsExists(path))
                {
                    path = Constants.ImagHost + curriculumClassificationListBeanList.get(position).getVoice_src();
                }
                player.playUrl(path);
            }
        }).start();
    }



    @OnClick({R.id.layout_play_left, R.id.layout_play_middle, R.id.layout_play_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_play_left://上一首
                if(count > 0)
                {
                    count --;
                    ivMiddle.setImageResource(R.mipmap.ic_bofang);
                    getStartPlayer(count);
                }
                else
                {
                    ShowToast("这已经是第一个了");
                }

                break;
            case R.id.layout_play_middle://播放/暂停
                if(!player.mediaPlayer.isPlaying())
                {
                    ivMiddle.setImageResource(R.mipmap.ic_bofang);
                    player.mediaPlayer.start();
                }
                else
                {
                    ivMiddle.setImageResource(R.mipmap.ic_zanting);
                    player.mediaPlayer.pause();
                }
                break;
            case R.id.layout_play_right://下一首
                if(curriculumClassificationListBeanList.size()-1 > count)
                {
                    count ++;
                    ivMiddle.setImageResource(R.mipmap.ic_bofang);
                    getStartPlayer(count);
                }
                else
                {
                    ShowToast("没有更多了");
                }
                break;
        }
    }


    // 进度改变
    class SeekBarChangeEvent implements SeekBar.OnSeekBarChangeListener {
        int progress;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // 原本是(progress/seekBar.getMax())*player.mediaPlayer.getDuration()
            this.progress = progress * player.mediaPlayer.getDuration()
                    / seekBar.getMax();
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            // seekTo()的参数是相对与影片时间的数字，而不是与seekBar.getMax()相对的数字
            player.mediaPlayer.seekTo(progress);
        }

    }


    //停止刷新
    private void onStopLoad() {
        if (page == 1) {
            layoutRefreshLayout.finishRefreshing();
        } else {
            layoutRefreshLayout.finishLoadmore();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player = null;
        }
    }


    /*****************************************************
     * 下载文件
     *****************************************************/
    /*使用DownLoadManager时只能通过DownLoadService.getDownLoadManager()的方式来获取下载管理器，不能通过new DownLoadManager()的方式创建下载管理器*/
    private DownLoadManager manager;
    //对比本地是否有下载
    private Boolean getDownloadFlie(String id)
    {
        String json = spUtils.get("src",Constants.uniqueness + "json","").toString();
        if("".equals(json))
        {
            return false;
        }

        List<DownloadBean> downloadBeen = getDownloadBeens();
        for (int i = 0; i < downloadBeen.size(); i++) {
            if(downloadBeen.get(i).getId().equals(id))
            {
                return true;
            }
        }
        return false;
    }


    private List<DownloadBean> getDownloadBeens()
    {
        String json = spUtils.get("src",Constants.uniqueness + "json","").toString();
        if("".equals(json))
        {
            return  new ArrayList<DownloadBean>();
        }
        else
        {
            List<DownloadBean> downloadBeen = new ArrayList<>();
            downloadBeen.addAll(JSONArray.parseArray(json,DownloadBean.class));
            return downloadBeen;
        }
    }



    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            /*获取下载管理器*/
            manager = DownLoadService.getDownLoadManager();
            /*设置用户ID，客户端切换用户时可以显示相应用户的下载任务*/
            manager.changeUser(Constants.uniqueness + "luffy");
            /*断点续传需要服务器的支持，设置该项时要先确保服务器支持断点续传功能*/
            manager.setSupportBreakpoint(true);
            listdata = manager.getAllTask();
            manager.setAllTaskListener(new DownloadManagerListener());
        }
    };
    //下载监听
    private class DownloadManagerListener implements DownLoadListener {

        @Override
        public void onStart(SQLDownLoadInfo sqlDownLoadInfo) {
            Log.e("onStart","===="+sqlDownLoadInfo.toString());

        }

        @Override
        public void onProgress(SQLDownLoadInfo sqlDownLoadInfo, boolean isSupportBreakpoint) {
            //根据监听到的信息查找列表相对应的任务，更新相应任务的进度
            Log.e("onProgress","===="+sqlDownLoadInfo.toString());
            listdata = manager.getAllTask();
            for(TaskInfo taskInfo : listdata){
                if(taskInfo.getTaskID().equals(sqlDownLoadInfo.getTaskID())){
                    taskInfo.setDownFileSize(sqlDownLoadInfo.getDownloadSize());
                    taskInfo.setFileSize(sqlDownLoadInfo.getFileSize());
                    double size = (double) sqlDownLoadInfo.getDownloadSize()/(double)sqlDownLoadInfo.getFileSize();
                    for (int i = 0; i < curriculumClassificationListBeanList.size(); i++) {
                        if(curriculumClassificationListBeanList.get(i).getId().equals(sqlDownLoadInfo.getTaskID()))
                        {
                            curriculumClassificationListBeanList.get(i).setProgress(((int)(size *100))+"");
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                    break;
                }
            }
        }

        @Override
        public void onStop(SQLDownLoadInfo sqlDownLoadInfo, boolean isSupportBreakpoint) {
            Log.e("onStop","===="+sqlDownLoadInfo.toString());
        }

        @Override
        public void onSuccess(SQLDownLoadInfo sqlDownLoadInfo) {
            Log.e("onSuccess","===="+sqlDownLoadInfo.toString());
            //根据监听到的信息查找列表相对应的任务，删除对应的任务
            for(TaskInfo taskInfo : listdata){
                if(taskInfo.getTaskID().equals(sqlDownLoadInfo.getTaskID())){
                    for (int i = 0; i < curriculumClassificationListBeanList.size(); i++) {
                        if(curriculumClassificationListBeanList.get(i).getId().equals(sqlDownLoadInfo.getTaskID()))
                        {
                            curriculumClassificationListBeanList.get(i).setStats(3);
                            List<DownloadBean> downloadBeens = new ArrayList<>();
                            DownloadBean downloadBean = new DownloadBean();
                            downloadBean.setId(curriculumClassificationListBeanList.get(i).getId());
                            downloadBean.setCourseType(getIntent().getStringExtra("id"));
                            downloadBean.setType(getIntent().getStringExtra("typeID"));
                            downloadBean.setTitle(curriculumClassificationListBeanList.get(i).getName());
                            downloadBean.setTime(com.test720.wendujiaoyu.study.utils.DateUtils.formatDate(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss"));
                            String url = "";
                            if ("1".equals(getIntent().getStringExtra("type")))
                            {
                                url = curriculumClassificationListBeanList.get(i).getVideo_src();
                            }
                            else if ("2".equals(getIntent().getStringExtra("type")))
                            {
                                url = curriculumClassificationListBeanList.get(i).getVoice_src();
                            }
                            String prefix=url.substring(url.lastIndexOf("."));
                            downloadBean.setFormat(prefix);
                            String path = FileHelper.getFileDefaultPath() + "/" +curriculumClassificationListBeanList.get(i).getName()+prefix;
                            downloadBean.setSrc(path);
                            String json = spUtils.get("src",Constants.uniqueness + "json","").toString();
                            if(!"".equals(json))
                            {
                                downloadBeens.addAll(JSONArray.parseArray(json,DownloadBean.class));
                            }
                            downloadBeens.add(downloadBean);
                            spUtils.put("src",Constants.uniqueness + "json",JSONArray.toJSONString(downloadBeens));
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                    listdata.remove(taskInfo);
                    break;
                }
            }
        }

        @Override
        public void onError(SQLDownLoadInfo sqlDownLoadInfo) {
            Log.e("onError","===="+sqlDownLoadInfo.toString());
            //根据监听到的信息查找列表相对应的任务，停止该任务
            for(TaskInfo taskInfo : listdata){
                if(taskInfo.getTaskID().equals(sqlDownLoadInfo.getTaskID())){
                    for (int i = 0; i < curriculumClassificationListBeanList.size(); i++) {
                        if(curriculumClassificationListBeanList.get(i).getId().equals(sqlDownLoadInfo.getTaskID()))
                        {
                            curriculumClassificationListBeanList.get(i).setStats(0);
                            adapter.notifyDataSetChanged();
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }



}
