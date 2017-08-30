package com.test.com.firstFragmentactivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ExamPlanActivity extends BaseToolbarActivity {
    @BindView(R.id.examPlanRecylerView)
    RecyclerView examPlanRecylerView;
    @BindView(R.id.imageRight)
    ImageView rightView;
    @BindView(R.id.majorName)
    TextView majorName;
    @BindView(R.id.majorCode)
    TextView majorCode;
    @BindView(R.id.noData)
    TextView noData;

    private ListView mPlanListView;
    private List<ExamPlanLists.PlanList> mLists;
    private List<ExamPlanLists.Course_list> mCourse_lists;

    @Override
    protected int getContentView() {
        return R.layout.activity_exam_plan;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.put("uuid", MyApplication.getDataBase().getUuid());
        getDataFromInternet(UrlFactory.plan, params, 0);
        showLoadingDialog();
    }

    private void setAdapter() {
        examPlanRecylerView.setLayoutManager(new LinearLayoutManager(this));
        BaseRecyclerAdapter adaper = new BaseRecyclerAdapter<ExamPlanLists.PlanList>(this, mLists, R.layout.item_examplanlist) {

            @Override
            public void convert(BaseRecyclerHolder holder, ExamPlanLists.PlanList item, int position, boolean isScrolling) {
                mPlanListView = holder.getView(R.id.examPlanListView);
                /**
                 * ReculerView里面的ListView的适配器
                 */
                View view = LayoutInflater.from(ExamPlanActivity.this).inflate(R.layout.item_examheader, null);
                mPlanListView.addHeaderView(view);
                TextView time = (TextView) view.findViewById(R.id.time);
                time.setText(item.getStart_time() + "~" + item.getEnd_time());
                mPlanListView.setAdapter(new ExamPlamListAdapter(item.getCourse_list(), ExamPlanActivity.this));
            }

        };
        examPlanRecylerView.setAdapter(adaper);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                mLists = new ArrayList<>();
                ShowToast(object.getString("msg"));
                Gson gson = new Gson();
                if (object.getJSONObject("data").getJSONArray("planList").size() != 0) {
                    ExamPlanLists examPlanLists = gson.fromJson(object.toJSONString(), ExamPlanLists.class);
                    mLists.addAll(examPlanLists.getData().getPlanList());
                    if (examPlanLists.getData().getPlanList().size() == 0) {

                    } else {
                        if (mLists.size() == 0) {
                            noData.setVisibility(View.VISIBLE);
                            examPlanRecylerView.setVisibility(View.INVISIBLE);
                        } else {
                            noData.setVisibility(View.INVISIBLE);
                            examPlanRecylerView.setVisibility(View.VISIBLE);
                        }
                        majorCode.setText(examPlanLists.getData().getMajorInfo().getCode());
                        majorName.setText(examPlanLists.getData().getMajorInfo().getName());
                        setAdapter();
                    }
                }
                else {
                    doSearch();
                }
                break;
            case 1:
                gson = new Gson();
                if (object.getJSONObject("data").getJSONArray("planList").size() != 0) {
                    ExamPlanLists examPlanLists = gson.fromJson(object.toJSONString(), ExamPlanLists.class);
                    mLists.addAll(examPlanLists.getData().getPlanList());
                    if (mLists.size() == 0) {
                        noData.setVisibility(View.VISIBLE);
                        examPlanRecylerView.setVisibility(View.INVISIBLE);
                    } else {
                        noData.setVisibility(View.INVISIBLE);
                        examPlanRecylerView.setVisibility(View.VISIBLE);

                    }
                    majorCode.setText(examPlanLists.getData().getMajorInfo().getCode());
                    majorName.setText(examPlanLists.getData().getMajorInfo().getName());
                    setAdapter();
                }
                break;
        }
    }

    private void doSearch() {
        RequestParams params = new RequestParams();
        params.put("uuid", MyApplication.getDataBase().getUuid());
        params.put("majorName", "会计");
        getDataFromInternet(UrlFactory.plan, params, 1);

    }

    @Override
    protected void setListener() {
        rightView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageRight:
                jumpToActivity(SearchPlanActivity.class, false);
                break;
        }

    }

    @Override
    protected void initView() {
        initToobar(R.mipmap.fanhui, "考试计划", R.mipmap.sousuo);

    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
    }


}
