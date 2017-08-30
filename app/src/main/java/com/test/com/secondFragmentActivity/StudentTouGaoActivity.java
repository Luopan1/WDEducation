package com.test.com.secondFragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.test.com.R;
import com.test.com.WebViewActivity;
import com.test.com.adapter.CommonAdaper;
import com.test.com.adapter.ViewHolder;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.entity.ZiXunEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class StudentTouGaoActivity extends BaseToolbarActivity {
    @BindView(R.id.mineAdavantageListView)
    ListView mineAdavantageListView;
    private List<ZiXunEntity> mLists;

    @Override
    protected int getContentView() {
        return R.layout.activity_mine_advantage;
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();
        for(int i=0;i<5;i++){
            mLists.add(new ZiXunEntity("http://img2.woyaogexing.com/2017/07/31/f43b2a8a6dd85057!250x250.png","自考心得...","5424"));
        }
        setAdapter();

    }

    private void setAdapter() {
        CommonAdaper adaper=new CommonAdaper<ZiXunEntity>(this,mLists,R.layout.item_zikaozixun) {

            @Override
            public void convert(ViewHolder holder, ZiXunEntity item, int position) {
                holder.setImageByUrl(R.id.newImage,item.getImageUrl());
                holder.setText(R.id.Title,item.getTitle());
                holder.setText(R.id.sendTime,item.getGetGrade());
            }
        };

        mineAdavantageListView.setAdapter(adaper);

    }

    @Override
    protected void setListener() {
        mineAdavantageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle =new Bundle();
                bundle.putString(WebViewActivity.TITLE,"自考心得");
                bundle.putString(WebViewActivity.URL,"https://baike.baidu.com/item/%E7%90%85%E7%90%8A%E6%A6%9C/2135131?fr=aladdin");
                jumpToActivity(WebViewActivity.class,bundle,false);
            }
        });

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
        initToobar("学员投稿",R.mipmap.fanhui);
    }
}
