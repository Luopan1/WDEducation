package com.test.com.activity;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.test.com.R;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.utills.HintUtils;

import butterknife.BindView;

public class RegiestActivity extends BaseToolbarActivity {
    @BindView(R.id.checkBox1)
    CheckBox checkBox1;
    @BindView(R.id.checkBox2)
    CheckBox checkBox2;
    @BindView(R.id.et_setPWD)
    EditText et_setPWD;
    @BindView(R.id.et_comfirmPWD)
    EditText et_comfirmPWD;
    @BindView(R.id.regiest)
    Button regiest;

    @Override
    protected int getContentView() {
        return R.layout.activity_regiest;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        regiest.setOnClickListener(this);
    }

    @Override
    protected void initBase() {
        isshowActionbar=true;
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
            case R.id.regiest:
                content1 = et_comfirmPWD.getText().toString().trim();
                content = et_setPWD.getText().toString().trim();
                if (content1.equals(content)&&(!content.isEmpty())) {


                } else if (content.isEmpty()) {
                   HintUtils.showToast(this,"密码为空");
                } else {
                    HintUtils.showToast(this,"两次输入的密码不一致");
                }
                break;
        }
    }
    @Override
    protected void initView() {
        initToobar("注册",R.mipmap.fanhui);
    }
}
