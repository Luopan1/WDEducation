package com.test720.wendujiaoyu.firstFragmentactivity;

import android.view.View;
import android.widget.ListView;

import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.adapter.CommonAdaper;
import com.test720.wendujiaoyu.adapter.ViewHolder;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.entity.ZiXunEntity;

import java.util.ArrayList;

import butterknife.BindView;
public class NewsActivity extends BaseToolbarActivity {
    @BindView(R.id.newsListView)
    ListView newsListView;
    private ArrayList<ZiXunEntity> mLists;

    @Override
    protected int getContentView() {
        return R.layout.activity_news;
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            mLists.add(new ZiXunEntity("", "2017年下半年网络注册学习报名已经开始了，现在报名十月考试可以加30分哦！", "2017-05-20  14:20"));
        }
        setAdapter();



    }






    private void setAdapter() {
        CommonAdaper adapter = new CommonAdaper<ZiXunEntity>(this, mLists, R.layout.item_news_listsview) {

            @Override
            public void convert(ViewHolder holder, ZiXunEntity item, int position) {
                holder.setText(R.id.newsTitle, item.getTitle());
                holder.setText(R.id.newsSendTime, item.getGetGrade());
            }

        };
        newsListView.setAdapter(adapter);
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

}
