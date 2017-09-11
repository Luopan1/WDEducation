package com.test.com.application;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.test.com.study.download.DownLoadService;
import com.test.com.study.network.AppManager;
import com.test.com.utills.Constants;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by LuoPan on 2017/7/27.
 */

public class MyApplication extends Application {
    public static MyApplication context;
    public static userBaseInfo userBaseInfo;
    public static IWXAPI api;

    {
        PlatformConfig.setWeixin("wx3035dace6ab3628d", "615d12bf0d8af7c127ca91707dd0a360");
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
        Config.DEBUG =true;
        QueuedWork.isUseThreadPool = true;
        UMShareAPI.get(this);


        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);


        preinitX5WebCore();




        super.onCreate();
        api = WXAPIFactory.createWXAPI(this, Constants.WxId, true);
        //         将APP_ID注册到微信l
        api.registerApp(Constants.WxId);
        ActivitStats();
    }

/*
    *//**
     * 获取当前程序运行的进程名
     *//*
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }*/

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

