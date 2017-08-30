package com.test.com.study.utils;

/**
 * Created by hp on 2017/6/7.
 */

public class arrlay {

    private int img;/*商品icon*/
    private String name;/*商品名称*/
    private String price;/*单价*/
    private int num; /*已售数量*/


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "arrlay{" +
                "img=" + img +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", num=" + num +
                '}';
    }
}
