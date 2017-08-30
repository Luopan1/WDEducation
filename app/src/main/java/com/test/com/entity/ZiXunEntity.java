package com.test.com.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by LuoPan on 2017/7/28.
 */

public class ZiXunEntity implements MultiItemEntity {
    int kind;
    String imageUrl;
    String time;
    String title;
    String studyMethod;
    String getGrade;

    private int itemType;

    public static final String CanUseDaiJinquan = "canusedaijinquan";
    public static final String Cant_use_Daijinquan = "cant_use_daijinquan";
    public static final String CanUseRelatve = "canuseRelative";
    public static final String Cant_use_relative = "cant_use_relatve";


    public ZiXunEntity(String title) {
        this.title = title;
    }

    public ZiXunEntity(int kind, String title, String getGrade) {
        this.kind = kind;
        this.title = title;
        this.getGrade = getGrade;
    }

    public ZiXunEntity(int kind, String imageUrl, String time, String title, String studyMethod, String getGrade) {
        this.kind = kind;
        this.imageUrl = imageUrl;
        this.time = time;
        this.title = title;
        this.studyMethod = studyMethod;
        this.getGrade = getGrade;
    }

    public ZiXunEntity(String imageUrl, String title, String getGrade) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.getGrade = getGrade;
    }

    public String getStudyMethod() {
        return studyMethod;
    }

    public void setStudyMethod(String studyMethod) {
        this.studyMethod = studyMethod;
    }

    public String getGetGrade() {
        return getGrade;
    }

    public void setGetGrade(String getGrade) {
        this.getGrade = getGrade;
    }

    public ZiXunEntity(String imageUrl, int kind, String time, String title) {
        this.imageUrl = imageUrl;
        this.kind = kind;
        this.time = time;
        this.title = title;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int getItemType() {

        return itemType;
    }
}
