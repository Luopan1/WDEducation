package com.test720.wendujiaoyu.firstFragmentactivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.loopj.android.http.RequestParams;
import com.tencent.smtt.sdk.WebView;
import com.test720.wendujiaoyu.R;
import com.test720.wendujiaoyu.UrlFactory;
import com.test720.wendujiaoyu.baseUi.BaseToolbarActivity;
import com.test720.wendujiaoyu.utills.CircleImageView;
import com.test720.wendujiaoyu.utills.DensityUtil;
import com.test720.wendujiaoyu.utills.MyYAnimation;

import butterknife.BindView;

public class AskAndAnwserInfoActivity extends BaseToolbarActivity {
    @BindView(R.id.teacherPhoto)
    CircleImageView teacherPhoto;
    @BindView(R.id.teacherName)
    TextView teacherName;
    @BindView(R.id.loveMan)
    TextView loveMan;
    @BindView(R.id.teacherDescripation)
    TextView teacherDescripation;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.dianzanRelative)
    RelativeLayout dianzianRelative;
    @BindView(R.id.guanzhu)
    ImageView guanzhu;
    private String mId;
    private String mAnswer;
    private String mTeacher_describe;
    private String mTime;
    private int mLoveMan;
    private String mTouxiang;
    private String mAnswer_teacher;
    private String mTitle;
    private int isFabulous;
    private MyYAnimation mAnimation;


    @Override
    protected int getContentView() {
        return R.layout.activity_ask_and_anwser_info;
    }

    @Override
    protected void initData() {
        RequestParams params = new RequestParams();
        params.add("id", mId);
        getDataFromInternet(UrlFactory.answerInfo, params, 0);
        showLoadingDialog();


    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        switch (what) {
            case 0:
                if (object.getInteger("code").equals(1)) {
                    JSONObject data = object.getJSONObject("data");
                    JSONObject info = data.getJSONObject("info");
                    // TODO: 2017/8/9  沒有头像
                    Log.e("data", object.toString());
                    mAnswer_teacher = info.getString("answer_teacher");
                    mTouxiang = info.getString("touxiang");
                    Log.e("Touxiang", mTouxiang + "touxiang");
                    mLoveMan = info.getInteger("lovemun");
                    mTeacher_describe = info.getString("teacher_describe");
                    mAnswer = info.getString("answer");
                    isFabulous = info.getInteger("isFabulous");
                    time.setText("编辑时间:" + info.getString("time"));
                    Glide.with(AskAndAnwserInfoActivity.this).load(UrlFactory.imagePath+mTouxiang).asBitmap().centerCrop().into(teacherPhoto);
                    teacherName.setText(mAnswer_teacher);
                    loveMan.setText(mLoveMan + "");
                    teacherDescripation.setText(mTeacher_describe);
                    mWebView.loadUrl(UrlFactory.answerWeb + "/id/" + mId);

                    if (isFabulous == 1) {
                        Glide.with(this).load(R.mipmap.guanzu).asBitmap().into(guanzhu);
                    } else if (isFabulous == 0) {
                        Glide.with(this).load(R.mipmap.guanzwu).asBitmap().into(guanzhu);
                    }


                    break;
                }
            case 1:
                if (object.getInteger("code") == 1) {
                    ShowToast(object.getString("msg"));
                    if (isFabulous == 1) {
                      /*取消赞*/
                        isFabulous = 0;
                        mLoveMan = mLoveMan - 1;
                        loveMan.setText(mLoveMan + "");
                        Glide.with(this).load(R.mipmap.guanzwu).asBitmap().into(guanzhu);
                    } else if (isFabulous == 0) {
                        isFabulous = 1;
                        mLoveMan = mLoveMan + 1;
                        loveMan.setText(mLoveMan + "");
                        Glide.with(this).load(R.mipmap.guanzu).asBitmap().into(guanzhu);
                    }
                    guanzhu.clearAnimation();
                } else {
                    guanzhu.clearAnimation();
                }

                break;
        }
    }

    @Override
    protected void setListener() {
        dianzianRelative.setOnClickListener(this);
        mAnimation = new MyYAnimation();;
        mAnimation.setRepeatCount(Animation.INFINITE);

        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                dianzianRelative.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dianzianRelative.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

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
            case R.id.dianzanRelative:
                RequestParams params = new RequestParams();
                /*已经点赞了*/
                if (isFabulous == 1) {
                    /*取消赞*/
                    params.put("type", 2);
                } else if (isFabulous == 0) {
                    /*点赞*/
                    params.put("type", 1);
                     /*取消赞*/
                }
                params.put("id", mId);
                getDataFromInternet(UrlFactory.lovemunAnswer, params, 1);
                guanzhu.startAnimation(mAnimation);
                break;


        }
    }

    @Override
    protected void initView() {
        initToobar("问问", R.mipmap.fanhui);
        teacherPhoto.bringToFront();
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        teacherPhoto.setBorderColor(getResources().getColor(R.color.base_color));
        teacherPhoto.setBorderWidth(DensityUtil.dip2px(this, 3));
    }
}
