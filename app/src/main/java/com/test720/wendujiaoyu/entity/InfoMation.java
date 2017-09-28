package com.test720.wendujiaoyu.entity;

/**
 * Created by LuoPan on 2017/8/10 15:42.
 */

public class InfoMation {
    /**
     * "id": "1",
     * "zikao_title": "2017年4月  四川高等教育自考通告",
     * "img": "",
     * "time": "2038-01-19"
     */

    public String id;
    public String zikao_title;
    public String img;
    public String time;

    public InfoMation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZikao_title() {
        return zikao_title;
    }

    public void setZikao_title(String zikao_title) {
        this.zikao_title = zikao_title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "InfoMation{" +
                "id='" + id + '\'' +
                ", zikao_title='" + zikao_title + '\'' +
                ", img='" + img + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}

