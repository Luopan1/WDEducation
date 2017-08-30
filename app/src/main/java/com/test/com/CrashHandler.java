package com.test.com;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.test.com.application.MyApplication;


/**
 * Created by LuoPan on 2017/8/12 12:59.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    public static final String TAG = CrashHandler.class.getSimpleName();
    private static CrashHandler INSTANCE = new CrashHandler();
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;


    private CrashHandler() {
    }


    public static CrashHandler getInstance() {
        return INSTANCE;
    }


    public void init(Context ctx) {
        mContext = ctx;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // if (!handleException(ex) && mDefaultHandler != null) {
        // mDefaultHandler.uncaughtException(thread, ex);
        // } else {
        // android.os.Process.killProcess(android.os.Process.myPid());
        // System.exit(10);
        // }
        Log.e("uncaughtException" + thread.getName(), "TAG" + ex.getLocalizedMessage());
        ex.printStackTrace();
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(MyApplication.getContext(), "程序出错了", Toast.LENGTH_LONG).show();

                Looper.loop();


            }
        }.start();
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成. 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false
     */
   /* private boolean handleException(Throwable ex) {
        if (ex == null) {
            return true;
        }
        // new Handler(Looper.getMainLooper()).post(new Runnable() {
        // @Override
        // public void run() {
        // new AlertDialog.Builder(mContext).setTitle("提示")
        // .setMessage("程序崩溃了...").setNeutralButton("我知道了", null)
        // .create().show();
        // }
        // });


        return true;
    }*/
}