package com.test.com;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.test.com.baseUi.BaseToolbarActivity;
import com.test.com.baseUi.ProgressWebview;

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
    private static final String TAG = WebViewActivity.class.getSimpleName();
    private static final String APP_CACHE_DIRNAME = "/webcache"; // web缓存目录
    private String mUrl;
    @BindView(R.id.forum_context)
    ProgressWebview mWebView;
    private ProgressBar progressbar;  //进度条
    private int progressHeight = 10;  //进度条的高度，默认10px

    @Override
    protected int getContentView() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initData() {
        WebSettings webSettings = mWebView.getSettings();

        webSettings.setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK

        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 设置缓存模式
        // 开启DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启database storage API功能
        mWebView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath()
                + APP_CACHE_DIRNAME;
        // 设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath); // API 19 deprecated
        // 设置Application caches缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        // 开启Application Cache功能
        mWebView.getSettings().setAppCacheEnabled(true);

        mWebView.getScrollBarFadeDuration();



        mWebView.setWebViewClient(new WebViewClient() {
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





        mWebView.loadUrl(mUrl);
        Log.e("TAG+url", mUrl);
        mWebView.canGoBack();
        mWebView.setDrawingCacheEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
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
        mWebView.setOnWebViewListener(new ProgressWebview.onWebViewListener() {
            @Override
            public void onProgressChange(WebView view, int newProgress) {

            }

            @Override
            public void onPageFinish(WebView view) {

            }
        });
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

       /* progressbar = new ProgressBar(context, null,
                android.R.attr.progressBarStyleHorizontal);
        //设置加载进度条的高度
        progressbar.setLayoutParams(new AbsoluteLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, progressHeight, 0, 0));

        Drawable drawable = context.getResources().getDrawable(android.R.drawable.progress_horizontal);
        progressbar.setProgressDrawable(drawable);

        //添加进度到WebView
        mWebView.addView(progressbar);


        //适配手机大小
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);*/


    }
}
