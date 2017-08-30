package com.test.com.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.test.com.UrlFactory;
import com.test.com.study.download.DownLoadService;
import com.test.com.study.network.AppManager;
import com.test.com.utills.Constants;
import com.test.com.utills.SPUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import org.apache.http.Header;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by LuoPan on 2017/7/27.
 */

public class MyApplication extends Application {
    public static MyApplication context;
    public static userBaseInfo userBaseInfo;
    public static IWXAPI api;

    {
        PlatformConfig.setWeixin("wx3035dace6ab3628d", "b3a787ad44c68275f4d7c537d8af4565");
        PlatformConfig.setQQZone("101418727", "2e968ac823178bf919d5a6392bd51900");
    }

    @Override
    public void onCreate() {
        context = this;
        fm.jiecao.jcvideoplayer_lib.SPUtils spUtils = new fm.jiecao.jcvideoplayer_lib.SPUtils(getApplicationContext());

        userBaseInfo = new userBaseInfo();





        startService(new Intent(this, DownLoadService.class));
        preinitX5WebCore();

        CrashReport.initCrashReport(getApplicationContext(), "84898eb5a7", false);

        //         解决7.0不能打开照相机的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
        /**友盟*/
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);


        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);


        preinitX5WebCore();

        String prcessName = getCurProcessName(this);
        if (prcessName.equals("com.test.com")) {
//            autoLogin();
        }


        super.onCreate();
        api = WXAPIFactory.createWXAPI(this, Constants.WxId, true);
        //         将APP_ID注册到微信l
        api.registerApp(Constants.WxId);
        ActivitStats();
    }


    /**
     * 获取当前程序运行的进程名
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    private void autoLogin() {
        Log.e("TAG++++", "autoLogin");

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        if (!SPUtils.getCount().trim().isEmpty()) {
            params.put("username", SPUtils.getCount());
            params.put("password", SPUtils.getPWD());

            asyncHttpClient.post(UrlFactory.Login, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e("MyApplication+onFailure", throwable.toString());

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    JSONObject jsonObject = JSON.parseObject(responseString);
                    if (jsonObject.getInteger("code").equals(1)) {
                        Log.e("TAG+++passworld", "登录成功");
                        String token = jsonObject.getJSONObject("data").getString("token");
                        MyApplication.getDataBase().setToken(token);
                        // TODO: 2017/8/11 uuid不保存在sp中
                        MyApplication.userBaseInfo.setUuid("uuid");

                        Toast.makeText(MyApplication.getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("TAG++passworld", "登录失败");
                        MyApplication.getDataBase().setToken(" ");
                    }


                }
            });

        } else if (!SPUtils.getUnionid().isEmpty()) {
            Log.e("TAG++++", "getUnionid");
            params.put("unionid", SPUtils.getUnionid());
            asyncHttpClient.post(UrlFactory.appWeChatLogin, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e("MyApplication+onFailure", throwable.toString());

                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    JSONObject jsonObject = JSON.parseObject(responseString);
                    if (jsonObject.getInteger("code").equals(1)) {
                        String token = jsonObject.getJSONObject("data").getString("token");
                        MyApplication.getDataBase().setToken(token);
                        // TODO: 2017/8/11 uuid不保存在sp中
                        MyApplication.userBaseInfo.setUuid("uuid");
                        Log.e("TAG+++unionid", "登录成功");

                        Toast.makeText(MyApplication.getContext(), jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    }


                }
            });

        }


    }


    private void preinitX5WebCore() {
        if (!QbSdk.isTbsCoreInited()) {
            QbSdk.preInit(this, null);// 设置X5初始化完成的回调接口
        }
    }

    public static MyApplication getContext() {
        return context;
    }

    public static userBaseInfo getDataBase() {
        return userBaseInfo;
    }

    public static class userBaseInfo {
        String userName = "";
        String userPhoto;
        String uuid = "";
        String token = "";
        String unionid = "";

        public String getUnionid() {
            return unionid;
        }

        public void setUnionid(String unionid) {
            this.unionid = unionid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUuid() {
            Log.e("TAG++", uuid);
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public userBaseInfo(String userName, String userPhoto) {
            this.userName = userName;
            this.userPhoto = userPhoto;
        }

        public userBaseInfo() {
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
        }
    }


    //监听当前Activit状态
    private void ActivitStats() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                AppManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }


}

