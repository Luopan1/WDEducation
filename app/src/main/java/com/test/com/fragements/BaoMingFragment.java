package com.test.com.fragements;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.test.com.BaomingCallBack;
import com.test.com.R;
import com.test.com.ThridFragemntActivity.ComfirmOrderActivity;
import com.test.com.UrlFactory;
import com.test.com.adapter.BaoMIngAdapter;
import com.test.com.baseUi.BaseFragment;
import com.test.com.entity.BaoMingUser;
import com.test.com.entity.Major;
import com.test.com.entity.School;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LuoPan on 2017/8/14 19:35.
 */

public class BaoMingFragment extends BaseFragment implements View.OnClickListener, BaomingCallBack {


    ListView baoMingRecyclerView;
    private RelativeLayout titleRelative;
    private List<BaoMingUser> mBmingUser;
    private List<String> mMlists = new ArrayList<>();
    private List<Major.Data> mMajorsLists = new ArrayList<>();
    private List<String> mMajorsListString = new ArrayList<>();
    private BaoMIngAdapter mAdapter;
    private List<BaoMingUser> mBaoMingUserList;

    @Override
    protected void initView() {
        titleRelative = getView(R.id.titleRelative);
        baoMingRecyclerView = getView(R.id.BaoMingRecyclerView);
    }

    @Override
    protected void initData() {
        mBmingUser = new ArrayList<>();
        mBmingUser.add(new BaoMingUser());
        RequestParams params = new RequestParams();
        getDataFromInternet(UrlFactory.SEARCHSCHOOL, params, 0);


    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                Gson gson = new Gson();
                School school = gson.fromJson(object.toJSONString(), School.class);
                List<School.Data> datas = school.getData();
                for (int i = 0; i < datas.size(); i++) {
                    mMlists.add(i, datas.get(i).getSc_name());
                }
                Log.e("TAG", mMlists.toString());
                getDataFromInternet(UrlFactory.searchMajor, new RequestParams(), 1);
                showLoadingDialog("  ");
                break;
            case 1:

                gson = new Gson();
                Major majors = gson.fromJson(object.toJSONString(), Major.class);
                mMajorsLists = majors.getData();
                for (int i = 0; i < mMajorsLists.size(); i++) {
                    mMajorsListString.add(i, mMajorsLists.get(i).getName());
                }
                setAdapter();
                break;
            case 2:
                if (object.getInteger("code") == 1) {
                    String price = object.getJSONObject("data").getString("price");
                    String did = object.getJSONObject("data").getString("did");
                    Bundle bundle = new Bundle();
                    bundle.putString("price", price);
                    bundle.putString("did", did);
                    bundle.putSerializable("data", (Serializable) mBaoMingUserList);
                    bundle.putInt("type",1);
                    jumpToActivity(ComfirmOrderActivity.class, bundle, false);
                }
                break;


        }

    }


    private void setAdapter() {


        mAdapter = new BaoMIngAdapter(getActivity(), mBmingUser, mMlists, mMajorsListString, this);

        baoMingRecyclerView.setAdapter(mAdapter);
    }


    protected void setListener() {


    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragemnt_baoming;
    }

    public void onClick(View v) {
        switch (v.getId()) {

        }

    }

    @Override
    public void CallBack(List<BaoMingUser> lists) {
        mBaoMingUserList = new ArrayList<>();
        mBaoMingUserList.addAll(lists);
        RequestParams params = new RequestParams();
        params.put("list", JSONObject.toJSONString(lists));
        Log.e("TAG++++params", params.toString());
        getDataFromInternet(UrlFactory.signUp, params, 2);
        showLoadingDialog("");
    }
}
