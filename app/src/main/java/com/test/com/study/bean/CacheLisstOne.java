package com.test.com.study.bean;

/**
 * Created by jie on 2017/8/17.
 */

public class CacheLisstOne {


    /**
     * id : 1
     * name : 公开课
     * img : Public/Img/4.jpg
     * stats 是否下载过图片
     * type  区分类别
     * num  数量
     */

    private String id;
    private String name;
    private String img;
    private int stats;
    private int type;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CacheLisstOne{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", img='" + img + '\'' +
                ", stats=" + stats +
                ", type=" + type +
                '}';
    }

    public int getStats() {
        return stats;
    }

    public void setStats(int stats) {
        this.stats = stats;
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
