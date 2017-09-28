package com.test720.wendujiaoyu.study.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.study.adapter.StudyHomeRecyclerViewAdapter;
import com.test720.wendujiaoyu.study.bean.CacheLisstOne;
import com.test720.wendujiaoyu.study.bean.DownloadBean;
import com.test720.wendujiaoyu.study.bean.StudyHomeBanner;
import com.test720.wendujiaoyu.study.bean.StudyHomeErrorBean;
import com.test720.wendujiaoyu.study.bean.StudyHomeType;
import com.test720.wendujiaoyu.study.download.DownLoadListener;
import com.test720.wendujiaoyu.study.download.DownLoadManager;
import com.test720.wendujiaoyu.study.download.DownLoadService;
import com.test720.wendujiaoyu.study.download.TaskInfo;
import com.test720.wendujiaoyu.study.download.dbcontrol.FileHelper;
import com.test720.wendujiaoyu.study.download.dbcontrol.bean.SQLDownLoadInfo;
import com.test720.wendujiaoyu.study.network.RxSchedulersHelper;
import com.test720.wendujiaoyu.study.network.RxSubscriber;
import com.test720.wendujiaoyu.study.utils.RecycleViewDivider;
import com.test720.wendujiaoyu.utills.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class StudyHomeActivity extends BaseToolbarActivity {


    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    @BindView(R.id.layout_refreshLayout)
    TwinklingRefreshLayout layoutRefreshLayout;
    private StudyHomeRecyclerViewAdapter studyHomeRecyclerViewAdapter;
    private List<StudyHomeBanner> studyHomeBannerList = new ArrayList<>();
    private List<StudyHomeType> studyHomeTypeList = new ArrayList<>();
    private List<StudyHomeErrorBean> studyHomeErrorBeanArrayList = new ArrayList<>();
    private List<DownloadBean> downloadBeens = new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.activity_study_home;
    }

    @Override
    protected void initData() {
        initToobar("学习首页",R.mipmap.fanhui);

        //是否第一次进入学习模块第一次则删除全部文件  记录
        if("".equals(spUtils.get("cacheFirst",Constants.uniqueness + "record","").toString()))
        {
            spUtils.put("cacheFirst",Constants.uniqueness + "record","1");
            spUtils.put("cache", Constants.uniqueness + "list", "");
            deleteDir( FileHelper.getFileDefaultPath());
            spUtils.put("src", Constants.uniqueness + "json", "");
        }



        handler.sendEmptyMessageDelayed(1, 50);
        List<Integer> integerList = new ArrayList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        integerList.add(4);
        integerList.add(5);
        integerList.add(6);

        myRecyclerView.setLayoutManager(new LinearLayoutManager(myRecyclerView.getContext()));
        myRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        studyHomeRecyclerViewAdapter = new StudyHomeRecyclerViewAdapter(mActivity, integerList, studyHomeBannerList, studyHomeTypeList, studyHomeErrorBeanArrayList,downloadBeens);
        myRecyclerView.setAdapter(studyHomeRecyclerViewAdapter);
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
        }, 200);
        getCacheDataList();
    }



    private void getCacheDataList()
    {
        downloadBeens.clear();
        String json = spUtils.get("src", Constants.uniqueness + "json", "").toString();
        if(!"".equals(json))
        {
            List<DownloadBean> downloadBeanList = JSONArray.parseArray(json,DownloadBean.class);
            int count = 0;
            for (int i = downloadBeanList.size()-1; i >=0; i--) {

                if(downloadBeanList.get(i).getFormat().equals(".mp4"))
                {
                    count++;
                    if(count > 3)return;
                    downloadBeens.add(downloadBeanList.get(i));
                    Log.e("===========","====="+downloadBeanList.get(i).getSrc());
                }
            }
        }
        studyHomeRecyclerViewAdapter.notifyDataSetChanged();
    }



    @Override
    protected void setListener() {
        layoutRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                getStudyIndex();
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


    private void getStudyIndex() {
        mSubscription = apiService.getStudyIndex(Constants.uid).compose(RxSchedulersHelper.<JSONObject>io_main()).subscribe(new RxSubscriber<JSONObject>() {
            @Override
            public void _onNext(JSONObject jsonObject) {
                if (jsonObject.getIntValue("code") == 1) {
                    JSONObject object = jsonObject.getJSONObject("data");
                    studyHomeBannerList.clear();
                    studyHomeTypeList.clear();
                    studyHomeErrorBeanArrayList.clear();
                    studyHomeBannerList.addAll(JSONObject.parseArray(object.getJSONArray("lunbo").toJSONString(), StudyHomeBanner.class));
                    studyHomeTypeList.addAll(JSONObject.parseArray(object.getJSONArray("type").toJSONString(), StudyHomeType.class));
                    studyHomeErrorBeanArrayList.addAll(JSONObject.parseArray(object.getJSONArray("error").toJSONString(), StudyHomeErrorBean.class));
                    studyHomeRecyclerViewAdapter.notifyDataSetChanged();


                    if ("".equals(spUtils.get("cache", Constants.uniqueness + "list", "").toString())) {
                        List<CacheLisstOne> cacheLisstOnes = new ArrayList<CacheLisstOne>();
                        for (int i = 0; i < object.getJSONArray("courseList").size(); i++) {
                            JSONObject object1 = object.getJSONArray("courseList").getJSONObject(i);
                            CacheLisstOne cacheLisstOne = new CacheLisstOne();
                            cacheLisstOne.setId(object1.getString("id"));
                            cacheLisstOne.setImg(Constants.ImagHost + object1.getString("img"));
                            cacheLisstOne.setName(object1.getString("name"));
                            cacheLisstOnes.add(cacheLisstOne);
                            List<CacheLisstOne> list = new ArrayList<CacheLisstOne>();
                            for (int i1 = 0; i1 < object1.getJSONArray("list").size(); i1++) {
                                CacheLisstOne cacheLisstOne1 = JSONObject.parseObject(object1.getJSONArray("list").getJSONObject(i1).toJSONString(),CacheLisstOne.class);
                                cacheLisstOne1.setImg(Constants.ImagHost + cacheLisstOne1.getImg());
                                list.add(cacheLisstOne1);
                            }
                            spUtils.put("cache", Constants.uniqueness + "list" + object1.getString("id"), JSONArray.toJSONString(list));
                        }
                        if (cacheLisstOnes.size() > 0) {
                            manager.addTask(cacheLisstOnes.get(0).getId() + "-" + cacheLisstOnes.get(0).getName(), cacheLisstOnes.get(0).getImg(), cacheLisstOnes.get(0).getName() + ".png");
                        }
                        spUtils.put("cache", Constants.uniqueness + "list", JSONArray.toJSONString(cacheLisstOnes));
                    }
                    myRecyclerView.setVisibility(View.VISIBLE);
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


    /*******************************************************
     * 下载
     **************************************************/
     /*使用DownLoadManager时只能通过DownLoadService.getDownLoadManager()的方式来获取下载管理器，不能通过new DownLoadManager()的方式创建下载管理器*/
    private DownLoadManager manager;
    private ArrayList<TaskInfo> listdata = new ArrayList<TaskInfo>(); //下载队列
    Handler handler = new Handler() {
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
            Log.e("====", "====");
            List<CacheLisstOne> cacheLisstOnes = getCacheList();
            int count = 0;
            for (int i = 0; i < listdata.size(); i++) {
                for (int i1 = 0; i1 < cacheLisstOnes.size(); i1++) {
                    if (listdata.get(i).getTaskID().equals(cacheLisstOnes.get(i1).getId() + "-" + cacheLisstOnes.get(i1).getName())) {
                        count++;
                        manager.startTask(cacheLisstOnes.get(i1).getId() + "-" + cacheLisstOnes.get(i1).getName());
                        break;
                    }
                }
                if (count > 0) break;
            }
            if (count == 0) {
                for (int i = 0; i < cacheLisstOnes.size(); i++) {
                    if (cacheLisstOnes.get(i).getStats() == 0) {
                        String taskId = cacheLisstOnes.get(i).getId() + "-" + cacheLisstOnes.get(i).getName();
                        manager.addTask(taskId, cacheLisstOnes.get(i).getImg(), cacheLisstOnes.get(i).getName() + ".png");
                        break;
                    }
                }
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        if(studyHomeRecyclerViewAdapter !=null)
        {
            getCacheDataList();
        }
        if (manager != null) {
            final List<CacheLisstOne> cacheLisstOnes = getCacheList();
            Log.e("===", "===");
            int count = 0;
            for (int i = 0; i < listdata.size(); i++) {
                for (int i1 = 0; i1 < cacheLisstOnes.size(); i1++) {
                    if (listdata.get(i).getTaskID().equals(cacheLisstOnes.get(i1).getId() + "-" + cacheLisstOnes.get(i1).getName())) {
                        count++;
                        manager.startTask(cacheLisstOnes.get(i1).getId() + "-" + cacheLisstOnes.get(i1).getName());
                        break;
                    }
                }
                if (count > 0) break;
            }
            if (count == 0) {
                for (int i = 0; i < cacheLisstOnes.size(); i++) {
                    if (cacheLisstOnes.get(i).getStats() == 0) {
                        String taskId = cacheLisstOnes.get(i).getId() + "-" + cacheLisstOnes.get(i).getName();
                        manager.addTask(taskId, cacheLisstOnes.get(i).getImg(), cacheLisstOnes.get(i).getName() + ".png");
                        break;
                    }
                }
            }
        }
    }

    //下载监听
    private class DownloadManagerListener implements DownLoadListener {

        @Override
        public void onStart(SQLDownLoadInfo sqlDownLoadInfo) {
            Log.e("======", "====" + sqlDownLoadInfo.toString());

        }

        @Override
        public void onProgress(SQLDownLoadInfo sqlDownLoadInfo, boolean isSupportBreakpoint) {
            //根据监听到的信息查找列表相对应的任务，更新相应任务的进度
            listdata = manager.getAllTask();
            for (TaskInfo taskInfo : listdata) {
                if (taskInfo.getTaskID().equals(sqlDownLoadInfo.getTaskID())) {
                    break;
                }
            }
        }

        @Override
        public void onStop(SQLDownLoadInfo sqlDownLoadInfo, boolean isSupportBreakpoint) {

        }

        @Override
        public void onSuccess(SQLDownLoadInfo sqlDownLoadInfo) {
            //根据监听到的信息查找列表相对应的任务，删除对应的任务
            for (TaskInfo taskInfo : listdata) {
                listdata.remove(taskInfo);
                final List<CacheLisstOne> cacheLisstOnes = getCacheList();
                for (int i = 0; i < cacheLisstOnes.size(); i++) {
                    if (cacheLisstOnes.get(i).getStats() == 0) {
                        String taskId = cacheLisstOnes.get(i).getId() + "-" + cacheLisstOnes.get(i).getName();
                        if (taskId.equals(taskInfo.getTaskID())) {
                            cacheLisstOnes.get(i).setStats(1);
                            cacheLisstOnes.get(i).setImg(sqlDownLoadInfo.getFilePath());
                            if ((i + 1) <= cacheLisstOnes.size() - 1) {
                                manager.addTask(cacheLisstOnes.get(i + 1).getId() + "-" + cacheLisstOnes.get(i + 1).getName(), cacheLisstOnes.get(i + 1).getImg(), cacheLisstOnes.get(i + 1).getName() + ".png");
                            }
                            break;
                        }
                    }
                }
                //分类出来在存入进去
                List <CacheLisstOne> list = new ArrayList<>();
                List <List <CacheLisstOne>> list1 = new ArrayList<>();
                String json = spUtils.get("cache", Constants.uniqueness + "list", "").toString();
                if (!"".equals(json)) {
                    for (int i = 0; i < JSON.parseArray(json).size(); i++) {
                        list1.add(new ArrayList<CacheLisstOne>());
                        list.add(cacheLisstOnes.get(i));
                    }
                    spUtils.put("cache",Constants.uniqueness + "list", JSONArray.toJSONString(list));
                }

                for (int i = 0; i < cacheLisstOnes.size(); i++) {
                    for (int i1 = 0; i1 < list.size(); i1++) {
                        if(cacheLisstOnes.get(i).getType() == Integer.parseInt(list.get(i1).getId()))
                        {
                            list1.get(i1).add(cacheLisstOnes.get(i));
                        }
                    }
                }
                for (int i = 0; i < list.size(); i++) {
                    spUtils.put("cache",Constants.uniqueness + "list"+list.get(i).getId(), JSONArray.toJSONString(list1.get(i)));
                }
                break;
            }
        }

        @Override
        public void onError(SQLDownLoadInfo sqlDownLoadInfo) {
            //根据监听到的信息查找列表相对应的任务，停止该任务
            for (TaskInfo taskInfo : listdata) {
                if (taskInfo.getTaskID().equals(sqlDownLoadInfo.getTaskID())) {
                    break;
                }
            }
        }
    }


    private List<CacheLisstOne> getCacheList() {
        String json = spUtils.get("cache", Constants.uniqueness + "list", "").toString();
        List<CacheLisstOne> cacheLisstOnes = new ArrayList<>();
        List<CacheLisstOne> list = new ArrayList<>();
        if (!"".equals(json)) {
            cacheLisstOnes = JSONArray.parseArray(json, CacheLisstOne.class);
            for (int i = 0; i < cacheLisstOnes.size(); i++) {
                if (!"".equals(spUtils.get("cache", Constants.uniqueness + "list" + cacheLisstOnes.get(i).getId(), "").toString())) {

                    List<CacheLisstOne> cacheLisstOnes1 = JSONArray.parseArray(spUtils.get("cache", Constants.uniqueness + "list" + cacheLisstOnes.get(i).getId(), "").toString(), CacheLisstOne.class);
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



    //删除文件夹和文件夹里面的文件
    public void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirWihtFile(dir);
    }

    public void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirWihtFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }




}
