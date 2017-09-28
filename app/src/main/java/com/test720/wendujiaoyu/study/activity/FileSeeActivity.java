package com.test720.wendujiaoyu.study.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.baseUi.ProgressWebview;
import com.test720.wendujiaoyu.study.download.DownLoadListener;
import com.test720.wendujiaoyu.study.download.DownLoadManager;
import com.test720.wendujiaoyu.study.download.DownLoadService;
import com.test720.wendujiaoyu.study.download.TaskInfo;
import com.test720.wendujiaoyu.study.download.dbcontrol.FileHelper;
import com.test720.wendujiaoyu.study.download.dbcontrol.bean.SQLDownLoadInfo;
import com.test720.wendujiaoyu.utills.Constants;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class FileSeeActivity extends BaseToolbarActivity {


    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.pbCrosswise)
    ProgressBar pbCrosswise;
    @BindView(R.id.layout_delete)
    RelativeLayout layoutDelete;
    @BindView(R.id.layout_progress_bg)
    LinearLayout layoutProgressBg;
    @BindView(R.id.tv_download)
    TextView tvDownload;
    @BindView(R.id.pdfView)
    PDFView pdfView;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.webView)
    ProgressWebview webView;




    @Override
    protected int getContentView() {
        return R.layout.activity_file_see;
    }

    @Override
    protected void initData() {
        initToobar(getIntent().getStringExtra("title"));
        handler.sendEmptyMessageDelayed(1, 50);
        pbCrosswise.setMax(100);
        String url = getIntent().getStringExtra("src");
        String prefix = url.substring(url.lastIndexOf("."));
        String src = FileHelper.getFileDefaultPath() + "/" + getIntent().getStringExtra("title") + prefix;
//        if(prefix.equals(".pdf"))
//        {
//            webView.setVisibility(View.VISIBLE);
//            webView.loadUrl("http://www.dzkonline.com/Uploads/word/zhongguoxiandaiwenxueshi/中国现代文学史/百代词曲之祖《菩萨蛮》.pdf");
//        }





        if (fileIsExists(src)) {
            if(prefix.equals(".pdf"))
            {
                pdfView.setVisibility(View.VISIBLE);
                tvNum.setVisibility(View.VISIBLE);
                pdfView.fromFile(new File(src))
                        .defaultPage(1)//默认展示第一页
                        .onPageChange(new OnPageChangeListener() {
                            @Override
                            public void onPageChanged(int page, int pageCount) {
                                tvNum.setText(page+"/"+pdfView.getPageCount());
                            }
                        }).load();
            }
            else
            {
                startActivity(getWordFileIntent(src));
            }
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {

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

    @OnClick({R.id.layout_delete, R.id.tv_download})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_delete:
                tvDownload.setVisibility(View.VISIBLE);
                tvDownload.setText("继续下载");
                layoutProgressBg.setVisibility(View.GONE);
                tvProgress.setVisibility(View.GONE);
                manager.stopTask(getIntent().getStringExtra("title") + getIntent().getStringExtra("id"));
                break;
            case R.id.tv_download:
                tvDownload.setVisibility(View.GONE);
                layoutProgressBg.setVisibility(View.VISIBLE);
                tvProgress.setVisibility(View.VISIBLE);
                int start = 0;
                for (int i = 0; i < listdata.size(); i++) {
                    if (listdata.get(i).getTaskID().equals(getIntent().getStringExtra("title") + getIntent().getStringExtra("id"))) {
                        start++;
                        break;
                    }
                }
                if (start == 0) {
                    String url = getIntent().getStringExtra("src");
                    String prefix = url.substring(url.lastIndexOf("."));
                    manager.addTask(getIntent().getStringExtra("title") + getIntent().getStringExtra("id"), Constants.ImagHost + url, getIntent().getStringExtra("title") + prefix);
                } else {
                    manager.startTask(getIntent().getStringExtra("title") + getIntent().getStringExtra("id"));
                }
                break;
        }
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
        }
    };



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
                    taskInfo.setDownFileSize(sqlDownLoadInfo.getDownloadSize());
                    taskInfo.setFileSize(sqlDownLoadInfo.getFileSize());
                    Log.e("===", "=====Size:" + sqlDownLoadInfo.getDownloadSize() + "======" + sqlDownLoadInfo.getFileSize());
                    double size = (double) sqlDownLoadInfo.getDownloadSize() / (double) sqlDownLoadInfo.getFileSize();
                    pbCrosswise.setProgress(((int) (size * 100)));
                    tvProgress.setText("下载进度:" + ((int) (size * 100)) + "%");
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
                String url = getIntent().getStringExtra("src");
                String prefix = url.substring(url.lastIndexOf("."));
                String src = FileHelper.getFileDefaultPath() + "/" + getIntent().getStringExtra("title") + prefix;
                if(prefix.equals(".pdf"))
                {
//
                        pdfView.setVisibility(View.VISIBLE);
                        tvNum.setVisibility(View.VISIBLE);
                        pdfView.fromFile(new File(src))
                                .defaultPage(1)//默认展示第一页
                                .onPageChange(new OnPageChangeListener() {
                                    @Override
                                    public void onPageChanged(int page, int pageCount) {
                                            tvNum.setText(page+"/"+pdfView.getPageCount());

                                    }
                                }).load();


//                    pdfView.setVisibility(View.VISIBLE);
//                    tvNum.setVisibility(View.VISIBLE);
//                    pdfView.fromFile(new File(src))
//                            //                .pages(0, 0, 0, 0, 0, 0) // 默认全部显示，pages属性可以过滤性显示
//                            .defaultPage(1)//默认展示第一页
//                            .onPageChange(new OnPageChangeListener() {
//                                @Override
//                                public void onPageChanged(int page, int pageCount) {
//                                    tvNum.setText(page+"/"+pdfView.getPageCount());
//                                }
//                            })
//                            .load();

//                    startActivity(getPDFFileIntent(src));
//                    finish();


                }
                else
                {
                    startActivity(getWordFileIntent(src));
                    finish();
                }

                break;
            }
        }





        @Override
        public void onError(SQLDownLoadInfo sqlDownLoadInfo) {
            //根据监听到的信息查找列表相对应的任务，停止该任务
            for (TaskInfo taskInfo : listdata) {
                if (taskInfo.getTaskID().equals(sqlDownLoadInfo.getTaskID())) {
                    ShowToast("下载错误");
                    tvDownload.setVisibility(View.VISIBLE);
                    tvDownload.setText("开始下载");
                    layoutProgressBg.setVisibility(View.GONE);
                    tvProgress.setVisibility(View.GONE);
                    pbCrosswise.setProgress(0);
                    break;
                }
            }
        }
    }





}
