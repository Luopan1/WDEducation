package com.test720.wendujiaoyu.fourthFragementActivity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.adapter.BaseRecyclerAdapter;
import com.test720.wendujiaoyu.adapter.BaseRecyclerHolder;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.baseUi.SpaceItemDecoration;
import com.test720.wendujiaoyu.entity.ZiXunEntity;
import com.test720.wendujiaoyu.utills.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CacheActivity extends BaseToolbarActivity {
    @BindView(R.id.VideoCacheRecylerView)
    RecyclerView VideoCacheRecylerView;
    private List<ZiXunEntity> mLists;
    private BaseRecyclerAdapter mAdapter;


    @Override
    protected int getContentView() {
        return R.layout.activity_video_cache;
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();
        mLists.add(new ZiXunEntity(0, "视频缓存", "http://pic95.huitu.com/res/20170404/751350_20170404012521326011_1.jpg"));
        mLists.add(new ZiXunEntity(0, "音频缓存", "http://pic95.huitu.com/res/20170404/751350_20170404012521326011_1.jpg"));
        setAdapter();
    }

    private void setAdapter() {
        mAdapter = new BaseRecyclerAdapter<ZiXunEntity>(this, mLists, R.layout.item_videocache_recylerview) {

            @Override
            public void convert(BaseRecyclerHolder holder, ZiXunEntity item, int position, boolean isScrolling) {
                holder.setImageByUrl(R.id.videoPlayImage, item.getGetGrade());
                holder.setText(R.id.title, item.getTitle());
            }
        };
        VideoCacheRecylerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                // TODO: 2017/8/10
                if (position == 0) {
                    jumpToActivity(VideoCacheActivity.class, false);
                }
                else {
                    jumpToActivity(VideoInfoActivity.class, false);
                }
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initView() {
        initToobar("缓存列表", R.mipmap.fanhui);
        VideoCacheRecylerView.setLayoutManager(new GridLayoutManager(this, 2));
        VideoCacheRecylerView.addItemDecoration(new SpaceItemDecoration(DensityUtil.dip2px(getApplicationContext(), 10), DensityUtil.dip2px(getApplicationContext(), 10)));
    }
}
