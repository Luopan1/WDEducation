package com.test720.wendujiaoyu.entity;

import java.util.List;

/**
 * Created by LuoPan on 2017/8/23 17:54.
 */

public class Order {

    /**
     * msg : 成功
     * data : [{"baokao_major":"汉语言文学","price":"190","time":"2017-08-23 16:54:16","stay_school":"西南财经大学","type":"0","d_id":"184"}]
     * code : 1
     */

    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * baokao_major : 汉语言文学
         * price : 190
         * time : 2017-08-23 16:54:16
         * stay_school : 西南财经大学
         * type : 0
         * d_id : 184
         */

        private String baokao_major;
        private String price;
        private String time;
        private String stay_school;
        private String type;
        private String d_id;

        public String getBaokao_major() {
            return baokao_major;
        }

        public void setBaokao_major(String baokao_major) {
            this.baokao_major = baokao_major;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStay_school() {
            return stay_school;
        }

        public void setStay_school(String stay_school) {
            this.stay_school = stay_school;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getD_id() {
            return d_id;
        }

        public void setD_id(String d_id) {
            this.d_id = d_id;
        }
    }
}
