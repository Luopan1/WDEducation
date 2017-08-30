package com.test.com.ThridFragemntActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.adapter.BaseRecyclerAdapter;
import com.test.com.adapter.BaseRecyclerHolder;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.entity.CuXiaoMa;
import com.test.com.entity.ZiXun;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HowToGetDaijinquanActivity extends BaseToolbarActivity {
    @BindView(R.id.HowToGetRevylerView)
    RecyclerView HowToGetRevylerView;
    private List<ZiXun> mList;
    private List<CuXiaoMa.DataBean> mDataLists;

    @Override
    protected int getContentView() {
        return R.layout.activity_how_to_get_daijinquan;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.put("type", 1);
        getDataFromInternet(UrlFactory.voucher, params, 0);
        showLoadingDialog();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        if (object.getInteger("code") == 1) {
            Log.e("TAG", object.toString());
            Gson gson = new Gson();
            CuXiaoMa cuXiaoMa = gson.fromJson(object.toString(), CuXiaoMa.class);
            mDataLists = new ArrayList<>();
            mDataLists.addAll(cuXiaoMa.getData());
            setAdapter();
        }
    }

    private void setAdapter() {
        BaseRecyclerAdapter adapter = new BaseRecyclerAdapter<CuXiaoMa.DataBean>(this, mDataLists, R.layout.item_hpwtoget) {

            @Override
            public void convert(BaseRecyclerHolder holder, CuXiaoMa.DataBean item, int position, boolean isScrolling) {
                holder.setText(R.id.title, (position + 1) + "、" + item.getQuestion());
                holder.setText(R.id.content, item.getAnswer());
            }
        };
        HowToGetRevylerView.setAdapter(adapter);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
        isShowToolBar = true;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initView() {
        initToobar("如何获取代金券", R.mipmap.fanhui);
        HowToGetRevylerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
