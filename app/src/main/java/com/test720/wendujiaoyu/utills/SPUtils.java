package com.test720.wendujiaoyu.utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.test720.wendujiaoyu.application.MyApplication;


/**
 * Created by LuoPan on 2017/8/9 19:09.
 */

public class SPUtils {
    static SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("WDEducation", Context.MODE_PRIVATE);

  /*  public static void saveInfo(String uuid, String phoneNumber) {
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器

        editor.putString("phoneNumber", phoneNumber);

        editor.putString("uuid", uuid);

        editor.commit();
    }*/

    public static String getUUID() {
        String name = sharedPreferences.getString("uuid", "");
        Log.e("uuid", name);
        return name;
    }

    public static void saveUserInfo(String pasword, String phoneNumber) {


        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器

        editor.putString("phoneNumber", phoneNumber);

        editor.putString("pasword", MD5Util.encrypt(pasword));

        editor.commit();
    }

    public static void setUnionid(String unionid) {
        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        editor.putString("unionid",unionid);
        editor.commit();

    }

    public static String getUnionid() {
        String unionid = sharedPreferences.getString("unionid", "");
        Log.e("TAG++getUnionid", unionid);
        return unionid;
    }

    public static String getCount() {
        String name = sharedPreferences.getString("phoneNumber", "");
        Log.e("+++账号", name);
        return name;
    }

    public static String getPWD() {
        String Pwd = sharedPreferences.getString("pasword", "");
        Log.e("TAG++原密码", Pwd);
        Log.e("TAG++解密之后的密码", MD5Util.decrypt(Pwd));
        return MD5Util.decrypt(Pwd);
    }

}
