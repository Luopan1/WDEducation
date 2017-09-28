package com.test720.wendujiaoyu.entity;

import java.io.Serializable;

/**
 * Created by LuoPan on 2017/8/22 10:47.
 */

public class BaoMingUser implements Serializable{
    /*array(
   'name'=>'aaaa',//姓名             'user_Idcard'=>'user_Idcard',//身份证
           'phone'=>'123',//电话            'stay_school'=>'stay_school',//在读院校
           'baokao_major'=>'baokao_major',//报考专业
    ),*/

    public String name;
    public String phone;
    public String user_Idcard;
    public String baokao_major;
    public String stay_school;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser_Idcard() {
        return user_Idcard;
    }

    public void setUser_Idcard(String user_Idcard) {
        this.user_Idcard = user_Idcard;
    }

    public String getBaokao_major() {
        return baokao_major;
    }

    public void setBaokao_major(String baokao_major) {
        this.baokao_major = baokao_major;
    }

    public String getStay_school() {
        return stay_school;
    }

    public void setStay_school(String stay_school) {
        this.stay_school = stay_school;
    }

    public BaoMingUser(String name, String phone, String user_Idcard, String baokao_major, String stay_school) {
        this.name = name;
        this.phone = phone;
        this.user_Idcard = user_Idcard;
        this.baokao_major = baokao_major;
        this.stay_school = stay_school;
    }

    public BaoMingUser() {

    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", user_Idcard='" + user_Idcard + '\'' +
                ", baokao_major='" + baokao_major + '\'' +
                ", stay_school='" + stay_school + '\'' +
                '}';
    }
}
