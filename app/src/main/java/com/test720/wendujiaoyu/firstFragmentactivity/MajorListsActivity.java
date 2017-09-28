package com.test720.wendujiaoyu.firstFragmentactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.UrlFactory;
import com.test720.wendujiaoyu.WebViewActivity;
import com.test720.wendujiaoyu.adapter.CommonAdaper;
import com.test720.wendujiaoyu.adapter.ViewHolder;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.entity.MasjorLists;

import java.util.List;

import butterknife.BindView;

public class MajorListsActivity extends BaseToolbarActivity {
    @BindView(R.id.majorListView)
    ListView majorListView;
    @BindView(R.id.imageRight)
    ImageView rightImage;
    private List<MasjorLists> mList;


    @Override
    protected int getContentView() {
        return R.layout.activity_major_lists;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        getDataFromInternet(UrlFactory.majorLists, params, 0);
        showLoadingDialog();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        JSONObject data = object.getJSONObject("data");
        JSONArray array = data.getJSONArray("list");
        Log.i("TAG", array.size() + "");
        mList = array.toJavaList(MasjorLists.class);
        setAdapter();
        Log.e("TAG+++", mList.toString());
    }

    private void setAdapter() {
        CommonAdaper adapter = new CommonAdaper<MasjorLists>(this, mList, R.layout.item_mejorlistview) {

            @Override
            public void convert(ViewHolder holder, MasjorLists item, int position) {

                holder.setText(R.id.title, item.getName());
                holder.setText(R.id.imageUrl, "(" + item.getCode() + ")");
                holder.setText(R.id.studyMethod, "主考院校：" + item.getSchool());

            }
        };
        majorListView.setAdapter(adapter);

    }

    @Override
    protected void setListener() {
        rightImage.setOnClickListener(this);

        majorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putString(WebViewActivity.URL,UrlFactory.searchMajorInfo+"/id/"+mList.get(position).getId());
                bundle.putString(WebViewActivity.TITLE,mList.get(position).getName()+"详情");
                jumpToActivity(WebViewActivity.class,bundle,false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageRight:
                jumpToActivity(SearchMajorActivity.class, false);
                break;
        }
    }

    @Override
    protected void initView() {
        initToobar(R.mipmap.fanhui, "专业", R.mipmap.sousuo);
    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
    }
}
