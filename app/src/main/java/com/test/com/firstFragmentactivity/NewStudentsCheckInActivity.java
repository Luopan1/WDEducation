package com.test.com.firstFragmentactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.test.com.R;
import com.test.com.UrlFactory;
import com.test.com.activity.MainActivity;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.entity.WebViewEntity;

import butterknife.BindView;

public class NewStudentsCheckInActivity extends BaseToolbarActivity {
    @BindView(R.id.banli)
    RadioButton banli;
    @BindView(R.id.xuzhi)
    RadioButton xuzhi;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.callPhone)
    LinearLayout callPhone;
    @BindView(R.id.content_relative)
    WebView content_relative;


    private String mPhoneNumber = "";
    private String image1 = "";
    private String image2 = "";
    private WebSettings mWebSettings;
    private String businessManagement;
    private String businessInstructions;
    private int type = 1;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录
    private String url; // 网页url


    @Override
    protected int getContentView() {
        return R.layout.activity_new_students_check_in;
    }

    @Override
    protected void initData() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("type", type);
        getDataFromInternet(UrlFactory.getNewsRegiest, requestParams, 0);

        showLoadingDialog();

    }

    @Override
    public void getSuccess(JSONObject object, int what) {
        super.getSuccess(object, what);
        if (object.getInteger("code") == 1) {
            Gson gson = new Gson();
            WebViewEntity webViewEntity = gson.fromJson(object.toString(), WebViewEntity.class);
            mPhoneNumber = webViewEntity.getData().getExaminationTime().getPhone();
            title.setText("您距离第" + webViewEntity.getData().getExaminationTime().getFrequency() + "次自考还有" + webViewEntity.getData().getExaminationTime().getDays() + "天");
            businessManagement = webViewEntity.getData().getNewregisterMsg().getHandle();
            businessInstructions = webViewEntity.getData().getNewregisterMsg().getNotice();
            content_relative.loadUrl(businessManagement);


        }
    }

    @Override
    protected void setListener() {
        xuzhi.setOnClickListener(this);
        banli.setOnClickListener(this);
        callPhone.setOnClickListener(this);
    }

    @Override
    protected void initBase() {
        isshowActionbar = true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.banli:
                content_relative.loadUrl(businessManagement);
                break;
            case R.id.xuzhi:
                content_relative.loadUrl(businessInstructions);
                break;
            case R.id.callPhone:
                if (MainActivity.hasPremission) {
                    if (mPhoneNumber.isEmpty()) {

                    } else {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        Uri data = Uri.parse("tel:" + mPhoneNumber);
                        intent.setData(data);
                        startActivity(intent);

                    }
                } else {
                    ShowToast("请在设置中开启电话权限");
                }

                break;

        }

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 1);
        if (type == 1)
            initToobar("新生注册", R.mipmap.fanhui);
        else if (type == 2)
            initToobar("考试报考", R.mipmap.fanhui);
        else if (type == 3)
            initToobar("毕业申请", R.mipmap.fanhui);
        else if (type == 4)
            initToobar("学位申请", R.mipmap.fanhui);
        else if (type == 5)
            initToobar("学位英语", R.mipmap.fanhui);
        else if (type == 6)
            initToobar("准考证补办", R.mipmap.fanhui);
        mWebSettings = content_relative.getSettings();
        content_relative.getSettings().setJavaScriptEnabled(true);
        content_relative.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK

        content_relative.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 设置缓存模式
        // 开启DOM storage API 功能
        content_relative.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        content_relative.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath()
                + APP_CACHE_DIRNAME;
        // 设置数据库缓存路径
        content_relative.getSettings().setDatabasePath(cacheDirPath); // API 19 deprecated
        // 设置Application caches缓存目录
        content_relative.getSettings().setAppCachePath(cacheDirPath);
        // 开启Application Cache功能
        content_relative.getSettings().setAppCacheEnabled(true);

        content_relative.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {

                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            // 页面开始时调用
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            // 页面加载完成调用
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

            }
        });


        content_relative.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {


                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue,
                        result);
            }
        });
        content_relative.loadUrl(url);

    }
}
