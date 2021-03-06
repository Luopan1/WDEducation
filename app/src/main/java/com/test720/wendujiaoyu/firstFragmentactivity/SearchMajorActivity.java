package com.test720.wendujiaoyu.firstFragmentactivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.UrlFactory;
import com.test720.wendujiaoyu.WebViewActivity;
import com.test720.wendujiaoyu.adapter.BaseRecyclerAdapter;
import com.test720.wendujiaoyu.adapter.BaseRecyclerHolder;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.entity.MasjorLists;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchMajorActivity extends BaseToolbarActivity {
    @BindView(R.id.cancle)
    TextView cancle;
    @BindView(R.id.searchEdiText)
    EditText searchEdiText;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout id_flowlayout;
    private List<String> mHotSearch;
    @BindView(R.id.searchResultListview)
    RecyclerView searchResultListview;
    @BindView(R.id.HotSearchRelative)
    RelativeLayout HotSearchRelative;
    private List<MasjorLists> mList;
    private BaseRecyclerAdapter mAdapter;


    @Override
    protected int getContentView() {
        return R.layout.activity_search_major2;
    }

    @Override
    protected void initData() {
        mHotSearch = new ArrayList<>();
        mHotSearch.add(0, "会计");
        mHotSearch.add(1, "英语");
        mHotSearch.add(2, "小学教育");
        mHotSearch.add(3, "汉语言文学");
        mHotSearch.add(4, "学前教育");
        mHotSearch.add(5, "汉语言文学教育");
        mHotSearch.add(6,"市场营销");

        id_flowlayout.setAdapter(new TagAdapter<String>(mHotSearch) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_textview,
                        id_flowlayout, false);

                tv.setText(s);
                return tv;
            }
        });

    }

    @Override
    protected void setListener() {
        cancle.setOnClickListener(this);
        searchEdiText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //TODO   搜索
                    if (!searchEdiText.getText().toString().trim().isEmpty()) {
                        doSearch(searchEdiText.getText().toString().trim());
                    } else {
                        ShowToast("请输入搜索内容");
                    }

                    return true;
                }
                return false;

            }
        });

        id_flowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {
                doSearch(mHotSearch.get(position));
                Log.e("TAG++++",mHotSearch.get(position));
                return true;
            }
        });

    }

    private void doSearch(String text) {
        RequestParams params = new RequestParams();
        params.put("searchCondition", text);
        getDataFromInternet(UrlFactory.majorLists, params, 0);
        showLoadingDialog();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        mList = new ArrayList<>();
        if (object.getJSONObject("data").getJSONArray("list").size() != 0) {
            JSONObject data = object.getJSONObject("data");
            JSONArray array = data.getJSONArray("list");
            Log.i("TAG", array.size() + "");
            HotSearchRelative.setVisibility(View.INVISIBLE);
            searchResultListview.setVisibility(View.VISIBLE);
            mList = array.toJavaList(MasjorLists.class);
            Log.e("TAG+++++", mList.toString());
            setAdapter();
        } else {
            ShowToast("暂无此专业");
            mList.clear();
            setAdapter();
        }

    }

    private void setAdapter() {
        mAdapter = new BaseRecyclerAdapter<MasjorLists>(this, mList, R.layout.item_mejorlistview) {

            @Override
            public void convert(BaseRecyclerHolder holder, MasjorLists item, int position, boolean isScrolling) {
                holder.setText(R.id.title, item.getName());
                holder.setText(R.id.imageUrl, "(" + item.getCode() + ")");
                holder.setText(R.id.studyMethod, "主考院校：" + item.getSchool());

            }
        };

        searchResultListview.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.URL, UrlFactory.searchMajorInfo + "/id/" + mList.get(position).getId());
                bundle.putString(WebViewActivity.TITLE, mList.get(position).getName() + "详情");
                jumpToActivity(WebViewActivity.class, bundle, false);
            }
        });

    }

    @Override
    protected void initBase() {
        isshowActionbar = false;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancle:
                finish();
                break;
        }

    }

    @Override
    protected void initView() {
        searchResultListview.setLayoutManager(new LinearLayoutManager(this));

    }
}
