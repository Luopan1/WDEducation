package com.test720.wendujiaoyu.fragements;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.test720.wendujiaoyu.BaomingCallBack;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.ThridFragemntActivity.ComfirmOrderActivity;
import com.test720.wendujiaoyu.UrlFactory;
import com.test720.wendujiaoyu.adapter.BaoMIngAdapter;
import com.test720.wendujiaoyu.baseUi.BaseFragment;
import com.test720.wendujiaoyu.entity.BaoMingUser;
import com.test720.wendujiaoyu.entity.Major;
import com.test720.wendujiaoyu.entity.School;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Unbinder;

/**
 * Created by LuoPan on 2017/8/14 19:35.
 */

public class BaoMingFragment extends BaseFragment implements View.OnClickListener, BaomingCallBack {


    ListView baoMingRecyclerView;
    TextView mTelePhoneNumber;
    TextView mQQNumber;
    Unbinder unbinder;
    private RelativeLayout titleRelative;
    private List<BaoMingUser> mBmingUser;
    private List<String> mMlists = new ArrayList<>();
    private List<Major.Data> mMajorsLists = new ArrayList<>();
    private List<String> mMajorsListString = new ArrayList<>();
    private BaoMIngAdapter mAdapter;
    private List<BaoMingUser> mBaoMingUserList;
    RelativeLayout Relative;

    @Override
    protected void initView() {
        Relative=getView(R.id.Relative);
        titleRelative = getView(R.id.titleRelative);
        baoMingRecyclerView = getView(R.id.BaoMingRecyclerView);
        mTelePhoneNumber=getView(R.id.TelePhoneNumber);
        mQQNumber=getView(R.id.QQNumber);
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
                    bundle.putInt("type", 1);
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
        mTelePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                Uri data = Uri.parse("tel:" + mTelePhoneNumber.getText().toString().trim());
                intent.setData(data);
                startActivity(intent);
            }
        });
        mQQNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cmb = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(mQQNumber.getText().toString().trim());
                ShowToast("QQ号复制成功");
            }
        });
        Relative.setOnClickListener(this);
    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragemnt_baoming;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Relative:

                break;
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
