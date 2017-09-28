package com.test720.wendujiaoyu.study.bean;

/**
 * Created by jie on 2017/8/11.
 */

public class MajorChoiceBean {

    /**
     * id : 1
     * name : 公开课
     * img : Public/Img/4.jpg
     */

    private String id;
    private String name;
    private String img;
    private String is_open;

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
