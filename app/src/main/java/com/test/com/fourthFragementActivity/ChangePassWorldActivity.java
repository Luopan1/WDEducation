package com.test.com.fourthFragementActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.activity.LoginActivity;
import com.test.com.application.MyApplication;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.utills.SPUtils;

import butterknife.BindView;

public class ChangePassWorldActivity extends BaseToolbarActivity {
    @BindView(R.id.oldPwd)
    EditText oldPwd;
    @BindView(R.id.newPWD)
    EditText newPwd;
    @BindView(R.id.newPWD1)
    EditText newPwd1;

    @BindView(R.id.comfirmUpData)
    Button comfirmUpData;

    @Override
    protected int getContentView() {
        return R.layout.activity_change_pass_world;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        comfirmUpData.setOnClickListener(this);

    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comfirmUpData:
                if (!(newPwd.getText().toString().trim().equals(newPwd1.getText().toString().trim()))) {
                    ShowToast("两次输入的密码不一致");
                } else if (newPwd.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入新密码");
                } else if (oldPwd.getText().toString().trim().isEmpty()) {
                    ShowToast("请输入旧密码");
                } else if (isCorrentPasswrold(newPwd)) {
                    ShowToast("密码长度在6~18位");
                } else {
                    setNewPwd(oldPwd.getText().toString().trim(), oldPwd.getText().toString().trim());
                }

                break;
        }

    }

    @Override
    protected void initView() {
        initToobar("修改密码", R.mipmap.fanhui);
    }

    void setNewPwd(final String oldPwd, final String newPwd) {
        RequestParams params = new RequestParams();
        params.add("uuid", MyApplication.getDataBase().getUuid());
        params.add("newPwd", newPwd);
        params.add("oldPwd", oldPwd);

        getDataFromInternet(UrlFactory.editPassword, params, 0);
        showLoadingDialog();
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        ShowToast(object.getString("msg"));
        if (object.getInteger("code").equals("1")) {
            SPUtils.saveUserInfo(newPwd.getText().toString().trim(), SPUtils.getCount());

            MyApplication.getDataBase().setToken("");
            SPUtils.saveUserInfo("", "");
            SPUtils.setUnionid("");
            MyApplication.getDataBase().setUnionid("");


            new AlertDialog.Builder(ChangePassWorldActivity.this).setTitle("提示")//设置对话框标题
                    .setMessage("密码修改成功，请重新登录")//设置显示的内容
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                            jumpToActivity(LoginActivity.class, false);
                        }

                    }).show();//在按键响应事件中显示此对话框、


        }

    }
}
