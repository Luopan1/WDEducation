package com.test720.wendujiaoyu.fourthFragementActivity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.loopj.android.http.RequestParams;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.UrlFactory;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.entity.Major;
import com.test720.wendujiaoyu.entity.School;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

public class ChangeUserNickNameActivity extends BaseToolbarActivity {
    public static String TYPE = "type";
    public static String TITLE = "title";

    @BindView(R.id.deleteRelative)
    RelativeLayout deleteRelative;
    @BindView(R.id.editTextNickname)
    EditText editTextNickname;
    @BindView(R.id.textRight)
    TextView rightText;

    @BindView(R.id.chooseRelative)
    RelativeLayout chooseRelative;
    @BindView(R.id.editRelative)
    RelativeLayout editRelative;

    @BindView(R.id.niceSpinner)
    MaterialSpinner mSpinner;
    private int mType;

    public static String nickName = "";
    public static String Colloge = "";
    public static String major = " ";
    public static String majorIn = "";
    public static String mail = "";
    private List<String> Mlists;
    private List<String> mMajorsListString;


    @Override
    protected int getContentView() {
        return R.layout.activity_change_user_nick_name;
    }

    @Override
    protected void initData() {
        if (mType == 2) {
            RequestParams params = new RequestParams();
            getDataFromInternet(UrlFactory.SEARCHSCHOOL, params, 0);
            showLoadingDialog();


        } else if (mType == 4) {
            RequestParams params = new RequestParams();
            getDataFromInternet(UrlFactory.searchMajor, params, 1);
            showLoadingDialog();
        }


    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        if (what == 0) {
            Gson gson = new Gson();
            School school = gson.fromJson(object.toJSONString(), School.class);
            List<School.Data> datas = school.getData();
            Mlists = new ArrayList<String>();
            for (int i = 0; i < datas.size(); i++) {
                Mlists.add(i, datas.get(i).getSc_name().toString());
            }
            Colloge = datas.get(0).getSc_name();
            mSpinner.setItems(Mlists);
        }
        if (what == 1) {
            Gson gson = new Gson();
            Major majors = gson.fromJson(object.toJSONString(), Major.class);
            List<Major.Data> majorsLists = majors.getData();
            mMajorsListString = new ArrayList<String>();
            for (int i = 0; i < majorsLists.size(); i++) {
                mMajorsListString.add(i, majorsLists.get(i).getName().toString());
            }
            major = majorsLists.get(0).getName();
            mSpinner.setItems(mMajorsListString);
        }
        if (what == 2) {
            if (object.getInteger("code") == 1) {
                nickName = editTextNickname.getText().toString().trim();
                ShowToast(object.getString("msg"));
                finish();
            }
        }
        if (what == 3) {
            if (object.getInteger("code") == 1) {
                if (Colloge.trim().isEmpty()) {
                    Colloge = Mlists.get(0);
                }
                ShowToast(object.getString("msg"));
                finish();
            }
        }
        if (what == 4) {
            if (object.getInteger("code") == 1) {
                majorIn = editTextNickname.getText().toString().trim();
                ShowToast(object.getString("msg"));
                finish();
            }
        }
        if (what == 5) {
            if (object.getInteger("code") == 1) {
                ShowToast(object.getString("msg"));
                finish();
            }
        }
        if (what == 6) {
            if (object.getInteger("code") == 1) {
                mail = editTextNickname.getText().toString().trim();
                ShowToast(object.getString("msg"));
                finish();
            }
        }

    }

    @Override
    protected void setListener() {
        deleteRelative.setOnClickListener(this);
        rightText.setOnClickListener(this);
        mSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if (mType == 2) {
                    Colloge = item.toString();
                } else if (mType == 4) {
                    major = item.toString();
                }

            }
        });
    }

    @Override
    protected void initBase() {
        isshowActionbar = true;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.deleteRelative:
                editTextNickname.setText("");
                break;
            case R.id.textRight:
                if (mType == 1) {
                    if (editTextNickname.getText().toString().isEmpty()) {
                        ShowToast("昵称不能为空");
                    } else {
                        // TODO: 2017/8/16  上传到服务器
                        RequestParams params = new RequestParams();
                        params.put("nickname", editTextNickname.getText().toString().trim());
                        getDataFromInternet(UrlFactory.editUserInfo, params, 2);
                        showLoadingDialog();

                    }
                }
                if (mType == 2) {
                    if (Colloge.trim().length() == 0) {
                        ShowToast("请选择在读院校");
                    } else {
                        // TODO: 2017/8/16  上传到服务器
                        RequestParams params = new RequestParams();
                        params.put("school", Colloge);
                        getDataFromInternet(UrlFactory.editUserInfo, params, 3);
                        showLoadingDialog();
                    }
                }
                if (mType == 3) {
                    if (editTextNickname.getText().toString().isEmpty()) {
                        ShowToast("请填写在读专业");
                    } else {
                        // TODO: 2017/8/16
                        RequestParams params = new RequestParams();
                        params.put("stay_major", editTextNickname.getText().toString().trim());
                        getDataFromInternet(UrlFactory.editUserInfo, params, 4);
                        showLoadingDialog();
                    }
                }
                if (mType == 4) {
                    if (major.trim().length() == 0) {
                        ShowToast("请选择报考专业");
                    } else {
                        // TODO: 2017/8/16
                        RequestParams params = new RequestParams();
                        params.put("examination_major", major);
                        getDataFromInternet(UrlFactory.editUserInfo, params, 5);
                        showLoadingDialog();
                    }
                }
                if (mType == 5) {
                    if (editTextNickname.getText().toString().isEmpty()) {
                        ShowToast("请填写邮箱");
                    } else if (!isCorretMail(editTextNickname.getText().toString().trim())) {
                        ShowToast("请填写正确的邮箱");
                    } else {
                        // TODO: 2017/8/16
                        RequestParams params = new RequestParams();
                        params.put("email", editTextNickname.getText().toString().trim());
                        getDataFromInternet(UrlFactory.editUserInfo, params, 6);
                        showLoadingDialog();
                    }
                }
                break;

        }
    }

    public boolean isCorretMail(String mail) {
        //电子邮件
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(mail);
        boolean isMatched = matcher.matches();

        return isMatched;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(TITLE);
        mType = intent.getIntExtra(TYPE, 0);
        initToobar(R.mipmap.fanhui, title, "保存");

        if ((mType == 2) || (mType == 4)) {
            /**选择服务器的数据*/
            editRelative.setVisibility(View.GONE);
            chooseRelative.setVisibility(View.VISIBLE);
        } else {
            /**填写数据*/
            editRelative.setVisibility(View.VISIBLE);
            chooseRelative.setVisibility(View.GONE);
        }

    }
}
