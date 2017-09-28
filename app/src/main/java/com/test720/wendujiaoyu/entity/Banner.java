package com.test720.wendujiaoyu.entity;

/**
 * Created by LuoPan on 2017/8/9 16:25.
 */

public class Banner {
    /**
     * "ba_img": "Public/Img/4.jpg",
     * "target_type": "1",
     * "ba_id": "1"
     * "target_url": ""
     */


    public String ba_img;
    public String target_type;
    public String ba_id;
    public String target_url;

    public String getTarget_url() {
        return target_url;
    }

    public void setTarget_url(String target_url) {
        this.target_url = target_url;
    }

    public Banner() {
    }

    public String getBa_img() {
        return ba_img;
    }

    public void setBa_img(String ba_img) {
        this.ba_img = ba_img;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public String getBa_id() {
        return ba_id;
    }

    public void setBa_id(String ba_id) {
        this.ba_id = ba_id;
    }
}
