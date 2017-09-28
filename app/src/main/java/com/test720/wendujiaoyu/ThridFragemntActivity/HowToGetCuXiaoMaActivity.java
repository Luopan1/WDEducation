package com.test720.wendujiaoyu.ThridFragemntActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.UrlFactory;
import com.test720.wendujiaoyu.adapter.BaseRecyclerAdapter;
import com.test720.wendujiaoyu.adapter.BaseRecyclerHolder;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.entity.CuXiaoMa;
import com.test720.wendujiaoyu.entity.ZiXun;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.test720.wendujiaoyu.R.id.HowToGetRevylerView;


public class HowToGetCuXiaoMaActivity extends BaseToolbarActivity {


    @BindView(HowToGetRevylerView)
    RecyclerView mHowToGetRevylerView;
    private List<ZiXun> mList;
    private List<CuXiaoMa.DataBean> mDataLists;

    @Override
    protected int getContentView() {
        return R.layout.activity_how_to_get_cu_xiao_ma;
    }

    @Override
    protected void initData() {

        RequestParams params = new RequestParams();
        params.put("type", 2);
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
                holder.setText(R.id.title,(position+1)+"、"+item.getQuestion());
                holder.setText(R.id.content,item.getAnswer());

            }
        };
        mHowToGetRevylerView.setAdapter(adapter);
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
        initToobar("如何获取促销码", R.mipmap.fanhui);
        mHowToGetRevylerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
