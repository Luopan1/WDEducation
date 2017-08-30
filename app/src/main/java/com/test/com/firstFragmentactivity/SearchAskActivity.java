package com.test.com.firstFragmentactivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.entity.Question;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchAskActivity extends BaseToolbarActivity {
    @BindView(R.id.cancle)
    TextView cancle;
    @BindView(R.id.searchEdiText)
    EditText searchEdiText;
    @BindView(R.id.searchResultRecylcerView)
    RecyclerView searchResultRecylcerView;
    List<Question> lists = new ArrayList<>();
    /**
     * 当期页数
     */
    private int mCurrentPage = 1;

    /**
     * 总页数
     */

    private static int mTotalPage = 0;
    private QuickAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_search_ask;
    }

    @Override
    protected void initData() {
        mAdapter = new QuickAdapter();
        searchResultRecylcerView.setLayoutManager(new LinearLayoutManager(this));
        searchResultRecylcerView.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                doSearch();
            }
        });

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("id", lists.get(position).getId());
                jumpToActivity(AskAndAnwserInfoActivity.class, bundle, false);
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
                    if (searchEdiText.getText().toString().trim().isEmpty()) {
                        ShowToast("请输入搜索内容");
                    }else {
                        doSearch();
                    }
                    return true;
                }
                return false;

            }
        });
    }


    private void doSearch() {

        RequestParams params = new RequestParams();
        params.put("searchCondition", searchEdiText.getText().toString().trim());
        params.put("page", mCurrentPage);
        getDataFromInternet(UrlFactory.answerLists, params, 0);
        showLoadingDialog();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        JSONObject data = object.getJSONObject("data");
        JSONArray array = data.getJSONArray("list");
        if (array.size() == 0) {
            ShowToast("没有此问题");
            if(lists.size()!=0){
                lists.clear();
                mAdapter.notifyDataSetChanged();
            }
            return;
        }
        lists.addAll(array.toJavaList(Question.class));
        mTotalPage = data.getInteger("total");
        mAdapter.loadMoreComplete();
        mAdapter.notifyDataSetChanged();
        mCurrentPage++;
        if (mCurrentPage > mTotalPage) {
            mAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancle:
                finish();
                break;
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initBase() {
        isshowActionbar = false;
    }

    public class QuickAdapter extends BaseQuickAdapter<Question, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_asklistview, lists);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Question item) {
            viewHolder.setText(R.id.askTitle, item.getTitle());
            viewHolder.setText(R.id.loveMan, item.getLovemun());
            Glide.with(SearchAskActivity.this).load(item.touxiang).into((ImageView) viewHolder.getView(R.id.anwserPhotoImage));
        }
    }
}
