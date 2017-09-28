package com.test720.wendujiaoyu.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.UrlFactory;
import com.test720.wendujiaoyu.application.MyApplication;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.utills.Constants;
import com.test720.wendujiaoyu.utills.SPUtils;

import butterknife.BindView;

public class WeChatLoginActivity extends BaseToolbarActivity {


    @BindView(R.id.userCount)
    EditText mUserCount;
    @BindView(R.id.userPWD)
    EditText mUserPWD;
    @BindView(R.id.forgetPwd)
    TextView mForgetPwd;
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.clearPwd)
    ImageView clearPwd;

    @Override
    protected int getContentView() {
        return R.layout.activity_we_chat_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        super.initView();
        initToobar("绑定账号", R.mipmap.fanhui);
    }

    @Override
    protected void setListener() {
        mBtLogin.setOnClickListener(this);
        mForgetPwd.setOnClickListener(this);
        clearPwd.setOnClickListener(this);
    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clearPwd:
                mUserPWD.setText("");
                break;
            case R.id.bt_login:
                if (mUserCount.getText().toString().trim().isEmpty() | mUserPWD.getText().toString().trim().isEmpty()) {
                    ShowToast("账号或者密码不能为空");
                } else if (isCorrentPasswrold(mUserPWD)) {
                    ShowToast("密码长度在6~18位");
                } else {
                    login();
                    //
                }

                break;
            case R.id.forgetPwd:
                jumpToActivity(ForgetPassWorldActivity.class, false);

                break;

        }
    }

    void login() {
        // TODO: 2017/8/18  加上微信的id参数
        RequestParams params = new RequestParams();
        params.put("phone", mUserCount.getText().toString().trim());
        params.put("header", Constants.header);
        params.put("nickname", Constants.nickName);
        params.put("password", mUserPWD.getText().toString().trim());
        params.put("unionid", MyApplication.getDataBase().getUnionid());
        getDataFromInternet(UrlFactory.weChatBindPhone, params, 0);
        showLoadingDialog();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        // TODO: 2017/8/19 有账号绑定账号回来  应该有一个token
        super.getSuccess(object, what);
        ShowToast(object.getString("msg"));
        Log.i("TAG+++", object.getString("msg"));
        MyApplication.getDataBase().setToken(object.getJSONObject("data").getString("token"));

       /* String uuid = object.getJSONObject("data").getString("uuid");
        MyApplication.userBaseInfo.setUuid(uuid);
        Constants.uniqueness=uuid;*/

        SPUtils.setUnionid(MyApplication.getDataBase().getUnionid());

        jumpToActivity(MainActivity.class, true);

    }
}
