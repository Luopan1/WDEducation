package com.test720.wendujiaoyu.study.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.study.bean.DownloadBean;
import com.test720.wendujiaoyu.study.bean.FileFormatBean;
import com.test720.wendujiaoyu.study.utils.BaseRecyclerAdapter;
import com.test720.wendujiaoyu.study.utils.BaseRecyclerHolder;
import com.test720.wendujiaoyu.study.utils.ItemAnimatorFactory;
import com.test720.wendujiaoyu.study.utils.SpacesItemDecoration;
import com.test720.wendujiaoyu.utills.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FileFormatActivity extends BaseToolbarActivity {
    @BindView(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    private BaseRecyclerAdapter<FileFormatBean> adapter;
    private List<FileFormatBean> fileFormatBeanList = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_file_format;
    }

    @Override
    protected void initData() {
        initToobar("我的缓存");
        adapter = new BaseRecyclerAdapter<FileFormatBean>(context, fileFormatBeanList, R.layout.item_major_choice) {
            @Override
            public void convert(BaseRecyclerHolder holder, FileFormatBean item, int position, boolean isScrolling) {
//                int tolt = sizeUtils.screenWidth()/2-30;
//                sizeUtils.setLayoutSizeWidht(holder.getView(R.id.layout_bg),tolt);
//                sizeUtils.setLayoutSize(holder.getView(R.id.layout_within),tolt,tolt);
//                sizeUtils.setLayoutSizeHeight(holder.getView(R.id.iv_icon),((int)(tolt/3.5))*2);
                sizeUtils.setTextSize((TextView) holder.getView(R.id.tv_title),20);
                holder.setText(R.id.tv_title,fileFormatBeanList.get(position).getFormat());
            }
        };
        myRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false));
        myRecyclerView.setItemAnimator(ItemAnimatorFactory.slidein());
        myRecyclerView.addItemDecoration(new SpacesItemDecoration(2, 0, 0, 7, 3));
        myRecyclerView.setAdapter(adapter);
        getDataList();
    }

    @Override
    protected void setListener() {
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                startActivity(new Intent(mContext,CacheCurriculumListActivity.class).putExtra("title",fileFormatBeanList.get(position).getFormat().equals("视频课程")?".mp4":".mp3"));
            }
        });
    }

    @Override
    protected void initBase() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataList();
    }

    private void getDataList()
    {
        fileFormatBeanList.clear();
        String json = spUtils.get("src", Constants.uniqueness + "json", "").toString();
        if(!"".equals(json))
        {
            List<DownloadBean> downloadBeens = JSONArray.parseArray(json,DownloadBean.class);
            int video = 0;
            int audio = 0;
            for (int i = 0; i < downloadBeens.size(); i++) {
                if(downloadBeens.get(i).getFormat().equals(".mp4"))
                    video++;
                else
                    audio++;
            }
            if(video > 0)
            {
                FileFormatBean fileFormatBean = new FileFormatBean();
                fileFormatBean.setFormat("视频课程");
                fileFormatBeanList.add(fileFormatBean);
            }
            if(audio > 0)
            {
                FileFormatBean fileFormatBean = new FileFormatBean();
                fileFormatBean.setFormat("音频课程");
                fileFormatBeanList.add(fileFormatBean);
            }
        }
        adapter.notifyDataSetChanged();
    }

}
