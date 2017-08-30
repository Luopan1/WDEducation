package com.test.com.fragements;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.RequestParams;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.activity.CommitSuccessActivity;
import com.test.com.baseUi.BaseFragment;



public class ZiXunFragment extends BaseFragment implements View.OnClickListener {

    private Button mCommitQuestion;
    EditText content;
    EditText phoneNumber;

    @Override
    protected void initView() {
        mCommitQuestion = getView(R.id.commitQuestion);
        content = getView(R.id.content);
        phoneNumber = getView(R.id.phoneNumber);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setListener() {
        mCommitQuestion.setOnClickListener(this);

    }

    @Override
    protected int setLayoutResID() {
        return R.layout.fragment_zixun;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commitQuestion:
                String contentText = content.getText().toString().trim();
                String phone = phoneNumber.getText().toString().trim();
                if (contentText.isEmpty() || phone.isEmpty()) {
                    ShowToast("内容不能为空");
                } else {
                    if (isLogined()) {
                        submitInformation(contentText, phone);
                    }

                }

                //                jumpToActivity(CommitSuccessActivity.class, false);
                break;
        }
    }

    public void submitInformation(String content, String phone) {
        RequestParams params = new RequestParams();
        params.add("content", content);
        params.add("phone", phone);
        getDataFromInternet(UrlFactory.addConsultation, params, 0);
        showLoadingDialog("");
    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        if (object.getInteger("code") == 1) {
            jumpToActivity(CommitSuccessActivity.class, false);
        } else {
            ShowToast("提交失败");
        }
    }
}
