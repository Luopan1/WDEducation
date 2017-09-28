package com.test720.wendujiaoyu.secondFragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.WebViewActivity;
import com.test720.wendujiaoyu.adapter.CommonAdaper;
import com.test720.wendujiaoyu.adapter.ViewHolder;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.entity.ZiXunEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ZiKaoInformationActivity extends BaseToolbarActivity {
    @BindView(R.id.zikaoZiXunListView)
    ListView zikaoZiXunListView;
    private List<ZiXunEntity> mLists;


    @Override
    protected int getContentView() {
        return R.layout.activity_zi_kao_information;
    }

    @Override
    protected void initData() {
        mLists = new ArrayList<>();
        for(int i=0;i<10;i++){
            mLists.add(new ZiXunEntity("http://img2.woyaogexing.com/2017/07/31/83744dff1a5f2d80!350x350_big.png","2017年4月(17.1次)四川省高等教育自学考试通知","2017-05-20"));
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

        zikaoZiXunListView.setAdapter(adaper);
    }

    @Override
    protected void setListener() {
            zikaoZiXunListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle =new Bundle();
                    bundle.putString(WebViewActivity.TITLE,"自考资讯");
                    bundle.putString(WebViewActivity.URL,"https://x5.tencent.com/tbs/guide/sdkInit.html");
                    jumpToActivity(WebViewActivity.class,bundle,false);
                }
            });
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
        initToobar("自考资讯", R.mipmap.fanhui);
    }
}
