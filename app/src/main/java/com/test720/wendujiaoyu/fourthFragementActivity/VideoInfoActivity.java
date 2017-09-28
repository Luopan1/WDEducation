package com.test720.wendujiaoyu.fourthFragementActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.adapter.BaseRecyclerAdapter;
import com.test720.wendujiaoyu.adapter.BaseRecyclerHolder;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.entity.ZiXunEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoInfoActivity extends BaseToolbarActivity {
    @BindView(R.id.videoInfoListView)
    LRecyclerView videoInfoListView;
    private List<ZiXunEntity> mLists;
    private BaseRecyclerAdapter mAdapter;


    @Override
    protected int getContentView() {
        return R.layout.activity_video_info;
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();
        for(int i=0;i<5;i++){
           mLists.add(new ZiXunEntity("中国近现代史精讲班第3课"));
        }
            setAdapter();
    }

    private void setAdapter() {
        mAdapter = new BaseRecyclerAdapter<ZiXunEntity>(this,mLists, R.layout.item_videoinfo_listview1) {

            @Override
            public void convert(BaseRecyclerHolder holder, ZiXunEntity item, final int position, boolean isScrolling) {
                holder.setText(R.id.videTitle,item.getTitle());
                holder.getView(R.id.delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLists.remove(position);
                        mAdapter.notifyDataSetChanged();
                        ShowToast("点击删除了");
                    }
                });
                holder.getView(R.id.content).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShowToast("你点击了第"+position+"个");
                    }
                });
            }
        };
      /*  CommonAdaper adaper=new CommonAdaper<ZiXunEntity>(this,mLists,R.layout.item_videoinfo_listview) {

            @Override
            public void convert(ViewHolder holder, ZiXunEntity item, int position) {
                holder.setText(R.id.videTitle,item.getTitle());
            }
        };*/
        LRecyclerViewAdapter adapter=new LRecyclerViewAdapter(mAdapter);
        videoInfoListView.setAdapter(adapter);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isshowActionbar=true;

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initView() {
        initToobar("中国近现代史",R.mipmap.fanhui);
        videoInfoListView.setLayoutManager(new LinearLayoutManager(this));
        videoInfoListView.setPullRefreshEnabled(false);

    }
}
