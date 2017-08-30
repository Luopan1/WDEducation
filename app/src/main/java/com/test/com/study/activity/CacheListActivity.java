package com.test.com.study.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.test.com.R;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.study.adapter.Adapter;
import com.test.com.study.bean.CurriculumClassificationListBean;
import com.test.com.study.bean.DownloadBean;
import com.test.com.study.utils.Player;
import com.test.com.study.utils.RecycleViewDivider;
import com.test.com.utills.Constants;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CacheListActivity extends BaseToolbarActivity implements Adapter.IonSlidingViewClickListener{
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
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
    private Adapter adapter;
    private List<CurriculumClassificationListBean> classificationListBeanArrayList  = new ArrayList<>();
    private Player player; // 播放器
    private int count = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_cache_list;
    }

    @Override
    protected void initData() {
        initToobar(getIntent().getStringExtra("name"));
        player = new Player(seeBar,tvStats,tvEnd);
        getDataList();
//        adapter = new BaseRecyclerAdapter<CurriculumClassificationListBean>(context, classificationListBeanArrayList, R.layout.item_curriculum_classification_list) {
//            @Override
//            public void convert(BaseRecyclerHolder holder, CurriculumClassificationListBean item, final int position, boolean isScrolling) {
//
//
//                holder.setText(R.id.tv_title, classificationListBeanArrayList.get(position).getName());
//                RelativeLayout layoutStats = holder.getView(R.id.layout_left);
//                layoutStats.setVisibility(View.GONE);
//
//            }
//        };

//        myRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        myRecyclerView.setLayoutManager(new LinearLayoutManager(myRecyclerView.getContext()));
        myRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        myRecyclerView.setAdapter(adapter = new Adapter(this,classificationListBeanArrayList));
    }

    @Override
        protected void setListener() {
        seeBar.setOnSeekBarChangeListener(new SeekBarChangeEvent());
//        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerView parent, View view, int position) {
//
//            }
//        });
    }

    @Override
    protected void initBase() {

    }

    private void getDataList()
    {
        String json = spUtils.get("src", Constants.uniqueness + "json", "").toString();
        if(!"".equals(json))
        {
            List<DownloadBean> downloadBeens = JSONArray.parseArray(json,DownloadBean.class);
            for (int i = 0; i < downloadBeens.size(); i++) {
                if(downloadBeens.get(i).getType().equals(getIntent().getStringExtra("id")))
                {
                    if(downloadBeens.get(i).getCourseType().equals(getIntent().getStringExtra("typeId")))
                    {
                        if(getIntent().getStringExtra("title").equals(downloadBeens.get(i).getFormat()))
                        {
                            CurriculumClassificationListBean curriculumClassificationListBean = new CurriculumClassificationListBean();
                            curriculumClassificationListBean.setName(downloadBeens.get(i).getTitle());
                            curriculumClassificationListBean.setVideo_src(downloadBeens.get(i).getSrc());
                            curriculumClassificationListBean.setId(downloadBeens.get(i).getId());
                            classificationListBeanArrayList.add(curriculumClassificationListBean);
                        }
                    }
                }
            }
        }
    }



    /*****************************************************
     * 播放音频
     *****************************************************/
    private void getStartPlayer(final int position) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                player.playUrl(classificationListBeanArrayList.get(position).getVideo_src());
            }
        }).start();
    }



    @OnClick({R.id.layout_play_left, R.id.layout_play_middle, R.id.layout_play_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_play_left://上一首
                if(classificationListBeanArrayList.size() == 0)
                    return;
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
                if(classificationListBeanArrayList.size() == 0)
                    return;
                if(classificationListBeanArrayList.size()-1 > count)
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

    @Override
    public void onItemClick(View view, int position) {
        Log.e("====","点击项："+position);
        String url = classificationListBeanArrayList.get(position).getVideo_src();
        String prefix=url.substring(url.lastIndexOf("."));
        if(prefix.equals(".mp4"))
        {
            startActivity(new Intent(mContext, VideoPlayActivity.class).putExtra("url", classificationListBeanArrayList.get(position).getVideo_src()).putExtra("title",classificationListBeanArrayList.get(position).getName()));
        }else
        {
            layoutBg.setVisibility(View.VISIBLE);
            count = position;
            getStartPlayer(count);
        }
    }

    @Override
    public void onDeleteBtnCilck(View view, int position) {
        Log.e("====","点击项："+position);
        String json = spUtils.get("src",Constants.uniqueness +  "json", "").toString();
        if(!"".equals(json)) {
            List<DownloadBean> downloadBeens = JSONArray.parseArray(json, DownloadBean.class);
            if(deleteFile(classificationListBeanArrayList.get(position).getVideo_src()))
            {
                for (int i = 0; i < downloadBeens.size(); i++) {
                    if(classificationListBeanArrayList.get(position).getName().equals(downloadBeens.get(i).getTitle()) && classificationListBeanArrayList.get(position).getId().equals(downloadBeens.get(i).getId()))
                    {
                        downloadBeens.remove(i);
                        break;
                    }
                }
                spUtils.put("src", Constants.uniqueness + "json", JSONArray.toJSONString(downloadBeens));
                adapter.removeData(position);
            }

        }

    }


    /**
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player = null;
        }
    }


}
