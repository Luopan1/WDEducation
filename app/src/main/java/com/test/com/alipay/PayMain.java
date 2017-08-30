package com.test.com.alipay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

public class PayMain {


    Activity mContext;
    CallBack callBack;
    String notify_url;

    public PayMain(Activity mContext, CallBack callBack, String notify_url) {
        super();
        this.mContext = mContext;
        this.callBack = callBack;
        this.notify_url = notify_url;
    }

    public void Pay(String price, String orderNo) {
        this.Pay("文都教育", "文都教育", price, orderNo);
    }

    public void Pay(String subject, String body, String price, String orderNo) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, subject, body, price, orderNo, notify_url);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = Keys.PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey);
        final String orderInfo = orderParam + "&" + sign;
        Log.e("TAG+++orderInfo",orderInfo);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult result = new PayResult((Map<String, String>) msg.obj);
            callBack.call(result);
        }
    };


    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2017082408349739";
    private static final int SDK_PAY_FLAG = 1;


}
