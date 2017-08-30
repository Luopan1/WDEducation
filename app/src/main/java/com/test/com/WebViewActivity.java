package com.test.com;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.test.com.baseUi.BaseToolbarActivity;

import butterknife.BindView;

/**
 * TITLE  toolbar的标题
 * URL    网页的连接
 * <p>
 * isShowActionBar  是否展示toolbar
 */
public class WebViewActivity extends BaseToolbarActivity {

    public static String TITLE = "tilte";
    public static String URL = "Url";
    private String mUrl;
    @BindView(R.id.forum_context)
    WebView mWebView;

    @Override
    protected int getContentView() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initData() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSaveFormData(true);

        mWebView.loadUrl(mUrl);
        Log.e("TAG+url", mUrl);
        mWebView.canGoBack();
        mWebView.setDrawingCacheEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initBase() {
        isshowActionbar = true;

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initView() {
        Bundle intent = getIntent().getExtras();
        String title = intent.getString(TITLE);
        mUrl = intent.getString(URL);
        Log.e(TAG, mUrl);
        initToobar(title, R.mipmap.fanhui);
    }
}
