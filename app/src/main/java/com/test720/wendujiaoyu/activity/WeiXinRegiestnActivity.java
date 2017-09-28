package com.test720.wendujiaoyu.activity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.loopj.android.http.RequestParams;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.UrlFactory;
import com.test720.wendujiaoyu.application.MyApplication;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.entity.Major;
import com.test720.wendujiaoyu.entity.School;
import com.test720.wendujiaoyu.utills.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.test720.wendujiaoyu.R.id.QQmail;
import static com.test720.wendujiaoyu.R.id.passworld;
import static com.test720.wendujiaoyu.R.id.sendCheckCode;

public class WeiXinRegiestnActivity extends BaseToolbarActivity {

    @BindView(R.id.phoneNumber)
    EditText mPhoneNumber;
    @BindView(R.id.sendCheckCode)
    TextView mSendCheckCode;
    @BindView(R.id.checkCode)
    EditText mCheckCode;
    @BindView(R.id.name)
    EditText mName;
    @BindView(R.id.zaiduYuanxiao)
    MaterialSpinner mZaiduYuanxiao;
    @BindView(R.id.zaiduzhuanye)
    EditText mZaiduzhuanye;
    @BindView(R.id.baokaozhuanye)
    MaterialSpinner mBaokaozhuanye;
    @BindView(QQmail)
    EditText mQQmail;
    @BindView(passworld)
    EditText mPassworld;
    @BindView(R.id.comfirmPasswrold)
    EditText mComfirmPasswrold;
    @BindView(R.id.bt_complete)
    Button mBtComplete;
    int i = 60;
    public int Sex = 1;
    public String studyInSchool = "";
    public String studyMajor = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_wei_xin_login;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        getDataFromInternet(UrlFactory.SEARCHSCHOOL, params, 0);
        showLoadingDialog();

    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                Gson gson = new Gson();
                School school = gson.fromJson(object.toJSONString(), School.class);
                List<School.Data> datas = school.getData();
                List<String> mlists = new ArrayList<String>();
                for (int i = 0; i < datas.size(); i++) {
                    mlists.add(i, datas.get(i).getSc_name().toString());
                }
                Log.e("TAG", mlists.toString());
                mZaiduYuanxiao.setItems(mlists);
                studyInSchool=datas.get(0).getSc_name();
                getDataFromInternet(UrlFactory.searchMajor, new RequestParams(), 1);
                showLoadingDialog();

                break;
            case 1:
                gson = new Gson();
                Major majors = gson.fromJson(object.toJSONString(), Major.class);
                List<Major.Data> majorsLists = majors.getData();
                List<String> majorsListString = new ArrayList<String>();
                for (int i = 0; i < majorsLists.size(); i++) {
                    majorsListString.add(i, majorsLists.get(i).getName().toString());
                }
                studyMajor=majorsLists.get(0).getName();
                mBaokaozhuanye.setItems(majorsListString);
                break;
            case 3:
                ShowToast(object.getString("msg"));
                if (object.getInteger("code") == 1) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            i--;
                            mSendCheckCode.setText(i + "S");
                            mSendCheckCode.setEnabled(false);
                            if (i > 0) {
                                handler.postDelayed(this, 1000);
                            } else {
                                i = 60;
                                mSendCheckCode.setText("获取验证码");
                                mSendCheckCode.setEnabled(true);
                            }

                        }
                    }, 1000);
                }
                break;
            case 4:
                ShowToast(object.getString("msg"));
                if (object.getInteger("code").equals(1)) {
                /*  String uuid = object.getJSONObject("data").getString("uuid");
                    MyApplication.userBaseInfo.setUuid(uuid);
                    Constants.uniqueness=uuid;*/
                    com.test720.wendujiaoyu.utills.SPUtils.saveUserInfo(mPassworld.getText().toString().trim(), mPhoneNumber.getText().toString().trim());
                    jumpToActivity(MainActivity.class, true);
                }
                break;


        }
    }

    @Override
    protected void setListener() {
        mZaiduYuanxiao.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                studyInSchool = item.toString();
            }
        });
        mBaokaozhuanye.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                studyMajor = item.toString();
            }
        });
    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
    }

    @OnClick({R.id.rb_man, R.id.rb_woman, sendCheckCode, R.id.bt_complete})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_man:
                Sex = 1;
                break;
            case R.id.rb_woman:
                Sex = 2;
                break;
            case sendCheckCode:
                if (mPhoneNumber.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入电话号码");
                } else if (mPhoneNumber.getText().toString().trim().length() != 11) {
                    ShowToast("请输入正确的电话号码");
                } else {
                    getCheckCode(mPhoneNumber.getText().toString().trim());
                }
                break;
            case R.id.bt_complete:
                if (mPhoneNumber.getText().toString().trim().isEmpty() || mCheckCode.getText().toString().trim().isEmpty()
                        || mName.getText().toString().trim().isEmpty() || mZaiduYuanxiao.getText().toString().trim().isEmpty() ||
                        mZaiduzhuanye.getText().toString().trim().isEmpty() || mBaokaozhuanye.getText().toString().trim().isEmpty()
                        || mQQmail.getText().toString().trim().isEmpty() || mPassworld.getText().toString().trim().isEmpty() ||
                        mComfirmPasswrold.getText().toString().trim().isEmpty()) {
                    ShowToast("信息必须全部填写");

                } else if (mPhoneNumber.getText().toString().trim().length() != 11) {
                    ShowToast("请输入正确的电话号码");
                } else if (!mPassworld.getText().toString().trim().equals(mComfirmPasswrold.getText().toString().trim())) {
                    ShowToast("两次输入的密码不一致");
                } else if (isCorrentPasswrold(mComfirmPasswrold)) {
                    ShowToast("密码长度在6~18位");
                } else {
                    // TODO: 2017/8/19 注册
                    weixinLogin();
                }

                break;
        }
    }

    private void weixinLogin() {
        RequestParams params = new RequestParams();
        params.add("username", mPhoneNumber.getText().toString().trim());
        params.add("code", mCheckCode.getText().toString().trim());
        params.add("name", mName.getText().toString().trim());
        params.put("sex", Sex);
        params.add("school", studyInSchool);
        params.put("header", Constants.header);
        params.put("nickname", Constants.nickName);
        params.add("stay_major", mZaiduzhuanye.getText().toString().trim());
        params.add("examination_major", studyMajor);
        params.add("email", mQQmail.getText().toString().trim());
        params.add("password", mPassworld.getText().toString().trim());
        params.add("unionid", MyApplication.getDataBase().getUnionid());
        getDataFromInternet(UrlFactory.REGISTER, params, 4);
        showLoadingDialog();

    }

    private void getCheckCode(String trim) {
        RequestParams params = new RequestParams();
        params.add("phone", trim);
        getDataFromInternet(UrlFactory.GETREGIESTERCHECKCODE, params, 3);
        showLoadingDialog();

    }


    @Override
    protected void initView() {
        super.initView();
        initToobar("绑定账号", R.mipmap.fanhui);
    }


}
