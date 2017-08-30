package com.test.com.ThridFragemntActivity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.adapter.BaseRecyclerAdapter;
import com.test.com.adapter.BaseRecyclerHolder;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.entity.BaoMIngCuXiaoMa;
import com.test.com.entity.ZiXun;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CuXiaoMaActivity extends BaseToolbarActivity {


    @BindView(R.id.CuxiaoMaEditText)
    EditText mCuxiaoMaEditText;
    @BindView(R.id.tv_useCuXiaoMa)
    TextView mTvUseCuXiaoMa;
    @BindView(R.id.CuXiaoMaRecyclerView)
    RecyclerView mCuXiaoMaRecyclerView;
    private List<ZiXun> mList;
    @BindView(R.id.howToGetCuXiaoMa)
    Button howToGetCuXiaoMa;
    List<BaoMIngCuXiaoMa.DataBean.CuxiaoBean> cuxiaoBeanList = new ArrayList<>();
    private BaseRecyclerAdapter mAdapter;

    @Override
    protected int getContentView() {
        return R.layout.activity_cu_xiao_ma;
    }

    @Override
    protected void initData() {

        RequestParams params = new RequestParams();
        params.put("type", 1);
        getDataFromInternet(UrlFactory.RegistrationFee, params, 0);
        showLoadingDialog();


    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                if (object.getInteger("code") == 1) {
                    Log.e("TAG+++++++", object.toString());
                    Gson gson = new Gson();
                    BaoMIngCuXiaoMa cuXiaoMa = gson.fromJson(object.toString(), BaoMIngCuXiaoMa.class);

                    cuxiaoBeanList.addAll(cuXiaoMa.getData().getCuxiao());

                    setAdapter();
                }
                break;
            case 1:
                Gson gson = new Gson();
                Log.e("TAG++++", object.toString() + "+++");
                BaoMIngCuXiaoMa cuXiaoMa1 = gson.fromJson(object.toString(), BaoMIngCuXiaoMa.class);
                BaoMIngCuXiaoMa.DataBean.CuxiaoBean cuxiaoBean = cuXiaoMa1.getData().getCuxiao().get(0);
                Log.e("TAG++++", cuxiaoBean.toString() + "+++");
                cuxiaoBeanList.add(cuxiaoBeanList.size(), cuxiaoBean);
                mAdapter.notifyDataSetChanged();
                break;
        }

    }

    private void setAdapter() {
        mAdapter = new BaseRecyclerAdapter<BaoMIngCuXiaoMa.DataBean.CuxiaoBean>(this, cuxiaoBeanList, R.layout.item_cuxiaoma_use) {

            @Override
            public void convert(BaseRecyclerHolder holder, BaoMIngCuXiaoMa.DataBean.CuxiaoBean item, int position, boolean isScrolling) {
                holder.setText(R.id.Money, item.getMoney());
                holder.setText(R.id.daiJinQuanName, item.getName());
                holder.setText(R.id.DeadLine, "有效期:" + item.getStart_time() + "-" + item.getEnd_time());

            }
        };
        mCuXiaoMaRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("cuxiaoma", (Serializable) cuxiaoBeanList.get(position));
                setResult(4, intent);
                finish();
            }
        });
    }

    @Override
    protected void setListener() {
        mTvUseCuXiaoMa.setOnClickListener(this);
        howToGetCuXiaoMa.setOnClickListener(this);

    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
        isShowToolBar = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.howToGetCuXiaoMa:
                jumpToActivity(HowToGetCuXiaoMaActivity.class, false);

                break;
            case R.id.tv_useCuXiaoMa:/**使用促销码*/
                if (mCuxiaoMaEditText.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入促销码");
                } else {
                    RequestParams params = new RequestParams();
                    params.put("code", mCuxiaoMaEditText.getText().toString().trim());
                    getDataFromInternet(UrlFactory.cuxiaoCode, params, 1);
                    showLoadingDialog();
                }

                break;
        }

    }

    @Override
    protected void initView() {
        super.initView();
        initToobar("促销码", R.mipmap.fanhui);
        mCuXiaoMaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
