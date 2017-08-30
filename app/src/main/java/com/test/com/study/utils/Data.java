package com.test.com.study.utils;

import java.util.List;

/**
 * java 实体类
 * Created by 南尘 on 16-7-28.
 */
public class Data {

    private String text;
    private int imageId;
    private String imageUrl;
    private int ischouse = 0;/*是否选中 1为选中，0未选中*/

    private int num = 1;/*数量*/
    private List<arrlay> list;

    public List<arrlay> getList() {
        return list;
    }

    public void setList(List<arrlay> list) {
        this.list = list;
    }

    public Data() {
    }

    public Data(String text, int imageId) {
        this.text = text;
        this.imageId = imageId;
    }

    public Data(String text, String imageUrl) {
        this.imageUrl = imageUrl;
        this.text = text;
    }

    public Data(String text, String imageUrl, List<arrlay> list) {
        this.imageUrl = imageUrl;
        this.text = text;
        this.list = list;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getIschouse() {
        return ischouse;
    }

    public void setIschouse(int ischouse) {
        this.ischouse = ischouse;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Data{" +
                "text='" + text + '\'' +
                ", imageId=" + imageId +
                ", imageUrl='" + imageUrl + '\'' +
                ", list=" + list +
                '}';
    }
}
