package com.test.com.activity;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.entity.Major;
import com.test.com.entity.School;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class RegiestActivity1 extends BaseToolbarActivity {

    @BindView(R.id.rb_man)
    RadioButton mRbMan;
    @BindView(R.id.rb_woman)
    RadioButton mRbWoman;
    @BindView(R.id.phoneNumber)
    EditText phoneNumber;
    @BindView(R.id.sendCheckCode)
    TextView sendCheckCode;
    @BindView(R.id.niceSpinner)
    MaterialSpinner niceSpinner;
    @BindView(R.id.major)
    MaterialSpinner major;

    @BindView(R.id.et_checkCode)
    EditText et_checkCode;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.studyInMajor)
    EditText studyInMajor;
    @BindView(R.id.QQmail)
    EditText QQmail;
    @BindView(R.id.passworld)
    EditText passworld;
    @BindView(R.id.comfirmPwd)
    EditText comfirmPwd;


    public int Sex = 1;
    public String studyInSchool = "";
    public String studyMajor = "";
    int i = 60;

    @Override
    protected int getContentView() {
        return R.layout.activity_login1;
    }

    void Login() {
        RequestParams params = new RequestParams();
        params.add("username", phoneNumber.getText().toString().trim());
        params.add("code", et_checkCode.getText().toString().trim());
        params.add("name", name.getText().toString().trim());
        params.put("sex", Sex);
        params.add("school", studyInSchool);
        params.add("stay_major", studyInMajor.getText().toString().trim());
        params.add("examination_major", studyMajor);
        params.add("email", QQmail.getText().toString().trim());
        params.add("password", passworld.getText().toString().trim());
        getDataFromInternet(UrlFactory.REGISTER, params, 4);
        showLoadingDialog();

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
                niceSpinner.setItems(mlists);

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
                major.setItems(majorsListString);
                break;
            case 3:
                ShowToast(object.getString("msg"));
                if (object.getInteger("code") == 1) {
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            i--;
                            sendCheckCode.setText(i + "S");
                            sendCheckCode.setEnabled(false);
                            if (i > 0) {
                                handler.postDelayed(this, 1000);
                            } else {
                                i = 60;
                                sendCheckCode.setText("获取验证码");
                                sendCheckCode.setEnabled(true);
                            }

                        }
                    }, 1000);
                }
                break;
            case 4:
                ShowToast(object.getString("msg"));
                if (object.getInteger("code").equals(1)) {
                    finish();
                }
                break;
        }
    }

    @Override
    protected void setListener() {
        niceSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                studyInSchool = item.toString();
            }
        });
        major.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
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

    @OnClick({R.id.rb_man, R.id.rb_woman, R.id.sendCheckCode, R.id.regiest})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_man:
                mRbMan.setChecked(true);
                mRbWoman.setChecked(false);
                Sex = 1;
                break;
            case R.id.rb_woman:
                mRbWoman.setChecked(true);
                mRbMan.setChecked(false);
                Sex = 2;
                break;
            case R.id.sendCheckCode:
                if (phoneNumber.getText().toString().trim().isEmpty()) {
                    ShowToast("手机号不能为空");
                } else if (phoneNumber.getText().toString().trim().length() != 11) {
                    ShowToast("请输入正确的手机号");
                } else {
                    getCheckCode(phoneNumber.getText().toString().trim());
                }
                break;
            case R.id.regiest:
                if (phoneNumber.getText().toString().trim().isEmpty()
                        || et_checkCode.getText().toString().trim().isEmpty()
                        || studyInMajor.getText().toString().trim().isEmpty()
                        || QQmail.getText().toString().trim().isEmpty()
                        || passworld.getText().toString().trim().isEmpty()
                        || comfirmPwd.getText().toString().trim().isEmpty()) {
                    ShowToast("信息必须全部填写");

                } else if (phoneNumber.getText().toString().trim().length() != 11) {
                    ShowToast("请输入正确的手机号码");

                } else if (!(passworld.getText().toString().trim().equals(comfirmPwd.getText().toString().trim()))) {
                    ShowToast("两次输入的密码不一致");
                } else if (isCorrentPasswrold(passworld)) {
                    ShowToast("密码长度在6~18位");
                } else {
                    Login();
                }


                break;
        }
    }


    @Override
    protected void initView() {
        initToobar("注册", R.mipmap.fanhui);
    }

    String getCheckCode(String number) {
        RequestParams params = new RequestParams();
        params.add("phone", number);

        getDataFromInternet(UrlFactory.GETREGIESTERCHECKCODE, params, 3);
        showLoadingDialog();

        return null;
    }


}
