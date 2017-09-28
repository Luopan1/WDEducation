package com.test720.wendujiaoyu.activity;

import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.UrlFactory;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;

import butterknife.BindView;

public class ForgetPassWorldActivity extends BaseToolbarActivity {
    @BindView(R.id.checkBox1)
    CheckBox checkBox1;
    @BindView(R.id.checkBox2)
    CheckBox checkBox2;
    @BindView(R.id.et_setPWD)
    EditText et_setPWD;
    @BindView(R.id.et_comfirmPWD)
    EditText et_comfirmPWD;
    @BindView(R.id.comfirmUpData)
    Button comfirmUpData;
    @BindView(R.id.tv_getCheckCode)
    TextView tv_getCheckCode;
    @BindView(R.id.phoneNumber)
    EditText phoneNumber;
    @BindView(R.id.checkCode)
    EditText checkCode;
    int i = 60;


    @Override
    protected int getContentView() {
        return R.layout.activity_forget_pass_world;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        comfirmUpData.setOnClickListener(this);
        tv_getCheckCode.setOnClickListener(this);
    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
    }

    @Override
    public void onClick(View view) {
        String content = "";
        String content1 = "";
        switch (view.getId()) {
            case R.id.checkBox1:
                if (checkBox1.isChecked()) {
                    content = et_setPWD.getText().toString().trim();
                    if (!content.isEmpty()) {
                        et_setPWD.setText(content);
                        et_setPWD.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        et_setPWD.setSelection(content.length());
                    }
                } else {
                    content = et_setPWD.getText().toString().trim();
                    et_setPWD.setText(content);
                    et_setPWD.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_setPWD.setSelection(content.length());
                }
                break;
            case R.id.checkBox2:
                if (checkBox2.isChecked()) {
                    content1 = et_comfirmPWD.getText().toString().trim();
                    if (!content1.isEmpty()) {
                        et_comfirmPWD.setText(content1);
                        et_comfirmPWD.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        et_comfirmPWD.setSelection(content1.length());
                    }
                } else {
                    content1 = et_comfirmPWD.getText().toString().trim();
                    et_comfirmPWD.setText(content1);
                    et_comfirmPWD.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_comfirmPWD.setSelection(content1.length());
                }
                break;
            case R.id.comfirmUpData:
                String phone = phoneNumber.getText().toString().trim();
                String Code = checkCode.getText().toString().trim();
                content1 = et_comfirmPWD.getText().toString().trim();
                content = et_setPWD.getText().toString().trim();
                if (phone.isEmpty() || phone.length() != 11) {
                    ShowToast("请输入正确的手机号");
                } else if (Code.isEmpty()) {
                    ShowToast("验证码不能为空");
                } else if (content.isEmpty()) {
                    ShowToast("密码不能为空");
                } else if (!content1.equals(content)) {
                    ShowToast("两次输入的密码不一致");
                } else if (isCorrentPasswrold(et_setPWD)) {
                    ShowToast("密码长度在6~18位");
                } else if (content1.equals(content)) {
                    RequestParams params = new RequestParams();
                    params.put("phone", phone);
                    params.put("newPwd", content1);
                    params.put("code", Code);
                    getDataFromInternet(UrlFactory.forgetEditPwd, params, 0);
                    showLoadingDialog();
                }

                break;
            case R.id.tv_getCheckCode:
                phone = phoneNumber.getText().toString().trim();

                if (!phone.isEmpty() && phone.length() == 11) {

                    RequestParams params = new RequestParams();
                    params.put("phone", phone);
                    getDataFromInternet(UrlFactory.forgetGetCode, params, 1);
                    showLoadingDialog();
                } else {
                    ShowToast("请输入正确的手机号");
                }

                break;
        }
    }

    @Override
    protected void initView() {
        initToobar("忘记密码", R.mipmap.fanhui);
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        if (what == 0) {
            ShowToast(object.getString("msg"));
        } else if (what == 1) {

            ShowToast(object.getString("msg"));
            if (object.getInteger("code") == 1) {
                /**请求验证码*/
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        i--;
                        tv_getCheckCode.setText(i + "S");
                        tv_getCheckCode.setEnabled(false);
                        if (i > 0) {
                            handler.postDelayed(this, 1000);
                        } else {
                            i = 60;
                            tv_getCheckCode.setText("获取验证码");
                            tv_getCheckCode.setEnabled(true);
                        }

                    }
                }, 1000);
            }
        }
    }
}
