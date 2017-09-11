package com.test.com.firstFragmentactivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.adapter.BaseRecyclerAdapter;
import com.test.com.adapter.BaseRecyclerHolder;
import com.test.com.adapter.ExamPlamListAdapter;
import com.test.com.application.MyApplication;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.entity.ExamPlanLists;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchPlanActivity extends BaseToolbarActivity {
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
    @BindView(R.id.titleRelative)
    RelativeLayout titleRelative;


    @BindView(R.id.majorName)
    TextView majorName;
    @BindView(R.id.majorCode)
    TextView majorCode;
    private ListView mPlanListView;

    private List<ExamPlanLists.PlanList> mLists;
    private BaseRecyclerAdapter mAdaper;
    private ExamPlamListAdapter mPlamListAdapter;


    @Override
    protected int getContentView() {
        return R.layout.activity_search_major;
    }

    @Override
    protected void initData() {
        mHotSearch = new ArrayList<>();
        mHotSearch.add(0, "会计");
        mHotSearch.add(1, "金融");
        mHotSearch.add(2, "英语");
        mHotSearch.add(3, "市场营销");
        mHotSearch.add(4, "工商管理");
        mHotSearch.add(5, "新闻学");

        id_flowlayout.setAdapter(new TagAdapter<String>(mHotSearch) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_textview, id_flowlayout, false);

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
                return true;
            }
        });


    }

    private void doSearch(String planName) {
        RequestParams params = new RequestParams();
        params.put("uuid", MyApplication.getDataBase().getUuid());
        params.put("majorName", planName);
        getDataFromInternet(UrlFactory.plan, params, 0);
        showLoadingDialog();

    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                mLists = new ArrayList<>();
                Gson gson = new Gson();
                if (object.getJSONObject("data").getJSONArray("planList").size() != 0) {

                    ExamPlanLists examPlanLists = gson.fromJson(object.toJSONString(), ExamPlanLists.class);
                    if (examPlanLists.getData().getPlanList().size() != 0) {
                        HotSearchRelative.setVisibility(View.INVISIBLE);
                        titleRelative.setVisibility(View.VISIBLE);
                        searchResultListview.setVisibility(View.VISIBLE);
                        mLists.addAll(examPlanLists.getData().getPlanList());
                        Log.e("TAG+++Mlist", mLists.toString());
                        ShowToast("查询成功");
                        majorCode.setText(examPlanLists.getData().getMajorInfo().getCode());
                        majorName.setText(examPlanLists.getData().getMajorInfo().getName());
                        setAdapter();
                    }
                } else {
                    mLists.clear();
                    //                    HotSearchRelative.setVisibility(View.VISIBLE);
                    titleRelative.setVisibility(View.INVISIBLE);
                    setAdapter();
                    ShowToast("没有此内容");
                }


                break;
        }

    }

    private void setAdapter() {

        searchResultListview.setLayoutManager(new LinearLayoutManager(this));
        /**
         * ReculerView里面的ListView的适配器
         */
        mAdaper = new BaseRecyclerAdapter<ExamPlanLists.PlanList>(this, mLists, R.layout.item_examplanlist) {
            @Override
            public void convert(BaseRecyclerHolder holder, ExamPlanLists.PlanList item, int position, boolean isScrolling) {
                mPlanListView = holder.getView(R.id.examPlanListView);
                /**
                 * ReculerView里面的ListView的适配器
                 */
                View view = LayoutInflater.from(SearchPlanActivity.this).inflate(R.layout.item_examheader, null);
                mPlanListView.addHeaderView(view);
                TextView time = (TextView) view.findViewById(R.id.time);
                time.setText(item.getStart_time() + "~" + item.getEnd_time());
                mPlamListAdapter = new ExamPlamListAdapter(item.getCourse_list(), SearchPlanActivity.this);
                mPlanListView.setAdapter(mPlamListAdapter);
            }

        };
        searchResultListview.setAdapter(mAdaper);

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
        setAdapter();

    }

    @Override
    protected void initBase() {
        isshowActionbar = false;
    }
}
