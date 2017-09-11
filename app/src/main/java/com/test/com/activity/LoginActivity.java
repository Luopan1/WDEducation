package com.test.com.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.application.MyApplication;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.utills.Constants;
import com.test.com.utills.SPUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;

public class LoginActivity extends BaseToolbarActivity {
    @BindView(R.id.forgetPwd)
    TextView forgetPwd;
    @BindView(R.id.weixinLogin)
    RelativeLayout weixinLogin;

    private ImageView mClearPWD;
    private EditText mUserPWD;
    private EditText mUserCount;
    private Button mBt_login;
    private TextView mRegiest;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        mClearPWD.setOnClickListener(this);
        mBt_login.setOnClickListener(this);
        mRegiest.setOnClickListener(this);
        forgetPwd.setOnClickListener(this);
    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
    }

    @Override
    protected void initView() {
        initToobar("登录", R.mipmap.fanhui);
        mClearPWD = (ImageView) findViewById(R.id.clearPwd);
        mUserPWD = (EditText) findViewById(R.id.userPWD);
        mUserCount = (EditText) findViewById(R.id.userCount);
        mBt_login = (Button) findViewById(R.id.bt_login);
        mRegiest = (TextView) findViewById(R.id.regiest);
        weixinLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.regiest:
                jumpToActivity(RegiestActivity1.class, false);

                break;
            case R.id.forgetPwd:
                jumpToActivity(ForgetPassWorldActivity.class, false);
                break;
            case R.id.weixinLogin:
                UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;


        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
            showLoadingDialog();
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            try {
                cancleLoadingDialog();

                String unionid = data.get("uid");
                Log.e("TAG++onComplete", unionid + "+++");
                MyApplication.getDataBase().setUnionid(unionid);
                SPUtils.setUnionid(unionid);
                Log.e("TAG+getUUId", SPUtils.getUnionid() + "+++");
                weChatLoginResult(unionid);

                //// TODO: 2017/8/18  没有账号 跳转到微信LoginActivity，有账号  跳转到登录界面 去绑定手机号
                Log.e("TAG++onComplete", data.toString());
            } catch (Exception e) {
                ShowToast("出错了 请稍候再试");
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            cancleLoadingDialog();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            cancleLoadingDialog();
        }
    };

    private void weChatLoginResult(String unionid) {
        RequestParams params = new RequestParams();
        params.put("unionid", unionid);
        SPUtils.setUnionid(unionid);
        getDataFromInternet(UrlFactory.appWeChatLogin, params, 1);

    }

    void login() {
        RequestParams params = new RequestParams();
        params.put("username", mUserCount.getText().toString().trim());
        params.put("password", mUserPWD.getText().toString().trim());
        getDataFromInternet(UrlFactory.Login, params, 0);
        showLoadingDialog();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                ShowToast(object.getString("msg"));
                if (object.getInteger("code").equals(1)) {
                    String token = object.getJSONObject("data").getString("token");
                    String uuid = object.getJSONObject("data").getString("uuid");



                    MyApplication.userBaseInfo.setUuid(uuid);
                    Constants.uniqueness=uuid;
                    Log.e("login", object.toString());


                   /* // TODO: 2017/8/30
                    spUtils.put("jihuo", Constants.uniqueness + "index", "111111");*/


                    MyApplication.getDataBase().setToken(token);

                    SPUtils.saveUserInfo(mUserPWD.getText().toString().trim(), mUserCount.getText().toString().trim());

                    finish();
                }
                break;
            case 1:
                ShowToast(object.getString("msg"));



                MyApplication.getDataBase().setToken(object.getJSONObject("data").getString("token"));

                SPUtils.setUnionid(MyApplication.getDataBase().getUnionid());


             /*   // TODO: 2017/8/30
                spUtils.put("jihuo", Constants.uniqueness + "index", "111111");*/


                finish();
                break;
        }
    }
}
