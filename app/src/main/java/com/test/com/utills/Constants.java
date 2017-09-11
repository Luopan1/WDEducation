package com.test.com.utills;

import android.content.Context;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.test.com.application.MyApplication;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by jie on 2017/8/11.
 */

public class  Constants {
    public static String ImagHost = "http://www.dzkonline.com/";
    public static String httpHost = ImagHost+"index.php/Myapi/";
    public static String uid = "";
    public static String WxId="wx3035dace6ab3628d";
    public static String uniqueness = "";


    public static String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append("202cb962ac59075b964b07152d234b70");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes())
                .toUpperCase();
        return appSign;
    }

    public static void genPayReq(Context context, String prepayId, String nonceStr) {
        if (!MyApplication.api.isWXAppInstalled()) {
            Toast.makeText(context, "没有安装微信", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        PayReq req = new PayReq();
        req.appId = Constants.WxId;
        req.partnerId = "1488089012";
        req.prepayId = prepayId;
        req.packageValue = "Sign=WXPay";
        req.nonceStr = nonceStr;
        req.timeStamp = String.valueOf((System.currentTimeMillis() / 1000) + "");
        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
        req.sign = genAppSign(signParams);
        MyApplication.api.sendReq(req);
    }

}
